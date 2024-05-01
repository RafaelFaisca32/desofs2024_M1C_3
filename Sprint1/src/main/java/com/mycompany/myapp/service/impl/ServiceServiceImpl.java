package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.Service;
import com.mycompany.myapp.repository.ServiceRepository;
import com.mycompany.myapp.service.ServiceService;
import com.mycompany.myapp.service.dto.ServiceDTO;
import com.mycompany.myapp.service.mapper.ServiceMapper;
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
 * Service Implementation for managing {@link com.mycompany.myapp.domain.Service}.
 */
@Service
@Transactional
public class ServiceServiceImpl implements ServiceService {

    private final Logger log = LoggerFactory.getLogger(ServiceServiceImpl.class);

    private final ServiceRepository serviceRepository;

    private final ServiceMapper serviceMapper;

    public ServiceServiceImpl(ServiceRepository serviceRepository, ServiceMapper serviceMapper) {
        this.serviceRepository = serviceRepository;
        this.serviceMapper = serviceMapper;
    }

    @Override
    public ServiceDTO save(ServiceDTO serviceDTO) {
        log.debug("Request to save Service : {}", serviceDTO);
        Service service = serviceMapper.toEntity(serviceDTO);
        service = serviceRepository.save(service);
        return serviceMapper.toDto(service);
    }

    @Override
    public ServiceDTO update(ServiceDTO serviceDTO) {
        log.debug("Request to update Service : {}", serviceDTO);
        Service service = serviceMapper.toEntity(serviceDTO);
        service = serviceRepository.save(service);
        return serviceMapper.toDto(service);
    }

    @Override
    public Optional<ServiceDTO> partialUpdate(ServiceDTO serviceDTO) {
        log.debug("Request to partially update Service : {}", serviceDTO);

        return serviceRepository
            .findById(serviceDTO.getId())
            .map(existingService -> {
                serviceMapper.partialUpdate(existingService, serviceDTO);

                return existingService;
            })
            .map(serviceRepository::save)
            .map(serviceMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ServiceDTO> findAll() {
        log.debug("Request to get all Services");
        return serviceRepository.findAll().stream().map(serviceMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     *  Get all the services where Transport is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<ServiceDTO> findAllWhereTransportIsNull() {
        log.debug("Request to get all services where Transport is null");
        return StreamSupport.stream(serviceRepository.findAll().spliterator(), false)
            .filter(service -> service.getTransport() == null)
            .map(serviceMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ServiceDTO> findOne(UUID id) {
        log.debug("Request to get Service : {}", id);
        return serviceRepository.findById(id).map(serviceMapper::toDto);
    }

    @Override
    public void delete(UUID id) {
        log.debug("Request to delete Service : {}", id);
        serviceRepository.deleteById(id);
    }
}
