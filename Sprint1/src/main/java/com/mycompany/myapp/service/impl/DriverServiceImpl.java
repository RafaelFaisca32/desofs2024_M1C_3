package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.Driver;
import com.mycompany.myapp.repository.DriverRepository;
import com.mycompany.myapp.service.DriverService;
import com.mycompany.myapp.service.dto.DriverDTO;
import com.mycompany.myapp.service.mapper.DriverMapper;
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
 * Service Implementation for managing {@link com.mycompany.myapp.domain.Driver}.
 */
@Service
@Transactional
public class DriverServiceImpl implements DriverService {

    private final Logger log = LoggerFactory.getLogger(DriverServiceImpl.class);

    private final DriverRepository driverRepository;

    private final DriverMapper driverMapper;

    public DriverServiceImpl(DriverRepository driverRepository, DriverMapper driverMapper) {
        this.driverRepository = driverRepository;
        this.driverMapper = driverMapper;
    }

    @Override
    public DriverDTO save(DriverDTO driverDTO) {
        log.debug("Request to save Driver : {}", driverDTO);
        Driver driver = driverMapper.toEntity(driverDTO);
        driver = driverRepository.save(driver);
        return driverMapper.toDto(driver);
    }

    @Override
    public DriverDTO update(DriverDTO driverDTO) {
        log.debug("Request to update Driver : {}", driverDTO);
        Driver driver = driverMapper.toEntity(driverDTO);
        driver = driverRepository.save(driver);
        return driverMapper.toDto(driver);
    }

    @Override
    public Optional<DriverDTO> partialUpdate(DriverDTO driverDTO) {
        log.debug("Request to partially update Driver : {}", driverDTO);

        return driverRepository
            .findById(driverDTO.getId())
            .map(existingDriver -> {
                driverMapper.partialUpdate(existingDriver, driverDTO);

                return existingDriver;
            })
            .map(driverRepository::save)
            .map(driverMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<DriverDTO> findAll() {
        log.debug("Request to get all Drivers");
        return driverRepository.findAll().stream().map(driverMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
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
            .map(driverMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<DriverDTO> findOne(UUID id) {
        log.debug("Request to get Driver : {}", id);
        return driverRepository.findById(id).map(driverMapper::toDto);
    }

    @Override
    public void delete(UUID id) {
        log.debug("Request to delete Driver : {}", id);
        driverRepository.deleteById(id);
    }
}
