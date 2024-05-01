package com.mycompany.myapp.web.rest;

import static com.mycompany.myapp.domain.ServiceAsserts.*;
import static com.mycompany.myapp.web.rest.TestUtil.createUpdateProxyForBean;
import static com.mycompany.myapp.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Service;
import com.mycompany.myapp.repository.ServiceRepository;
import com.mycompany.myapp.service.dto.ServiceDTO;
import com.mycompany.myapp.service.mapper.ServiceMapper;
import jakarta.persistence.EntityManager;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link ServiceResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ServiceResourceIT {

    private static final String DEFAULT_ITEMS = "AAAAAAAAAA";
    private static final String UPDATED_ITEMS = "BBBBBBBBBB";

    private static final String DEFAULT_SERVICE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_SERVICE_NAME = "BBBBBBBBBB";

    private static final Float DEFAULT_TOTAL_WEIGHT_OF_ITEMS = 1F;
    private static final Float UPDATED_TOTAL_WEIGHT_OF_ITEMS = 2F;

    private static final Float DEFAULT_PRICE = 1F;
    private static final Float UPDATED_PRICE = 2F;

    private static final ZonedDateTime DEFAULT_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String ENTITY_API_URL = "/api/services";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private ObjectMapper om;

    @Autowired
    private ServiceRepository serviceRepository;

    @Autowired
    private ServiceMapper serviceMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restServiceMockMvc;

    private Service service;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Service createEntity(EntityManager em) {
        Service service = new Service()
            .items(DEFAULT_ITEMS)
            .serviceName(DEFAULT_SERVICE_NAME)
            .totalWeightOfItems(DEFAULT_TOTAL_WEIGHT_OF_ITEMS)
            .price(DEFAULT_PRICE)
            .date(DEFAULT_DATE);
        return service;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Service createUpdatedEntity(EntityManager em) {
        Service service = new Service()
            .items(UPDATED_ITEMS)
            .serviceName(UPDATED_SERVICE_NAME)
            .totalWeightOfItems(UPDATED_TOTAL_WEIGHT_OF_ITEMS)
            .price(UPDATED_PRICE)
            .date(UPDATED_DATE);
        return service;
    }

    @BeforeEach
    public void initTest() {
        service = createEntity(em);
    }

    @Test
    @Transactional
    void createService() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Service
        ServiceDTO serviceDTO = serviceMapper.toDto(service);
        var returnedServiceDTO = om.readValue(
            restServiceMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(serviceDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            ServiceDTO.class
        );

        // Validate the Service in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedService = serviceMapper.toEntity(returnedServiceDTO);
        assertServiceUpdatableFieldsEquals(returnedService, getPersistedService(returnedService));
    }

    @Test
    @Transactional
    void createServiceWithExistingId() throws Exception {
        // Create the Service with an existing ID
        serviceRepository.saveAndFlush(service);
        ServiceDTO serviceDTO = serviceMapper.toDto(service);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restServiceMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(serviceDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Service in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllServices() throws Exception {
        // Initialize the database
        serviceRepository.saveAndFlush(service);

        // Get all the serviceList
        restServiceMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(service.getId().toString())))
            .andExpect(jsonPath("$.[*].items").value(hasItem(DEFAULT_ITEMS)))
            .andExpect(jsonPath("$.[*].serviceName").value(hasItem(DEFAULT_SERVICE_NAME)))
            .andExpect(jsonPath("$.[*].totalWeightOfItems").value(hasItem(DEFAULT_TOTAL_WEIGHT_OF_ITEMS.doubleValue())))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.doubleValue())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(sameInstant(DEFAULT_DATE))));
    }

    @Test
    @Transactional
    void getService() throws Exception {
        // Initialize the database
        serviceRepository.saveAndFlush(service);

        // Get the service
        restServiceMockMvc
            .perform(get(ENTITY_API_URL_ID, service.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(service.getId().toString()))
            .andExpect(jsonPath("$.items").value(DEFAULT_ITEMS))
            .andExpect(jsonPath("$.serviceName").value(DEFAULT_SERVICE_NAME))
            .andExpect(jsonPath("$.totalWeightOfItems").value(DEFAULT_TOTAL_WEIGHT_OF_ITEMS.doubleValue()))
            .andExpect(jsonPath("$.price").value(DEFAULT_PRICE.doubleValue()))
            .andExpect(jsonPath("$.date").value(sameInstant(DEFAULT_DATE)));
    }

    @Test
    @Transactional
    void getNonExistingService() throws Exception {
        // Get the service
        restServiceMockMvc.perform(get(ENTITY_API_URL_ID, UUID.randomUUID().toString())).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingService() throws Exception {
        // Initialize the database
        serviceRepository.saveAndFlush(service);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the service
        Service updatedService = serviceRepository.findById(service.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedService are not directly saved in db
        em.detach(updatedService);
        updatedService
            .items(UPDATED_ITEMS)
            .serviceName(UPDATED_SERVICE_NAME)
            .totalWeightOfItems(UPDATED_TOTAL_WEIGHT_OF_ITEMS)
            .price(UPDATED_PRICE)
            .date(UPDATED_DATE);
        ServiceDTO serviceDTO = serviceMapper.toDto(updatedService);

        restServiceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, serviceDTO.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(serviceDTO))
            )
            .andExpect(status().isOk());

        // Validate the Service in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedServiceToMatchAllProperties(updatedService);
    }

    @Test
    @Transactional
    void putNonExistingService() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        service.setId(UUID.randomUUID());

        // Create the Service
        ServiceDTO serviceDTO = serviceMapper.toDto(service);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restServiceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, serviceDTO.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(serviceDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Service in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchService() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        service.setId(UUID.randomUUID());

        // Create the Service
        ServiceDTO serviceDTO = serviceMapper.toDto(service);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restServiceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(serviceDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Service in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamService() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        service.setId(UUID.randomUUID());

        // Create the Service
        ServiceDTO serviceDTO = serviceMapper.toDto(service);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restServiceMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(serviceDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Service in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateServiceWithPatch() throws Exception {
        // Initialize the database
        serviceRepository.saveAndFlush(service);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the service using partial update
        Service partialUpdatedService = new Service();
        partialUpdatedService.setId(service.getId());

        partialUpdatedService
            .items(UPDATED_ITEMS)
            .serviceName(UPDATED_SERVICE_NAME)
            .totalWeightOfItems(UPDATED_TOTAL_WEIGHT_OF_ITEMS)
            .date(UPDATED_DATE);

        restServiceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedService.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedService))
            )
            .andExpect(status().isOk());

        // Validate the Service in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertServiceUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedService, service), getPersistedService(service));
    }

    @Test
    @Transactional
    void fullUpdateServiceWithPatch() throws Exception {
        // Initialize the database
        serviceRepository.saveAndFlush(service);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the service using partial update
        Service partialUpdatedService = new Service();
        partialUpdatedService.setId(service.getId());

        partialUpdatedService
            .items(UPDATED_ITEMS)
            .serviceName(UPDATED_SERVICE_NAME)
            .totalWeightOfItems(UPDATED_TOTAL_WEIGHT_OF_ITEMS)
            .price(UPDATED_PRICE)
            .date(UPDATED_DATE);

        restServiceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedService.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedService))
            )
            .andExpect(status().isOk());

        // Validate the Service in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertServiceUpdatableFieldsEquals(partialUpdatedService, getPersistedService(partialUpdatedService));
    }

    @Test
    @Transactional
    void patchNonExistingService() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        service.setId(UUID.randomUUID());

        // Create the Service
        ServiceDTO serviceDTO = serviceMapper.toDto(service);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restServiceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, serviceDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(serviceDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Service in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchService() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        service.setId(UUID.randomUUID());

        // Create the Service
        ServiceDTO serviceDTO = serviceMapper.toDto(service);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restServiceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(serviceDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Service in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamService() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        service.setId(UUID.randomUUID());

        // Create the Service
        ServiceDTO serviceDTO = serviceMapper.toDto(service);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restServiceMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(serviceDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Service in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteService() throws Exception {
        // Initialize the database
        serviceRepository.saveAndFlush(service);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the service
        restServiceMockMvc
            .perform(delete(ENTITY_API_URL_ID, service.getId().toString()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return serviceRepository.count();
    }

    protected void assertIncrementedRepositoryCount(long countBefore) {
        assertThat(countBefore + 1).isEqualTo(getRepositoryCount());
    }

    protected void assertDecrementedRepositoryCount(long countBefore) {
        assertThat(countBefore - 1).isEqualTo(getRepositoryCount());
    }

    protected void assertSameRepositoryCount(long countBefore) {
        assertThat(countBefore).isEqualTo(getRepositoryCount());
    }

    protected Service getPersistedService(Service service) {
        return serviceRepository.findById(service.getId()).orElseThrow();
    }

    protected void assertPersistedServiceToMatchAllProperties(Service expectedService) {
        assertServiceAllPropertiesEquals(expectedService, getPersistedService(expectedService));
    }

    protected void assertPersistedServiceToMatchUpdatableProperties(Service expectedService) {
        assertServiceAllUpdatablePropertiesEquals(expectedService, getPersistedService(expectedService));
    }
}
