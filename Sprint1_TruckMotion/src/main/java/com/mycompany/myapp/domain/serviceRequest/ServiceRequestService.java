package com.mycompany.myapp.domain.serviceRequest;

import com.mycompany.myapp.application.controller.errors.BadRequestAlertException;
import com.mycompany.myapp.infrastructure.repository.jpa.ServiceRequestRepository;
import com.mycompany.myapp.domain.serviceRequest.dto.ServiceRequestDTO;
import com.mycompany.myapp.domain.serviceRequest.mapper.ServiceRequestMapper;
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
 * Service Implementation for managing {@link ServiceRequest}.
 */
@Service
@Transactional
public class ServiceRequestService {

    private final Logger log = LoggerFactory.getLogger(ServiceRequestService.class);

    private final IServiceRequestRepository serviceRequestRepository;


    private static final String ENTITY_NAME = "serviceRequest";

    public ServiceRequestService(IServiceRequestRepository serviceRequestRepository) {
        this.serviceRequestRepository = serviceRequestRepository;
    }

    /**
     * Save a serviceRequest.
     *
     * @param serviceRequestDTO the entity to save.
     * @return the persisted entity.
     */
    public ServiceRequestDTO save(ServiceRequestDTO serviceRequestDTO) {
        log.debug("Request to save ServiceRequest : {}", serviceRequestDTO);
        ServiceRequest serviceRequest = ServiceRequestMapper.toEntity(serviceRequestDTO);
        serviceRequest = serviceRequestRepository.save(serviceRequest);
        return ServiceRequestMapper.toDto(serviceRequest);
    }

    /**
     * Update a serviceRequest.
     *
     * @param serviceRequestDTO the entity to save.
     * @return the persisted entity.
     */
    public ServiceRequestDTO update(ServiceRequestDTO serviceRequestDTO) {
        log.debug("Request to update ServiceRequest : {}", serviceRequestDTO);

        if (!serviceRequestRepository.existsById(new ServiceRequestId(serviceRequestDTO.getId()))) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ServiceRequest serviceRequest = ServiceRequestMapper.toEntity(serviceRequestDTO);
        serviceRequest = serviceRequestRepository.save(serviceRequest);
        return ServiceRequestMapper.toDto(serviceRequest);
    }

    /**
     * Partially update a serviceRequest.
     *
     * @param serviceRequestDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ServiceRequestDTO> partialUpdate(ServiceRequestDTO serviceRequestDTO) {
        log.debug("Request to partially update ServiceRequest : {}", serviceRequestDTO);

        if (!serviceRequestRepository.existsById(new ServiceRequestId(serviceRequestDTO.getId()))) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }


        return serviceRequestRepository
            .findById(new ServiceRequestId(serviceRequestDTO.getId()))
            .map(existingServiceRequest -> {
                ServiceRequestMapper.partialUpdate(existingServiceRequest, serviceRequestDTO);

                return existingServiceRequest;
            })
            .map(serviceRequestRepository::save)
            .map(ServiceRequestMapper::toDto);
    }

    /**
     * Get all the serviceRequests.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<ServiceRequestDTO> findAll() {
        log.debug("Request to get all ServiceRequests");
        List<ServiceRequest> list = serviceRequestRepository
            .findAll();
        return list
            .stream()
            .map(ServiceRequestMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     *  Get all the serviceRequests where Transport is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<ServiceRequestDTO> findAllWhereTransportIsNull() {
        log.debug("Request to get all serviceRequests where Transport is null");
        return StreamSupport.stream(serviceRequestRepository.findAll().spliterator(), false)
            .filter(serviceRequest -> serviceRequest.getTransport() == null)
            .map(ServiceRequestMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one serviceRequest by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ServiceRequestDTO> findOne(UUID id) {
        log.debug("Request to get ServiceRequest : {}", id);
        return serviceRequestRepository.findById(new ServiceRequestId(id)).map(ServiceRequestMapper::toDto);
    }

    /**
     * Delete the serviceRequest by id.
     *
     * @param id the id of the entity.
     */
    public void delete(UUID id) {
        log.debug("Request to delete ServiceRequest : {}", id);
        serviceRequestRepository.deleteById(new ServiceRequestId(id));
    }
}
