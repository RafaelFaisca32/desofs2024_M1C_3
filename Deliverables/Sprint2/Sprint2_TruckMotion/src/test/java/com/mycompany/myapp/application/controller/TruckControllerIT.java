package com.mycompany.myapp.application.controller;

import static com.mycompany.myapp.domain.truck.TruckAsserts.*;
import static com.mycompany.myapp.application.controller.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.transport.TransportId;
import com.mycompany.myapp.domain.truck.*;
import com.mycompany.myapp.domain.truck.dto.TruckDTO;
import com.mycompany.myapp.domain.truck.mapper.TruckMapper;
import jakarta.persistence.EntityManager;
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
 * Integration tests for the {@link TruckController} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TruckControllerIT {

    private static final String DEFAULT_MAKE = "AAAAAAAAAA";
    private static final String UPDATED_MAKE = "BBBBBBBBBB";

    private static final String DEFAULT_MODEL = "AAAAAAAAAA";
    private static final String UPDATED_MODEL = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/trucks";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private ObjectMapper om;

    @Autowired
    private ITruckRepository truckRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTruckMockMvc;

    private Truck truck;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Truck createEntity(EntityManager em) {
        Truck truck = new Truck();
        truck.updateMake(new Make(DEFAULT_MAKE));
        truck.updateModel(new Model(DEFAULT_MODEL));
        return truck;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Truck createUpdatedEntity(EntityManager em) {
        Truck truck = new Truck();
        truck.updateMake(new Make(UPDATED_MAKE));
        truck.updateModel(new Model(UPDATED_MODEL));
        return truck;
    }

    @BeforeEach
    public void initTest() {
        truck = createEntity(em);
    }

    @Test
    @Transactional
    void createTruck() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Truck
        TruckDTO truckDTO = TruckMapper.toDto(truck);
        var returnedTruckDTO = om.readValue(
            restTruckMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(truckDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            TruckDTO.class
        );

        // Validate the Truck in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedTruck = TruckMapper.toEntity(returnedTruckDTO);
        assertTruckUpdatableFieldsEquals(returnedTruck, getPersistedTruck(returnedTruck));
    }

    @Test
    @Transactional
    void createTruckWithExistingId() throws Exception {
        // Create the Truck with an existing ID
        truckRepository.saveAndFlush(truck);
        TruckDTO truckDTO = TruckMapper.toDto(truck);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTruckMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(truckDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Truck in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllTrucks() throws Exception {
        // Initialize the database
        truckRepository.saveAndFlush(truck);

        // Get all the truckList
        restTruckMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(truck.getId().toString())))
            .andExpect(jsonPath("$.[*].make").value(hasItem(DEFAULT_MAKE)))
            .andExpect(jsonPath("$.[*].model").value(hasItem(DEFAULT_MODEL)));
    }

    @Test
    @Transactional
    void getTruck() throws Exception {
        // Initialize the database
        truckRepository.saveAndFlush(truck);

        // Get the truck
        restTruckMockMvc
            .perform(get(ENTITY_API_URL_ID, truck.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(truck.getId().toString()))
            .andExpect(jsonPath("$.make").value(DEFAULT_MAKE))
            .andExpect(jsonPath("$.model").value(DEFAULT_MODEL));
    }

    @Test
    @Transactional
    void getNonExistingTruck() throws Exception {
        // Get the truck
        restTruckMockMvc.perform(get(ENTITY_API_URL_ID, UUID.randomUUID().toString())).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingTruck() throws Exception {
        // Initialize the database
        truckRepository.saveAndFlush(truck);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the truck
        Truck updatedTruck = truckRepository.findById(truck.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedTruck are not directly saved in db
        em.detach(updatedTruck);
        updatedTruck.updateMake(new Make(UPDATED_MAKE));
        updatedTruck.updateModel(new Model(UPDATED_MODEL));
        TruckDTO truckDTO = TruckMapper.toDto(updatedTruck);

        restTruckMockMvc
            .perform(
                put(ENTITY_API_URL_ID, truckDTO.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(truckDTO))
            )
            .andExpect(status().isOk());

        // Validate the Truck in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedTruckToMatchAllProperties(updatedTruck);
    }

    @Test
    @Transactional
    void putNonExistingTruck() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        truck = new Truck(new TruckId(),truck.getMake(),truck.getModel());

        // Create the Truck
        TruckDTO truckDTO = TruckMapper.toDto(truck);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTruckMockMvc
            .perform(
                put(ENTITY_API_URL_ID, truckDTO.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(truckDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Truck in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTruck() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        truck = new Truck(new TruckId(),truck.getMake(),truck.getModel());
        // Create the Truck
        TruckDTO truckDTO = TruckMapper.toDto(truck);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTruckMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(truckDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Truck in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTruck() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        truck = new Truck(new TruckId(),truck.getMake(),truck.getModel());

        // Create the Truck
        TruckDTO truckDTO = TruckMapper.toDto(truck);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTruckMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(truckDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Truck in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTruckWithPatch() throws Exception {
        // Initialize the database
        truckRepository.saveAndFlush(truck);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the truck using partial update
        Truck partialUpdatedTruck = new Truck(truck);

        partialUpdatedTruck.updateModel(new Model(UPDATED_MODEL));

        restTruckMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTruck.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedTruck))
            )
            .andExpect(status().isOk());

        // Validate the Truck in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertTruckUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedTruck, truck), getPersistedTruck(truck));
    }

    @Test
    @Transactional
    void fullUpdateTruckWithPatch() throws Exception {
        // Initialize the database
        truckRepository.saveAndFlush(truck);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the truck using partial update
        Truck partialUpdatedTruck = new Truck(truck);

        partialUpdatedTruck.updateMake(new Make(UPDATED_MAKE));
        partialUpdatedTruck.updateModel(new Model(UPDATED_MODEL));

        restTruckMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTruck.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedTruck))
            )
            .andExpect(status().isOk());

        // Validate the Truck in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertTruckUpdatableFieldsEquals(partialUpdatedTruck, getPersistedTruck(partialUpdatedTruck));
    }

    @Test
    @Transactional
    void patchNonExistingTruck() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        truck = new Truck(new TruckId(),truck.getMake(),truck.getModel());

        // Create the Truck
        TruckDTO truckDTO = TruckMapper.toDto(truck);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTruckMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, truckDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(truckDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Truck in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTruck() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        truck = new Truck(new TruckId(),truck.getMake(),truck.getModel());

        // Create the Truck
        TruckDTO truckDTO = TruckMapper.toDto(truck);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTruckMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(truckDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Truck in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTruck() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        truck = new Truck(new TruckId(),truck.getMake(),truck.getModel());

        // Create the Truck
        TruckDTO truckDTO = TruckMapper.toDto(truck);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTruckMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(truckDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Truck in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTruck() throws Exception {
        // Initialize the database
        truckRepository.saveAndFlush(truck);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the truck
        restTruckMockMvc
            .perform(delete(ENTITY_API_URL_ID, truck.getId().toString()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return truckRepository.count();
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

    protected Truck getPersistedTruck(Truck truck) {
        return truckRepository.findById(truck.getId()).orElseThrow();
    }

    protected void assertPersistedTruckToMatchAllProperties(Truck expectedTruck) {
        assertTruckAllPropertiesEquals(expectedTruck, getPersistedTruck(expectedTruck));
    }

    protected void assertPersistedTruckToMatchUpdatableProperties(Truck expectedTruck) {
        assertTruckAllUpdatablePropertiesEquals(expectedTruck, getPersistedTruck(expectedTruck));
    }
}
