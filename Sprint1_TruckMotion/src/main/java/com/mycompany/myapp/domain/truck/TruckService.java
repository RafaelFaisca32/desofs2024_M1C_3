package com.mycompany.myapp.domain.truck;

import com.mycompany.myapp.application.controller.errors.BadRequestAlertException;
import com.mycompany.myapp.infrastructure.repository.jpa.TruckRepository;
import com.mycompany.myapp.domain.truck.dto.TruckDTO;
import com.mycompany.myapp.domain.truck.mapper.TruckMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.mycompany.myapp.domain.truck}.
 */
@Service
@Transactional
public class TruckService {

    private final Logger log = LoggerFactory.getLogger(TruckService.class);

    private final ITruckRepository truckRepository;

    private final TruckMapper truckMapper;

    private static final String ENTITY_NAME = "truck";

    public TruckService(ITruckRepository truckRepository, TruckMapper truckMapper) {
        this.truckRepository = truckRepository;
        this.truckMapper = truckMapper;
    }

    /**
     * Save a truck.
     *
     * @param truckDTO the entity to save.
     * @return the persisted entity.
     */
    public TruckDTO save(TruckDTO truckDTO) {
        log.debug("Request to save Truck : {}", truckDTO);
        Truck truck = truckMapper.toEntity(truckDTO);
        truck = truckRepository.save(truck);
        return truckMapper.toDto(truck);
    }

    /**
     * Update a truck.
     *
     * @param truckDTO the entity to save.
     * @return the persisted entity.
     */
    public TruckDTO update(TruckDTO truckDTO) {
        log.debug("Request to update Truck : {}", truckDTO);

        if (!truckRepository.existsById(truckDTO.getId())) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Truck truck = truckMapper.toEntity(truckDTO);
        truck = truckRepository.save(truck);
        return truckMapper.toDto(truck);
    }

    /**
     * Partially update a truck.
     *
     * @param truckDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<TruckDTO> partialUpdate(TruckDTO truckDTO) {
        log.debug("Request to partially update Truck : {}", truckDTO);
        if (!truckRepository.existsById(truckDTO.getId())) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }
        return truckRepository
            .findById(truckDTO.getId())
            .map(existingTruck -> {
                truckMapper.partialUpdate(existingTruck, truckDTO);

                return existingTruck;
            })
            .map(truckRepository::save)
            .map(truckMapper::toDto);
    }

    /**
     * Get all the trucks.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<TruckDTO> findAll() {
        log.debug("Request to get all Trucks");
        return truckRepository.findAll().stream().map(truckMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     *  Get all the trucks where Driver is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<TruckDTO> findAllWhereDriverIsNull() {
        log.debug("Request to get all trucks where Driver is null");
        return StreamSupport.stream(truckRepository.findAll().spliterator(), false)
            .filter(truck -> truck.getDriver() == null)
            .map(truckMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one truck by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<TruckDTO> findOne(UUID id) {
        log.debug("Request to get Truck : {}", id);
        return truckRepository.findById(id).map(truckMapper::toDto);
    }

    /**
     * Delete the truck by id.
     *
     * @param id the id of the entity.
     */
    public void delete(UUID id) {
        log.debug("Request to delete Truck : {}", id);
        truckRepository.deleteById(id);
    }
}
