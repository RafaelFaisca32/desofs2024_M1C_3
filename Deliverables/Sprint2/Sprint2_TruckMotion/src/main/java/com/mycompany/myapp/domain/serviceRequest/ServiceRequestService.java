package com.mycompany.myapp.domain.serviceRequest;

import com.mycompany.myapp.application.controller.errors.BadRequestAlertException;
import com.mycompany.myapp.domain.driver.Driver;
import com.mycompany.myapp.domain.driver.DriverId;
import com.mycompany.myapp.domain.driver.IDriverRepository;
import com.mycompany.myapp.domain.driver.dto.DriverDTO;
import com.mycompany.myapp.domain.driver.mapper.DriverMapper;
import com.mycompany.myapp.domain.location.dto.LocationDTO;
import com.mycompany.myapp.domain.serviceRequest.dto.ServiceStatusDTO;
import com.mycompany.myapp.domain.serviceRequest.mapper.ServiceStatusMapper;
import com.mycompany.myapp.domain.transport.TransportService;
import com.mycompany.myapp.domain.transport.dto.TransportDTO;
import com.mycompany.myapp.infrastructure.repository.jpa.DriverRepository;
import com.mycompany.myapp.infrastructure.repository.jpa.ServiceRequestRepository;
import com.mycompany.myapp.domain.serviceRequest.dto.ServiceRequestDTO;
import com.mycompany.myapp.domain.serviceRequest.mapper.ServiceRequestMapper;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
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

    private final TransportService transportService;
    private static final String ENTITY_NAME = "serviceRequest";
    private final IDriverRepository driverRepository;

    public ServiceRequestService(IServiceRequestRepository serviceRequestRepository, TransportService transportService,
                                 IDriverRepository driverRepository) {
        this.serviceRequestRepository = serviceRequestRepository;
        this.transportService = transportService;
        this.driverRepository = driverRepository;
    }

    /**
     * Save a serviceRequest.
     *
     * @param serviceRequestDTO the entity to save.
     * @return the persisted entity.
     */
    public ServiceRequestDTO save(ServiceRequestDTO serviceRequestDTO) {
        log.debug("Request to save ServiceRequest : {}", serviceRequestDTO);

        ServiceRequest serviceRequest1 = ServiceRequestMapper.toEntity(serviceRequestDTO);
        serviceRequestDTO.setId(serviceRequest1.getId().value());
        if (serviceRequestDTO.getStatus() == null){
            serviceRequestDTO.setStatus(new ServiceStatusDTO("",Status.PENDING,serviceRequestDTO));
            serviceRequest1.getServiceStatuses().add(ServiceStatusMapper.toEntity(serviceRequestDTO.getStatus()));
        } else {
            if (serviceRequestDTO.getStatus().getId() == null){
                serviceRequestDTO.setStatus(new ServiceStatusDTO("",serviceRequestDTO.getStatus().getStatus(), serviceRequestDTO));
                serviceRequest1.getServiceStatuses().add(ServiceStatusMapper.toEntity(serviceRequestDTO.getStatus()));
            }
        }
        ServiceRequestMapper.partialUpdate(serviceRequest1, serviceRequestDTO);

        ServiceRequest serviceRequest = serviceRequestRepository.save(serviceRequest1);
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
        ServiceRequest serviceRequest2 = ServiceRequestMapper.toEntity(serviceRequestDTO);
        ServiceRequest serviceRequest1 = serviceRequestRepository.findById(new ServiceRequestId(serviceRequestDTO.getId())).get();
        if (serviceRequestDTO.getStatus() != null) {
            serviceRequestDTO.setStatus(new ServiceStatusDTO("", serviceRequestDTO.getStatus().getStatus(), new ServiceRequestDTO(serviceRequestDTO.getId())));
            serviceRequest1.getServiceStatuses().add(ServiceStatusMapper.toEntity(serviceRequestDTO.getStatus()));
        }

        ServiceRequestMapper.partialUpdate(serviceRequest1, serviceRequestDTO);
        serviceRequest1.setLocation(serviceRequest2.getLocation());
        serviceRequest1.setCustomer(serviceRequest2.getCustomer());
        ServiceRequest serviceRequest = serviceRequestRepository.save(serviceRequest1);
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
    public void updateRequestServiceStatus(UUID id, boolean isApproved, UUID driverId, String startTime,String endTime) {
        Optional<ServiceRequest> service = serviceRequestRepository.findById(new ServiceRequestId(id));
        if(service.isPresent()){
            ServiceRequest req = service.get();
            ServiceRequestDTO reqDTO = ServiceRequestMapper.toDto(req);
            if(isApproved){
                req.updateRequestStatus(new ServiceStatusDTO(null,Status.ACTIVE,reqDTO));
                Optional<Driver> driver = driverRepository.findById(new DriverId(driverId));
                if(driver.isPresent()) {
                    DriverDTO driverDTO = DriverMapper.toDto(driver.get());
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");

                    ZoneId zoneId = ZoneId.systemDefault();

                    ZonedDateTime zonedStartTime = ZonedDateTime.of(LocalDateTime.parse(startTime, formatter), zoneId);
                    ZonedDateTime zonedEndTime = ZonedDateTime.of(LocalDateTime.parse(endTime, formatter), zoneId);
                    transportService.save(new TransportDTO(null,zonedStartTime, zonedEndTime, reqDTO.getLocation(),driverDTO, reqDTO));
                }
            }else{
                req.updateRequestStatus(new ServiceStatusDTO(null,Status.CANCELED,reqDTO) );
            }
            serviceRequestRepository.save(req);
        }
    }

    @Transactional(readOnly = true)
    public List<ServiceRequestDTO> getByUserId(Long userId) {
        log.debug("Request to get Service Requests by UserId : {}", userId);
        return StreamSupport.stream(serviceRequestRepository.getByUserId(userId).spliterator(), false)
            .filter(serviceRequest -> serviceRequest.getTransport() == null)
            .map(ServiceRequestMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }
}
