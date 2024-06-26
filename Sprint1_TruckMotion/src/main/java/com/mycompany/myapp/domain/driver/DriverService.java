package com.mycompany.myapp.domain.driver;

import com.mycompany.myapp.application.controller.errors.BadRequestAlertException;
import com.mycompany.myapp.infrastructure.repository.jpa.DriverRepository;
import com.mycompany.myapp.domain.driver.dto.DriverDTO;
import com.mycompany.myapp.domain.driver.mapper.DriverMapper;
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
 * Service Implementation for managing {@link Driver}.
 */
@Service
@Transactional
public class DriverService {

    private final Logger log = LoggerFactory.getLogger(DriverService.class);

    private final IDriverRepository driverRepository;

    private static final String ENTITY_NAME = "driver";

    public DriverService(IDriverRepository driverRepository) {
        this.driverRepository = driverRepository;
    }

    /**
     * Save a driver.
     *
     * @param driverDTO the entity to save.
     * @return the persisted entity.
     */
    public DriverDTO save(DriverDTO driverDTO) {
        log.debug("Request to save Driver : {}", driverDTO);
        Driver driver = DriverMapper.toEntity(driverDTO);
        driver = driverRepository.save(driver);
        return DriverMapper.toDto(driver);
    }

    /**
     * Update a driver.
     *
     * @param driverDTO the entity to save.
     * @return the persisted entity.
     */
    public DriverDTO update(DriverDTO driverDTO) {
        log.debug("Request to update Driver : {}", driverDTO);
        DriverId id = new DriverId(driverDTO.getId());
        if (!driverRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }
        Driver driver = DriverMapper.toEntity(driverDTO);
        driver = driverRepository.save(driver);
        return DriverMapper.toDto(driver);
    }

    /**
     * Partially update a driver.
     *
     * @param driverDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<DriverDTO> partialUpdate(DriverDTO driverDTO) {
        log.debug("Request to partially update Driver : {}", driverDTO);
        DriverId id = new DriverId(driverDTO.getId());
        if (!driverRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        return driverRepository
            .findById(id)
            .map(existingDriver -> {
                DriverMapper.partialUpdate(existingDriver, driverDTO);

                return existingDriver;
            })
            .map(driverRepository::save)
            .map(DriverMapper::toDto);
    }

    /**
     * Get all the drivers.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<DriverDTO> findAll() {
        log.debug("Request to get all Drivers");
        return driverRepository.findAll().stream().map(DriverMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     *  Get all the drivers where Transport is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<DriverDTO> findAllWhereTransportIsNull() {
        log.debug("Request to get all drivers where Transport is null");
        return StreamSupport.stream(driverRepository.findAll().spliterator(), false)
            .filter(driver -> driver.getTransport() == null)
            .map(DriverMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one driver by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<DriverDTO> findOne(UUID id) {
        log.debug("Request to get Driver : {}", id);
        DriverId dId = new DriverId(id);
        return driverRepository.findById(dId).map(DriverMapper::toDto);
    }

    /**
     * Delete the driver by id.
     *
     * @param id the id of the entity.
     */
    public void delete(UUID id) {
        log.debug("Request to delete Driver : {}", id);
        DriverId dId = new DriverId(id);
        driverRepository.deleteById(dId);
    }
}
