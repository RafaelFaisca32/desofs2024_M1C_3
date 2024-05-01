package com.mycompany.myapp.web.rest;

import static com.mycompany.myapp.domain.ServiceStatusAsserts.*;
import static com.mycompany.myapp.web.rest.TestUtil.createUpdateProxyForBean;
import static com.mycompany.myapp.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.ServiceStatus;
import com.mycompany.myapp.domain.enumeration.Status;
import com.mycompany.myapp.repository.ServiceStatusRepository;
import com.mycompany.myapp.service.dto.ServiceStatusDTO;
import com.mycompany.myapp.service.mapper.ServiceStatusMapper;
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
 * Integration tests for the {@link ServiceStatusResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ServiceStatusResourceIT {

    private static final ZonedDateTime DEFAULT_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_OBSERVATIONS = "AAAAAAAAAA";
    private static final String UPDATED_OBSERVATIONS = "BBBBBBBBBB";

    private static final Status DEFAULT_STATUS = Status.ACTIVE;
    private static final Status UPDATED_STATUS = Status.FINISHED;

    private static final String ENTITY_API_URL = "/api/service-statuses";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private ObjectMapper om;

    @Autowired
    private ServiceStatusRepository serviceStatusRepository;

    @Autowired
    private ServiceStatusMapper serviceStatusMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restServiceStatusMockMvc;

    private ServiceStatus serviceStatus;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ServiceStatus createEntity(EntityManager em) {
        ServiceStatus serviceStatus = new ServiceStatus().date(DEFAULT_DATE).observations(DEFAULT_OBSERVATIONS).status(DEFAULT_STATUS);
        return serviceStatus;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ServiceStatus createUpdatedEntity(EntityManager em) {
        ServiceStatus serviceStatus = new ServiceStatus().date(UPDATED_DATE).observations(UPDATED_OBSERVATIONS).status(UPDATED_STATUS);
        return serviceStatus;
    }

    @BeforeEach
    public void initTest() {
        serviceStatus = createEntity(em);
    }

    @Test
    @Transactional
    void createServiceStatus() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the ServiceStatus
        ServiceStatusDTO serviceStatusDTO = serviceStatusMapper.toDto(serviceStatus);
        var returnedServiceStatusDTO = om.readValue(
            restServiceStatusMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(serviceStatusDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            ServiceStatusDTO.class
        );

        // Validate the ServiceStatus in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedServiceStatus = serviceStatusMapper.toEntity(returnedServiceStatusDTO);
        assertServiceStatusUpdatableFieldsEquals(returnedServiceStatus, getPersistedServiceStatus(returnedServiceStatus));
    }

    @Test
    @Transactional
    void createServiceStatusWithExistingId() throws Exception {
        // Create the ServiceStatus with an existing ID
        serviceStatusRepository.saveAndFlush(serviceStatus);
        ServiceStatusDTO serviceStatusDTO = serviceStatusMapper.toDto(serviceStatus);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restServiceStatusMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(serviceStatusDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ServiceStatus in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllServiceStatuses() throws Exception {
        // Initialize the database
        serviceStatusRepository.saveAndFlush(serviceStatus);

        // Get all the serviceStatusList
        restServiceStatusMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(serviceStatus.getId().toString())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(sameInstant(DEFAULT_DATE))))
            .andExpect(jsonPath("$.[*].observations").value(hasItem(DEFAULT_OBSERVATIONS)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));
    }

    @Test
    @Transactional
    void getServiceStatus() throws Exception {
        // Initialize the database
        serviceStatusRepository.saveAndFlush(serviceStatus);

        // Get the serviceStatus
        restServiceStatusMockMvc
            .perform(get(ENTITY_API_URL_ID, serviceStatus.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(serviceStatus.getId().toString()))
            .andExpect(jsonPath("$.date").value(sameInstant(DEFAULT_DATE)))
            .andExpect(jsonPath("$.observations").value(DEFAULT_OBSERVATIONS))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()));
    }

    @Test
    @Transactional
    void getNonExistingServiceStatus() throws Exception {
        // Get the serviceStatus
        restServiceStatusMockMvc.perform(get(ENTITY_API_URL_ID, UUID.randomUUID().toString())).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingServiceStatus() throws Exception {
        // Initialize the database
        serviceStatusRepository.saveAndFlush(serviceStatus);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the serviceStatus
        ServiceStatus updatedServiceStatus = serviceStatusRepository.findById(serviceStatus.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedServiceStatus are not directly saved in db
        em.detach(updatedServiceStatus);
        updatedServiceStatus.date(UPDATED_DATE).observations(UPDATED_OBSERVATIONS).status(UPDATED_STATUS);
        ServiceStatusDTO serviceStatusDTO = serviceStatusMapper.toDto(updatedServiceStatus);

        restServiceStatusMockMvc
            .perform(
                put(ENTITY_API_URL_ID, serviceStatusDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(serviceStatusDTO))
            )
            .andExpect(status().isOk());

        // Validate the ServiceStatus in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedServiceStatusToMatchAllProperties(updatedServiceStatus);
    }

    @Test
    @Transactional
    void putNonExistingServiceStatus() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        serviceStatus.setId(UUID.randomUUID());

        // Create the ServiceStatus
        ServiceStatusDTO serviceStatusDTO = serviceStatusMapper.toDto(serviceStatus);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restServiceStatusMockMvc
            .perform(
                put(ENTITY_API_URL_ID, serviceStatusDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(serviceStatusDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ServiceStatus in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchServiceStatus() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        serviceStatus.setId(UUID.randomUUID());

        // Create the ServiceStatus
        ServiceStatusDTO serviceStatusDTO = serviceStatusMapper.toDto(serviceStatus);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restServiceStatusMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(serviceStatusDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ServiceStatus in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamServiceStatus() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        serviceStatus.setId(UUID.randomUUID());

        // Create the ServiceStatus
        ServiceStatusDTO serviceStatusDTO = serviceStatusMapper.toDto(serviceStatus);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restServiceStatusMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(serviceStatusDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the ServiceStatus in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateServiceStatusWithPatch() throws Exception {
        // Initialize the database
        serviceStatusRepository.saveAndFlush(serviceStatus);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the serviceStatus using partial update
        ServiceStatus partialUpdatedServiceStatus = new ServiceStatus();
        partialUpdatedServiceStatus.setId(serviceStatus.getId());

        partialUpdatedServiceStatus.status(UPDATED_STATUS);

        restServiceStatusMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedServiceStatus.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedServiceStatus))
            )
            .andExpect(status().isOk());

        // Validate the ServiceStatus in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertServiceStatusUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedServiceStatus, serviceStatus),
            getPersistedServiceStatus(serviceStatus)
        );
    }

    @Test
    @Transactional
    void fullUpdateServiceStatusWithPatch() throws Exception {
        // Initialize the database
        serviceStatusRepository.saveAndFlush(serviceStatus);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the serviceStatus using partial update
        ServiceStatus partialUpdatedServiceStatus = new ServiceStatus();
        partialUpdatedServiceStatus.setId(serviceStatus.getId());

        partialUpdatedServiceStatus.date(UPDATED_DATE).observations(UPDATED_OBSERVATIONS).status(UPDATED_STATUS);

        restServiceStatusMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedServiceStatus.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedServiceStatus))
            )
            .andExpect(status().isOk());

        // Validate the ServiceStatus in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertServiceStatusUpdatableFieldsEquals(partialUpdatedServiceStatus, getPersistedServiceStatus(partialUpdatedServiceStatus));
    }

    @Test
    @Transactional
    void patchNonExistingServiceStatus() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        serviceStatus.setId(UUID.randomUUID());

        // Create the ServiceStatus
        ServiceStatusDTO serviceStatusDTO = serviceStatusMapper.toDto(serviceStatus);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restServiceStatusMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, serviceStatusDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(serviceStatusDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ServiceStatus in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchServiceStatus() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        serviceStatus.setId(UUID.randomUUID());

        // Create the ServiceStatus
        ServiceStatusDTO serviceStatusDTO = serviceStatusMapper.toDto(serviceStatus);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restServiceStatusMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(serviceStatusDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ServiceStatus in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamServiceStatus() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        serviceStatus.setId(UUID.randomUUID());

        // Create the ServiceStatus
        ServiceStatusDTO serviceStatusDTO = serviceStatusMapper.toDto(serviceStatus);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restServiceStatusMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(serviceStatusDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the ServiceStatus in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteServiceStatus() throws Exception {
        // Initialize the database
        serviceStatusRepository.saveAndFlush(serviceStatus);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the serviceStatus
        restServiceStatusMockMvc
            .perform(delete(ENTITY_API_URL_ID, serviceStatus.getId().toString()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return serviceStatusRepository.count();
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

    protected ServiceStatus getPersistedServiceStatus(ServiceStatus serviceStatus) {
        return serviceStatusRepository.findById(serviceStatus.getId()).orElseThrow();
    }

    protected void assertPersistedServiceStatusToMatchAllProperties(ServiceStatus expectedServiceStatus) {
        assertServiceStatusAllPropertiesEquals(expectedServiceStatus, getPersistedServiceStatus(expectedServiceStatus));
    }

    protected void assertPersistedServiceStatusToMatchUpdatableProperties(ServiceStatus expectedServiceStatus) {
        assertServiceStatusAllUpdatablePropertiesEquals(expectedServiceStatus, getPersistedServiceStatus(expectedServiceStatus));
    }
}
