package com.mycompany.myapp.application.controller;

import static com.mycompany.myapp.domain.transport.TransportAsserts.*;
import static com.mycompany.myapp.application.controller.TestUtil.createUpdateProxyForBean;
import static com.mycompany.myapp.application.controller.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.transport.ITransportRepository;
import com.mycompany.myapp.domain.transport.Transport;
import com.mycompany.myapp.domain.transport.dto.TransportDTO;
import com.mycompany.myapp.domain.transport.mapper.TransportMapper;
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
 * Integration tests for the {@link TransportController} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TransportControllerIT {

    private static final ZonedDateTime DEFAULT_START_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_START_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_END_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_END_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String ENTITY_API_URL = "/api/transports";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private ObjectMapper om;

    @Autowired
    private ITransportRepository transportRepository;

    @Autowired
    private TransportMapper transportMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTransportMockMvc;

    private Transport transport;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Transport createEntity(EntityManager em) {
        Transport transport = new Transport().startTime(DEFAULT_START_TIME).endTime(DEFAULT_END_TIME);
        return transport;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Transport createUpdatedEntity(EntityManager em) {
        Transport transport = new Transport().startTime(UPDATED_START_TIME).endTime(UPDATED_END_TIME);
        return transport;
    }

    @BeforeEach
    public void initTest() {
        transport = createEntity(em);
    }

    @Test
    @Transactional
    void createTransport() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Transport
        TransportDTO transportDTO = transportMapper.toDto(transport);
        var returnedTransportDTO = om.readValue(
            restTransportMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(transportDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            TransportDTO.class
        );

        // Validate the Transport in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedTransport = transportMapper.toEntity(returnedTransportDTO);
        assertTransportUpdatableFieldsEquals(returnedTransport, getPersistedTransport(returnedTransport));
    }

    @Test
    @Transactional
    void createTransportWithExistingId() throws Exception {
        // Create the Transport with an existing ID
        transportRepository.saveAndFlush(transport);
        TransportDTO transportDTO = transportMapper.toDto(transport);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTransportMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(transportDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Transport in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllTransports() throws Exception {
        // Initialize the database
        transportRepository.saveAndFlush(transport);

        // Get all the transportList
        restTransportMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(transport.getId().toString())))
            .andExpect(jsonPath("$.[*].startTime").value(hasItem(sameInstant(DEFAULT_START_TIME))))
            .andExpect(jsonPath("$.[*].endTime").value(hasItem(sameInstant(DEFAULT_END_TIME))));
    }

    @Test
    @Transactional
    void getTransport() throws Exception {
        // Initialize the database
        transportRepository.saveAndFlush(transport);

        // Get the transport
        restTransportMockMvc
            .perform(get(ENTITY_API_URL_ID, transport.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(transport.getId().toString()))
            .andExpect(jsonPath("$.startTime").value(sameInstant(DEFAULT_START_TIME)))
            .andExpect(jsonPath("$.endTime").value(sameInstant(DEFAULT_END_TIME)));
    }

    @Test
    @Transactional
    void getNonExistingTransport() throws Exception {
        // Get the transport
        restTransportMockMvc.perform(get(ENTITY_API_URL_ID, UUID.randomUUID().toString())).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingTransport() throws Exception {
        // Initialize the database
        transportRepository.saveAndFlush(transport);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the transport
        Transport updatedTransport = transportRepository.findById(transport.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedTransport are not directly saved in db
        em.detach(updatedTransport);
        updatedTransport.startTime(UPDATED_START_TIME).endTime(UPDATED_END_TIME);
        TransportDTO transportDTO = transportMapper.toDto(updatedTransport);

        restTransportMockMvc
            .perform(
                put(ENTITY_API_URL_ID, transportDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(transportDTO))
            )
            .andExpect(status().isOk());

        // Validate the Transport in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedTransportToMatchAllProperties(updatedTransport);
    }

    @Test
    @Transactional
    void putNonExistingTransport() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        transport.setId(UUID.randomUUID());

        // Create the Transport
        TransportDTO transportDTO = transportMapper.toDto(transport);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTransportMockMvc
            .perform(
                put(ENTITY_API_URL_ID, transportDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(transportDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Transport in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTransport() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        transport.setId(UUID.randomUUID());

        // Create the Transport
        TransportDTO transportDTO = transportMapper.toDto(transport);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTransportMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(transportDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Transport in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTransport() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        transport.setId(UUID.randomUUID());

        // Create the Transport
        TransportDTO transportDTO = transportMapper.toDto(transport);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTransportMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(transportDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Transport in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTransportWithPatch() throws Exception {
        // Initialize the database
        transportRepository.saveAndFlush(transport);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the transport using partial update
        Transport partialUpdatedTransport = new Transport();
        partialUpdatedTransport.setId(transport.getId());

        partialUpdatedTransport.startTime(UPDATED_START_TIME);

        restTransportMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTransport.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedTransport))
            )
            .andExpect(status().isOk());

        // Validate the Transport in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertTransportUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedTransport, transport),
            getPersistedTransport(transport)
        );
    }

    @Test
    @Transactional
    void fullUpdateTransportWithPatch() throws Exception {
        // Initialize the database
        transportRepository.saveAndFlush(transport);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the transport using partial update
        Transport partialUpdatedTransport = new Transport();
        partialUpdatedTransport.setId(transport.getId());

        partialUpdatedTransport.startTime(UPDATED_START_TIME).endTime(UPDATED_END_TIME);

        restTransportMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTransport.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedTransport))
            )
            .andExpect(status().isOk());

        // Validate the Transport in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertTransportUpdatableFieldsEquals(partialUpdatedTransport, getPersistedTransport(partialUpdatedTransport));
    }

    @Test
    @Transactional
    void patchNonExistingTransport() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        transport.setId(UUID.randomUUID());

        // Create the Transport
        TransportDTO transportDTO = transportMapper.toDto(transport);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTransportMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, transportDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(transportDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Transport in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTransport() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        transport.setId(UUID.randomUUID());

        // Create the Transport
        TransportDTO transportDTO = transportMapper.toDto(transport);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTransportMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(transportDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Transport in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTransport() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        transport.setId(UUID.randomUUID());

        // Create the Transport
        TransportDTO transportDTO = transportMapper.toDto(transport);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTransportMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(transportDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Transport in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTransport() throws Exception {
        // Initialize the database
        transportRepository.saveAndFlush(transport);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the transport
        restTransportMockMvc
            .perform(delete(ENTITY_API_URL_ID, transport.getId().toString()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return transportRepository.count();
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

    protected Transport getPersistedTransport(Transport transport) {
        return transportRepository.findById(transport.getId()).orElseThrow();
    }

    protected void assertPersistedTransportToMatchAllProperties(Transport expectedTransport) {
        assertTransportAllPropertiesEquals(expectedTransport, getPersistedTransport(expectedTransport));
    }

    protected void assertPersistedTransportToMatchUpdatableProperties(Transport expectedTransport) {
        assertTransportAllUpdatablePropertiesEquals(expectedTransport, getPersistedTransport(expectedTransport));
    }
}
