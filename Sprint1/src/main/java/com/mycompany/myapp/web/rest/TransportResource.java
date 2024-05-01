package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.repository.TransportRepository;
import com.mycompany.myapp.service.TransportService;
import com.mycompany.myapp.service.dto.TransportDTO;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.Transport}.
 */
@RestController
@RequestMapping("/api/transports")
public class TransportResource {

    private final Logger log = LoggerFactory.getLogger(TransportResource.class);

    private static final String ENTITY_NAME = "transport";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TransportService transportService;

    private final TransportRepository transportRepository;

    public TransportResource(TransportService transportService, TransportRepository transportRepository) {
        this.transportService = transportService;
        this.transportRepository = transportRepository;
    }

    /**
     * {@code POST  /transports} : Create a new transport.
     *
     * @param transportDTO the transportDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new transportDTO, or with status {@code 400 (Bad Request)} if the transport has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<TransportDTO> createTransport(@RequestBody TransportDTO transportDTO) throws URISyntaxException {
        log.debug("REST request to save Transport : {}", transportDTO);
        if (transportDTO.getId() != null) {
            throw new BadRequestAlertException("A new transport cannot already have an ID", ENTITY_NAME, "idexists");
        }
        transportDTO = transportService.save(transportDTO);
        return ResponseEntity.created(new URI("/api/transports/" + transportDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, transportDTO.getId().toString()))
            .body(transportDTO);
    }

    /**
     * {@code PUT  /transports/:id} : Updates an existing transport.
     *
     * @param id the id of the transportDTO to save.
     * @param transportDTO the transportDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated transportDTO,
     * or with status {@code 400 (Bad Request)} if the transportDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the transportDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<TransportDTO> updateTransport(
        @PathVariable(value = "id", required = false) final UUID id,
        @RequestBody TransportDTO transportDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Transport : {}, {}", id, transportDTO);
        if (transportDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, transportDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!transportRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        transportDTO = transportService.update(transportDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, transportDTO.getId().toString()))
            .body(transportDTO);
    }

    /**
     * {@code PATCH  /transports/:id} : Partial updates given fields of an existing transport, field will ignore if it is null
     *
     * @param id the id of the transportDTO to save.
     * @param transportDTO the transportDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated transportDTO,
     * or with status {@code 400 (Bad Request)} if the transportDTO is not valid,
     * or with status {@code 404 (Not Found)} if the transportDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the transportDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<TransportDTO> partialUpdateTransport(
        @PathVariable(value = "id", required = false) final UUID id,
        @RequestBody TransportDTO transportDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Transport partially : {}, {}", id, transportDTO);
        if (transportDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, transportDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!transportRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<TransportDTO> result = transportService.partialUpdate(transportDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, transportDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /transports} : get all the transports.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of transports in body.
     */
    @GetMapping("")
    public List<TransportDTO> getAllTransports() {
        log.debug("REST request to get all Transports");
        return transportService.findAll();
    }

    /**
     * {@code GET  /transports/:id} : get the "id" transport.
     *
     * @param id the id of the transportDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the transportDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<TransportDTO> getTransport(@PathVariable("id") UUID id) {
        log.debug("REST request to get Transport : {}", id);
        Optional<TransportDTO> transportDTO = transportService.findOne(id);
        return ResponseUtil.wrapOrNotFound(transportDTO);
    }

    /**
     * {@code DELETE  /transports/:id} : delete the "id" transport.
     *
     * @param id the id of the transportDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTransport(@PathVariable("id") UUID id) {
        log.debug("REST request to delete Transport : {}", id);
        transportService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
