package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.repository.ManagerRepository;
import com.mycompany.myapp.service.ManagerService;
import com.mycompany.myapp.service.dto.ManagerDTO;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.Manager}.
 */
@RestController
@RequestMapping("/api/managers")
public class ManagerResource {

    private final Logger log = LoggerFactory.getLogger(ManagerResource.class);

    private static final String ENTITY_NAME = "manager";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ManagerService managerService;

    private final ManagerRepository managerRepository;

    public ManagerResource(ManagerService managerService, ManagerRepository managerRepository) {
        this.managerService = managerService;
        this.managerRepository = managerRepository;
    }

    /**
     * {@code POST  /managers} : Create a new manager.
     *
     * @param managerDTO the managerDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new managerDTO, or with status {@code 400 (Bad Request)} if the manager has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<ManagerDTO> createManager(@RequestBody ManagerDTO managerDTO) throws URISyntaxException {
        log.debug("REST request to save Manager : {}", managerDTO);
        if (managerDTO.getId() != null) {
            throw new BadRequestAlertException("A new manager cannot already have an ID", ENTITY_NAME, "idexists");
        }
        managerDTO = managerService.save(managerDTO);
        return ResponseEntity.created(new URI("/api/managers/" + managerDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, managerDTO.getId().toString()))
            .body(managerDTO);
    }

    /**
     * {@code PUT  /managers/:id} : Updates an existing manager.
     *
     * @param id the id of the managerDTO to save.
     * @param managerDTO the managerDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated managerDTO,
     * or with status {@code 400 (Bad Request)} if the managerDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the managerDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ManagerDTO> updateManager(
        @PathVariable(value = "id", required = false) final UUID id,
        @RequestBody ManagerDTO managerDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Manager : {}, {}", id, managerDTO);
        if (managerDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, managerDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!managerRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        managerDTO = managerService.update(managerDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, managerDTO.getId().toString()))
            .body(managerDTO);
    }

    /**
     * {@code PATCH  /managers/:id} : Partial updates given fields of an existing manager, field will ignore if it is null
     *
     * @param id the id of the managerDTO to save.
     * @param managerDTO the managerDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated managerDTO,
     * or with status {@code 400 (Bad Request)} if the managerDTO is not valid,
     * or with status {@code 404 (Not Found)} if the managerDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the managerDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ManagerDTO> partialUpdateManager(
        @PathVariable(value = "id", required = false) final UUID id,
        @RequestBody ManagerDTO managerDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Manager partially : {}, {}", id, managerDTO);
        if (managerDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, managerDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!managerRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ManagerDTO> result = managerService.partialUpdate(managerDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, managerDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /managers} : get all the managers.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of managers in body.
     */
    @GetMapping("")
    public List<ManagerDTO> getAllManagers() {
        log.debug("REST request to get all Managers");
        return managerService.findAll();
    }

    /**
     * {@code GET  /managers/:id} : get the "id" manager.
     *
     * @param id the id of the managerDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the managerDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ManagerDTO> getManager(@PathVariable("id") UUID id) {
        log.debug("REST request to get Manager : {}", id);
        Optional<ManagerDTO> managerDTO = managerService.findOne(id);
        return ResponseUtil.wrapOrNotFound(managerDTO);
    }

    /**
     * {@code DELETE  /managers/:id} : delete the "id" manager.
     *
     * @param id the id of the managerDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteManager(@PathVariable("id") UUID id) {
        log.debug("REST request to delete Manager : {}", id);
        managerService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
