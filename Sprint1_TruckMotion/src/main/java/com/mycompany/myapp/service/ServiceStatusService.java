package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.ServiceStatus;
import com.mycompany.myapp.repository.ServiceStatusRepository;
import com.mycompany.myapp.service.dto.ServiceStatusDTO;
import com.mycompany.myapp.service.mapper.ServiceStatusMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.mycompany.myapp.domain.ServiceStatus}.
 */
@Service
@Transactional
public class ServiceStatusService {

    private final Logger log = LoggerFactory.getLogger(ServiceStatusService.class);

    private final ServiceStatusRepository serviceStatusRepository;

    private final ServiceStatusMapper serviceStatusMapper;

    public ServiceStatusService(ServiceStatusRepository serviceStatusRepository, ServiceStatusMapper serviceStatusMapper) {
        this.serviceStatusRepository = serviceStatusRepository;
        this.serviceStatusMapper = serviceStatusMapper;
    }

    /**
     * Save a serviceStatus.
     *
     * @param serviceStatusDTO the entity to save.
     * @return the persisted entity.
     */
    public ServiceStatusDTO save(ServiceStatusDTO serviceStatusDTO) {
        log.debug("Request to save ServiceStatus : {}", serviceStatusDTO);
        ServiceStatus serviceStatus = serviceStatusMapper.toEntity(serviceStatusDTO);
        serviceStatus = serviceStatusRepository.save(serviceStatus);
        return serviceStatusMapper.toDto(serviceStatus);
    }

    /**
     * Update a serviceStatus.
     *
     * @param serviceStatusDTO the entity to save.
     * @return the persisted entity.
     */
    public ServiceStatusDTO update(ServiceStatusDTO serviceStatusDTO) {
        log.debug("Request to update ServiceStatus : {}", serviceStatusDTO);
        ServiceStatus serviceStatus = serviceStatusMapper.toEntity(serviceStatusDTO);
        serviceStatus = serviceStatusRepository.save(serviceStatus);
        return serviceStatusMapper.toDto(serviceStatus);
    }

    /**
     * Partially update a serviceStatus.
     *
     * @param serviceStatusDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ServiceStatusDTO> partialUpdate(ServiceStatusDTO serviceStatusDTO) {
        log.debug("Request to partially update ServiceStatus : {}", serviceStatusDTO);

        return serviceStatusRepository
            .findById(serviceStatusDTO.getId())
            .map(existingServiceStatus -> {
                serviceStatusMapper.partialUpdate(existingServiceStatus, serviceStatusDTO);

                return existingServiceStatus;
            })
            .map(serviceStatusRepository::save)
            .map(serviceStatusMapper::toDto);
    }

    /**
     * Get all the serviceStatuses.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<ServiceStatusDTO> findAll() {
        log.debug("Request to get all ServiceStatuses");
        return serviceStatusRepository.findAll().stream().map(serviceStatusMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one serviceStatus by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ServiceStatusDTO> findOne(UUID id) {
        log.debug("Request to get ServiceStatus : {}", id);
        return serviceStatusRepository.findById(id).map(serviceStatusMapper::toDto);
    }

    /**
     * Delete the serviceStatus by id.
     *
     * @param id the id of the entity.
     */
    public void delete(UUID id) {
        log.debug("Request to delete ServiceStatus : {}", id);
        serviceStatusRepository.deleteById(id);
    }
}
