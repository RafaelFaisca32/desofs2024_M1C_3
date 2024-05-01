package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.repository.ServiceStatusRepository;
import com.mycompany.myapp.service.ServiceStatusService;
import com.mycompany.myapp.service.dto.ServiceStatusDTO;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.ServiceStatus}.
 */
@RestController
@RequestMapping("/api/service-statuses")
public class ServiceStatusResource {

    private final Logger log = LoggerFactory.getLogger(ServiceStatusResource.class);

    private static final String ENTITY_NAME = "serviceStatus";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ServiceStatusService serviceStatusService;

    private final ServiceStatusRepository serviceStatusRepository;

    public ServiceStatusResource(ServiceStatusService serviceStatusService, ServiceStatusRepository serviceStatusRepository) {
        this.serviceStatusService = serviceStatusService;
        this.serviceStatusRepository = serviceStatusRepository;
    }

    /**
     * {@code POST  /service-statuses} : Create a new serviceStatus.
     *
     * @param serviceStatusDTO the serviceStatusDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new serviceStatusDTO, or with status {@code 400 (Bad Request)} if the serviceStatus has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<ServiceStatusDTO> createServiceStatus(@RequestBody ServiceStatusDTO serviceStatusDTO) throws URISyntaxException {
        log.debug("REST request to save ServiceStatus : {}", serviceStatusDTO);
        if (serviceStatusDTO.getId() != null) {
            throw new BadRequestAlertException("A new serviceStatus cannot already have an ID", ENTITY_NAME, "idexists");
        }
        serviceStatusDTO = serviceStatusService.save(serviceStatusDTO);
        return ResponseEntity.created(new URI("/api/service-statuses/" + serviceStatusDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, serviceStatusDTO.getId().toString()))
            .body(serviceStatusDTO);
    }

    /**
     * {@code PUT  /service-statuses/:id} : Updates an existing serviceStatus.
     *
     * @param id the id of the serviceStatusDTO to save.
     * @param serviceStatusDTO the serviceStatusDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated serviceStatusDTO,
     * or with status {@code 400 (Bad Request)} if the serviceStatusDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the serviceStatusDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ServiceStatusDTO> updateServiceStatus(
        @PathVariable(value = "id", required = false) final UUID id,
        @RequestBody ServiceStatusDTO serviceStatusDTO
    ) throws URISyntaxException {
        log.debug("REST request to update ServiceStatus : {}, {}", id, serviceStatusDTO);
        if (serviceStatusDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, serviceStatusDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!serviceStatusRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        serviceStatusDTO = serviceStatusService.update(serviceStatusDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, serviceStatusDTO.getId().toString()))
            .body(serviceStatusDTO);
    }

    /**
     * {@code PATCH  /service-statuses/:id} : Partial updates given fields of an existing serviceStatus, field will ignore if it is null
     *
     * @param id the id of the serviceStatusDTO to save.
     * @param serviceStatusDTO the serviceStatusDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated serviceStatusDTO,
     * or with status {@code 400 (Bad Request)} if the serviceStatusDTO is not valid,
     * or with status {@code 404 (Not Found)} if the serviceStatusDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the serviceStatusDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ServiceStatusDTO> partialUpdateServiceStatus(
        @PathVariable(value = "id", required = false) final UUID id,
        @RequestBody ServiceStatusDTO serviceStatusDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update ServiceStatus partially : {}, {}", id, serviceStatusDTO);
        if (serviceStatusDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, serviceStatusDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!serviceStatusRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ServiceStatusDTO> result = serviceStatusService.partialUpdate(serviceStatusDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, serviceStatusDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /service-statuses} : get all the serviceStatuses.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of serviceStatuses in body.
     */
    @GetMapping("")
    public List<ServiceStatusDTO> getAllServiceStatuses() {
        log.debug("REST request to get all ServiceStatuses");
        return serviceStatusService.findAll();
    }

    /**
     * {@code GET  /service-statuses/:id} : get the "id" serviceStatus.
     *
     * @param id the id of the serviceStatusDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the serviceStatusDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ServiceStatusDTO> getServiceStatus(@PathVariable("id") UUID id) {
        log.debug("REST request to get ServiceStatus : {}", id);
        Optional<ServiceStatusDTO> serviceStatusDTO = serviceStatusService.findOne(id);
        return ResponseUtil.wrapOrNotFound(serviceStatusDTO);
    }

    /**
     * {@code DELETE  /service-statuses/:id} : delete the "id" serviceStatus.
     *
     * @param id the id of the serviceStatusDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteServiceStatus(@PathVariable("id") UUID id) {
        log.debug("REST request to delete ServiceStatus : {}", id);
        serviceStatusService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
