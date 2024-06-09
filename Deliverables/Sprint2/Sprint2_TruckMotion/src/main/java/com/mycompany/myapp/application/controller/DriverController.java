package com.mycompany.myapp.application.controller;

import com.mycompany.myapp.domain.driver.Driver;
import com.mycompany.myapp.domain.driver.dto.AvailableDriverDTO;
import com.mycompany.myapp.infrastructure.repository.jpa.DriverRepository;
import com.mycompany.myapp.domain.driver.DriverService;
import com.mycompany.myapp.domain.driver.dto.DriverDTO;
import com.mycompany.myapp.application.controller.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import com.mycompany.myapp.security.AuthoritiesConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link Driver}.
 */
@RestController
@RequestMapping("/api/drivers")
public class DriverController {

    private final Logger log = LoggerFactory.getLogger(DriverController.class);

    private static final String ENTITY_NAME = "driver";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DriverService driverService;

    public DriverController(DriverService driverService) {
        this.driverService = driverService;
    }

    /**
     * {@code POST  /drivers} : Create a new driver.
     *
     * @param driverDTO the driverDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new driverDTO, or with status {@code 400 (Bad Request)} if the driver has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    @PreAuthorize("hasAnyAuthority('" + AuthoritiesConstants.ADMIN + "', '" + AuthoritiesConstants.MANAGER + "')")
    public ResponseEntity<DriverDTO> createDriver(@RequestBody DriverDTO driverDTO) throws URISyntaxException {
        log.debug("REST request to save Driver : {}", driverDTO);
        if (driverDTO.getId() != null) {
            throw new BadRequestAlertException("A new driver cannot already have an ID", ENTITY_NAME, "idexists");
        }
        driverDTO = driverService.save(driverDTO);
        return ResponseEntity.created(new URI("/api/drivers/" + driverDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, driverDTO.getId().toString()))
            .body(driverDTO);
    }

    /**
     * {@code PUT  /drivers/:id} : Updates an existing driver.
     *
     * @param id the id of the driverDTO to save.
     * @param driverDTO the driverDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated driverDTO,
     * or with status {@code 400 (Bad Request)} if the driverDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the driverDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('" + AuthoritiesConstants.ADMIN + "', '" + AuthoritiesConstants.MANAGER + "')")
    public ResponseEntity<DriverDTO> updateDriver(
        @PathVariable(value = "id", required = false) final UUID id,
        @RequestBody DriverDTO driverDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Driver : {}, {}", id, driverDTO);
        if (driverDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, driverDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        driverDTO = driverService.update(driverDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, driverDTO.getId().toString()))
            .body(driverDTO);
    }

    /**
     * {@code GET  /drivers} : get all the drivers.
     *
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of drivers in body.
     */
    @GetMapping("")
    @PreAuthorize("hasAnyAuthority('" + AuthoritiesConstants.ADMIN + "', '" + AuthoritiesConstants.MANAGER + "', '" +AuthoritiesConstants.DRIVER+"')")
    public List<DriverDTO> getAllDrivers(@RequestParam(name = "filter", required = false) String filter) {
        if ("transport-is-null".equals(filter)) {
            log.debug("REST request to get all Drivers where transport is null");
            return driverService.findAllWhereTransportIsNull();
        }
        log.debug("REST request to get all Drivers");
        return driverService.findAll();
    }


    @GetMapping("/freeDrivers/{startDate}/{endDate}")
    @PreAuthorize("hasAnyAuthority('" + AuthoritiesConstants.ADMIN + "', '" + AuthoritiesConstants.MANAGER + "')")
    public List<AvailableDriverDTO> getAllFreeDrivers( @PathVariable(value = "startDate", required = false) final String startDate,
                                                       @PathVariable(value = "endDate", required = false) final String endDate) {

        return driverService.findAllWhereTransportIsNullAtACertainTime(startDate,endDate);
    }

    /**
     * {@code GET  /drivers/:id} : get the "id" driver.
     *
     * @param id the id of the driverDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the driverDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('" + AuthoritiesConstants.ADMIN + "', '" + AuthoritiesConstants.MANAGER + "')")
    public ResponseEntity<DriverDTO> getDriver(@PathVariable("id") UUID id) {
        log.debug("REST request to get Driver : {}", id);
        Optional<DriverDTO> driverDTO = driverService.findOne(id);
        return ResponseUtil.wrapOrNotFound(driverDTO);
    }

    /**
     * {@code DELETE  /drivers/:id} : delete the "id" driver.
     *
     * @param id the id of the driverDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('" + AuthoritiesConstants.ADMIN + "', '" + AuthoritiesConstants.MANAGER + "')")
    public ResponseEntity<Void> deleteDriver(@PathVariable("id") UUID id) {
        log.debug("REST request to delete Driver : {}", id);
        driverService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
