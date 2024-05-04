package com.mycompany.myapp.application.controller;

import com.mycompany.myapp.domain.serviceRequest.ServiceRequest;
import com.mycompany.myapp.infrastructure.repository.jpa.ServiceRequestRepository;
import com.mycompany.myapp.domain.serviceRequest.ServiceRequestService;
import com.mycompany.myapp.domain.serviceRequest.dto.ServiceRequestDTO;
import com.mycompany.myapp.application.controller.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link ServiceRequest}.
 */
@RestController
@RequestMapping("/api/service-requests")
public class ServiceRequestController {

    private final Logger log = LoggerFactory.getLogger(ServiceRequestController.class);

    private static final String ENTITY_NAME = "serviceRequest";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ServiceRequestService serviceRequestService;

    private final ServiceRequestRepository serviceRequestRepository;

    public ServiceRequestController(ServiceRequestService serviceRequestService, ServiceRequestRepository serviceRequestRepository) {
        this.serviceRequestService = serviceRequestService;
        this.serviceRequestRepository = serviceRequestRepository;
    }

    /**
     * {@code POST  /service-requests} : Create a new serviceRequest.
     *
     * @param serviceRequestDTO the serviceRequestDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new serviceRequestDTO, or with status {@code 400 (Bad Request)} if the serviceRequest has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<ServiceRequestDTO> createServiceRequest(@RequestBody ServiceRequestDTO serviceRequestDTO)
        throws URISyntaxException {
        log.debug("REST request to save ServiceRequest : {}", serviceRequestDTO);
        if (serviceRequestDTO.getId() != null) {
            throw new BadRequestAlertException("A new serviceRequest cannot already have an ID", ENTITY_NAME, "idexists");
        }
        serviceRequestDTO = serviceRequestService.save(serviceRequestDTO);
        return ResponseEntity.created(new URI("/api/service-requests/" + serviceRequestDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, serviceRequestDTO.getId().toString()))
            .body(serviceRequestDTO);
    }

    /**
     * {@code PUT  /service-requests/:id} : Updates an existing serviceRequest.
     *
     * @param id the id of the serviceRequestDTO to save.
     * @param serviceRequestDTO the serviceRequestDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated serviceRequestDTO,
     * or with status {@code 400 (Bad Request)} if the serviceRequestDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the serviceRequestDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ServiceRequestDTO> updateServiceRequest(
        @PathVariable(value = "id", required = false) final UUID id,
        @RequestBody ServiceRequestDTO serviceRequestDTO
    ) throws URISyntaxException {
        log.debug("REST request to update ServiceRequest : {}, {}", id, serviceRequestDTO);
        if (serviceRequestDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, serviceRequestDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!serviceRequestRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        serviceRequestDTO = serviceRequestService.update(serviceRequestDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, serviceRequestDTO.getId().toString()))
            .body(serviceRequestDTO);
    }

    /**
     * {@code PATCH  /service-requests/:id} : Partial updates given fields of an existing serviceRequest, field will ignore if it is null
     *
     * @param id the id of the serviceRequestDTO to save.
     * @param serviceRequestDTO the serviceRequestDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated serviceRequestDTO,
     * or with status {@code 400 (Bad Request)} if the serviceRequestDTO is not valid,
     * or with status {@code 404 (Not Found)} if the serviceRequestDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the serviceRequestDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ServiceRequestDTO> partialUpdateServiceRequest(
        @PathVariable(value = "id", required = false) final UUID id,
        @RequestBody ServiceRequestDTO serviceRequestDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update ServiceRequest partially : {}, {}", id, serviceRequestDTO);
        if (serviceRequestDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, serviceRequestDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!serviceRequestRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ServiceRequestDTO> result = serviceRequestService.partialUpdate(serviceRequestDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, serviceRequestDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /service-requests} : get all the serviceRequests.
     *
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of serviceRequests in body.
     */
    @GetMapping("")
    public List<ServiceRequestDTO> getAllServiceRequests(@RequestParam(name = "filter", required = false) String filter) {
        if ("transport-is-null".equals(filter)) {
            log.debug("REST request to get all ServiceRequests where transport is null");
            return serviceRequestService.findAllWhereTransportIsNull();
        }
        log.debug("REST request to get all ServiceRequests");
        return serviceRequestService.findAll();
    }

    /**
     * {@code GET  /service-requests/:id} : get the "id" serviceRequest.
     *
     * @param id the id of the serviceRequestDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the serviceRequestDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ServiceRequestDTO> getServiceRequest(@PathVariable("id") UUID id) {
        log.debug("REST request to get ServiceRequest : {}", id);
        Optional<ServiceRequestDTO> serviceRequestDTO = serviceRequestService.findOne(id);
        return ResponseUtil.wrapOrNotFound(serviceRequestDTO);
    }

    /**
     * {@code DELETE  /service-requests/:id} : delete the "id" serviceRequest.
     *
     * @param id the id of the serviceRequestDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteServiceRequest(@PathVariable("id") UUID id) {
        log.debug("REST request to delete ServiceRequest : {}", id);
        serviceRequestService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
