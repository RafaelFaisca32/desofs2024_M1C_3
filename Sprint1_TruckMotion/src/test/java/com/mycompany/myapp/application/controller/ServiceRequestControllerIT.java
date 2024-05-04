package com.mycompany.myapp.application.controller;

import static com.mycompany.myapp.domain.serviceRequest.ServiceRequestAsserts.*;
import static com.mycompany.myapp.application.controller.TestUtil.createUpdateProxyForBean;
import static com.mycompany.myapp.application.controller.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.serviceRequest.IServiceRequestRepository;
import com.mycompany.myapp.domain.serviceRequest.ServiceRequest;
import com.mycompany.myapp.domain.serviceRequest.dto.ServiceRequestDTO;
import com.mycompany.myapp.domain.serviceRequest.mapper.ServiceRequestMapper;
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
 * Integration tests for the {@link ServiceRequestController} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ServiceRequestControllerIT {

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

    private static final String ENTITY_API_URL = "/api/service-requests";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private ObjectMapper om;

    @Autowired
    private IServiceRequestRepository serviceRequestRepository;

    @Autowired
    private ServiceRequestMapper serviceRequestMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restServiceRequestMockMvc;

    private ServiceRequest serviceRequest;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ServiceRequest createEntity(EntityManager em) {
        ServiceRequest serviceRequest = new ServiceRequest()
            .items(DEFAULT_ITEMS)
            .serviceName(DEFAULT_SERVICE_NAME)
            .totalWeightOfItems(DEFAULT_TOTAL_WEIGHT_OF_ITEMS)
            .price(DEFAULT_PRICE)
            .date(DEFAULT_DATE);
        return serviceRequest;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ServiceRequest createUpdatedEntity(EntityManager em) {
        ServiceRequest serviceRequest = new ServiceRequest()
            .items(UPDATED_ITEMS)
            .serviceName(UPDATED_SERVICE_NAME)
            .totalWeightOfItems(UPDATED_TOTAL_WEIGHT_OF_ITEMS)
            .price(UPDATED_PRICE)
            .date(UPDATED_DATE);
        return serviceRequest;
    }

    @BeforeEach
    public void initTest() {
        serviceRequest = createEntity(em);
    }

    @Test
    @Transactional
    void createServiceRequest() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the ServiceRequest
        ServiceRequestDTO serviceRequestDTO = serviceRequestMapper.toDto(serviceRequest);
        var returnedServiceRequestDTO = om.readValue(
            restServiceRequestMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(serviceRequestDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            ServiceRequestDTO.class
        );

        // Validate the ServiceRequest in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedServiceRequest = serviceRequestMapper.toEntity(returnedServiceRequestDTO);
        assertServiceRequestUpdatableFieldsEquals(returnedServiceRequest, getPersistedServiceRequest(returnedServiceRequest));
    }

    @Test
    @Transactional
    void createServiceRequestWithExistingId() throws Exception {
        // Create the ServiceRequest with an existing ID
        serviceRequestRepository.saveAndFlush(serviceRequest);
        ServiceRequestDTO serviceRequestDTO = serviceRequestMapper.toDto(serviceRequest);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restServiceRequestMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(serviceRequestDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ServiceRequest in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllServiceRequests() throws Exception {
        // Initialize the database
        serviceRequestRepository.saveAndFlush(serviceRequest);

        // Get all the serviceRequestList
        restServiceRequestMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(serviceRequest.getId().toString())))
            .andExpect(jsonPath("$.[*].items").value(hasItem(DEFAULT_ITEMS)))
            .andExpect(jsonPath("$.[*].serviceName").value(hasItem(DEFAULT_SERVICE_NAME)))
            .andExpect(jsonPath("$.[*].totalWeightOfItems").value(hasItem(DEFAULT_TOTAL_WEIGHT_OF_ITEMS.doubleValue())))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.doubleValue())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(sameInstant(DEFAULT_DATE))));
    }

    @Test
    @Transactional
    void getServiceRequest() throws Exception {
        // Initialize the database
        serviceRequestRepository.saveAndFlush(serviceRequest);

        // Get the serviceRequest
        restServiceRequestMockMvc
            .perform(get(ENTITY_API_URL_ID, serviceRequest.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(serviceRequest.getId().toString()))
            .andExpect(jsonPath("$.items").value(DEFAULT_ITEMS))
            .andExpect(jsonPath("$.serviceName").value(DEFAULT_SERVICE_NAME))
            .andExpect(jsonPath("$.totalWeightOfItems").value(DEFAULT_TOTAL_WEIGHT_OF_ITEMS.doubleValue()))
            .andExpect(jsonPath("$.price").value(DEFAULT_PRICE.doubleValue()))
            .andExpect(jsonPath("$.date").value(sameInstant(DEFAULT_DATE)));
    }

    @Test
    @Transactional
    void getNonExistingServiceRequest() throws Exception {
        // Get the serviceRequest
        restServiceRequestMockMvc.perform(get(ENTITY_API_URL_ID, UUID.randomUUID().toString())).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingServiceRequest() throws Exception {
        // Initialize the database
        serviceRequestRepository.saveAndFlush(serviceRequest);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the serviceRequest
        ServiceRequest updatedServiceRequest = serviceRequestRepository.findById(serviceRequest.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedServiceRequest are not directly saved in db
        em.detach(updatedServiceRequest);
        updatedServiceRequest
            .items(UPDATED_ITEMS)
            .serviceName(UPDATED_SERVICE_NAME)
            .totalWeightOfItems(UPDATED_TOTAL_WEIGHT_OF_ITEMS)
            .price(UPDATED_PRICE)
            .date(UPDATED_DATE);
        ServiceRequestDTO serviceRequestDTO = serviceRequestMapper.toDto(updatedServiceRequest);

        restServiceRequestMockMvc
            .perform(
                put(ENTITY_API_URL_ID, serviceRequestDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(serviceRequestDTO))
            )
            .andExpect(status().isOk());

        // Validate the ServiceRequest in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedServiceRequestToMatchAllProperties(updatedServiceRequest);
    }

    @Test
    @Transactional
    void putNonExistingServiceRequest() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        serviceRequest.setId(UUID.randomUUID());

        // Create the ServiceRequest
        ServiceRequestDTO serviceRequestDTO = serviceRequestMapper.toDto(serviceRequest);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restServiceRequestMockMvc
            .perform(
                put(ENTITY_API_URL_ID, serviceRequestDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(serviceRequestDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ServiceRequest in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchServiceRequest() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        serviceRequest.setId(UUID.randomUUID());

        // Create the ServiceRequest
        ServiceRequestDTO serviceRequestDTO = serviceRequestMapper.toDto(serviceRequest);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restServiceRequestMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(serviceRequestDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ServiceRequest in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamServiceRequest() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        serviceRequest.setId(UUID.randomUUID());

        // Create the ServiceRequest
        ServiceRequestDTO serviceRequestDTO = serviceRequestMapper.toDto(serviceRequest);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restServiceRequestMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(serviceRequestDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the ServiceRequest in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateServiceRequestWithPatch() throws Exception {
        // Initialize the database
        serviceRequestRepository.saveAndFlush(serviceRequest);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the serviceRequest using partial update
        ServiceRequest partialUpdatedServiceRequest = new ServiceRequest();
        partialUpdatedServiceRequest.setId(serviceRequest.getId());

        partialUpdatedServiceRequest.serviceName(UPDATED_SERVICE_NAME).totalWeightOfItems(UPDATED_TOTAL_WEIGHT_OF_ITEMS).date(UPDATED_DATE);

        restServiceRequestMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedServiceRequest.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedServiceRequest))
            )
            .andExpect(status().isOk());

        // Validate the ServiceRequest in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertServiceRequestUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedServiceRequest, serviceRequest),
            getPersistedServiceRequest(serviceRequest)
        );
    }

    @Test
    @Transactional
    void fullUpdateServiceRequestWithPatch() throws Exception {
        // Initialize the database
        serviceRequestRepository.saveAndFlush(serviceRequest);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the serviceRequest using partial update
        ServiceRequest partialUpdatedServiceRequest = new ServiceRequest();
        partialUpdatedServiceRequest.setId(serviceRequest.getId());

        partialUpdatedServiceRequest
            .items(UPDATED_ITEMS)
            .serviceName(UPDATED_SERVICE_NAME)
            .totalWeightOfItems(UPDATED_TOTAL_WEIGHT_OF_ITEMS)
            .price(UPDATED_PRICE)
            .date(UPDATED_DATE);

        restServiceRequestMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedServiceRequest.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedServiceRequest))
            )
            .andExpect(status().isOk());

        // Validate the ServiceRequest in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertServiceRequestUpdatableFieldsEquals(partialUpdatedServiceRequest, getPersistedServiceRequest(partialUpdatedServiceRequest));
    }

    @Test
    @Transactional
    void patchNonExistingServiceRequest() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        serviceRequest.setId(UUID.randomUUID());

        // Create the ServiceRequest
        ServiceRequestDTO serviceRequestDTO = serviceRequestMapper.toDto(serviceRequest);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restServiceRequestMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, serviceRequestDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(serviceRequestDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ServiceRequest in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchServiceRequest() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        serviceRequest.setId(UUID.randomUUID());

        // Create the ServiceRequest
        ServiceRequestDTO serviceRequestDTO = serviceRequestMapper.toDto(serviceRequest);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restServiceRequestMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(serviceRequestDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ServiceRequest in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamServiceRequest() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        serviceRequest.setId(UUID.randomUUID());

        // Create the ServiceRequest
        ServiceRequestDTO serviceRequestDTO = serviceRequestMapper.toDto(serviceRequest);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restServiceRequestMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(serviceRequestDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the ServiceRequest in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteServiceRequest() throws Exception {
        // Initialize the database
        serviceRequestRepository.saveAndFlush(serviceRequest);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the serviceRequest
        restServiceRequestMockMvc
            .perform(delete(ENTITY_API_URL_ID, serviceRequest.getId().toString()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return serviceRequestRepository.count();
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

    protected ServiceRequest getPersistedServiceRequest(ServiceRequest serviceRequest) {
        return serviceRequestRepository.findById(serviceRequest.getId()).orElseThrow();
    }

    protected void assertPersistedServiceRequestToMatchAllProperties(ServiceRequest expectedServiceRequest) {
        assertServiceRequestAllPropertiesEquals(expectedServiceRequest, getPersistedServiceRequest(expectedServiceRequest));
    }

    protected void assertPersistedServiceRequestToMatchUpdatableProperties(ServiceRequest expectedServiceRequest) {
        assertServiceRequestAllUpdatablePropertiesEquals(expectedServiceRequest, getPersistedServiceRequest(expectedServiceRequest));
    }
}
