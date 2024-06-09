package com.mycompany.myapp.application.controller;

import com.mycompany.myapp.domain.customer.dto.CustomerDTO;
import com.mycompany.myapp.domain.driver.DriverId;
import com.mycompany.myapp.domain.driver.DriverService;
import com.mycompany.myapp.domain.driver.dto.DriverDTO;
import com.mycompany.myapp.domain.location.dto.LocationDTO;
import com.mycompany.myapp.domain.transport.Transport;
import com.mycompany.myapp.domain.transport.TransportId;
import com.mycompany.myapp.domain.user.User;
import com.mycompany.myapp.domain.user.UserService;
import com.mycompany.myapp.domain.user.dto.AdminUserDTO;
import com.mycompany.myapp.infrastructure.repository.jpa.TransportRepository;
import com.mycompany.myapp.domain.transport.TransportService;
import com.mycompany.myapp.domain.transport.dto.TransportDTO;
import com.mycompany.myapp.application.controller.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;

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
 * REST controller for managing {@link Transport}.
 */
@RestController
@RequestMapping("/api/transports")
public class TransportController {

    private final Logger log = LoggerFactory.getLogger(TransportController.class);

    private static final String ENTITY_NAME = "transport";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TransportService transportService;
    private final DriverService driverService;
    private final UserService userService;

    public TransportController(TransportService transportService, UserService userService,DriverService driverService) {
        this.transportService = transportService;
        this.userService = userService;
        this.driverService = driverService;
    }

    /**
     * {@code POST  /transports} : Create a new transport.
     *
     * @param transportDTO the transportDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new transportDTO, or with status {@code 400 (Bad Request)} if the transport has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    @PreAuthorize("hasAnyAuthority('" + AuthoritiesConstants.ADMIN + "', '" + AuthoritiesConstants.MANAGER + "')")
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
    @PreAuthorize("hasAnyAuthority('" + AuthoritiesConstants.ADMIN + "', '" + AuthoritiesConstants.MANAGER + "', '" + AuthoritiesConstants.DRIVER + "')")
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


        Optional<TransportDTO> transportRet = transportService.findOne(transportDTO.getId());
            if(transportRet.isPresent()) {
                if(transportRet.get().getId().equals(transportDTO.getId()) &&
                    transportRet.get().getDriver().equals(transportDTO.getDriver()) &&
                    transportRet.get().getLocation().equals(transportDTO.getLocation())&&
                    transportRet.get().getServiceRequest().equals(transportDTO.getServiceRequest()  )) {

                    transportDTO = transportService.update(transportDTO);
                    return ResponseEntity.ok()
                        .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, transportDTO.getId().toString()))
                        .body(transportDTO);
                }else {
                    throw new RuntimeException("Only can update the start date and end date");
                }


            }
            throw new RuntimeException("There is no transport with this id");
    }

    /**
     * {@code GET  /transports} : get all the transports.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of transports in body.
     */
    @GetMapping("")
    @PreAuthorize("hasAnyAuthority('" + AuthoritiesConstants.ADMIN + "', '" + AuthoritiesConstants.MANAGER + "','"+AuthoritiesConstants.DRIVER+"')")
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
    @PreAuthorize("hasAnyAuthority('" + AuthoritiesConstants.ADMIN + "', '" + AuthoritiesConstants.MANAGER + "', '" + AuthoritiesConstants.DRIVER + "')")
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
    @PreAuthorize("hasAnyAuthority('" + AuthoritiesConstants.ADMIN + "', '" + AuthoritiesConstants.MANAGER + "')")
    public ResponseEntity<Void> deleteTransport(@PathVariable("id") UUID id) {
        log.debug("REST request to delete Transport : {}", id);
        transportService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }

    @GetMapping("/getByUserLoggedIn")
    @PreAuthorize("hasAnyAuthority('" + AuthoritiesConstants.ADMIN + "', '" + AuthoritiesConstants.MANAGER + "', '" + AuthoritiesConstants.DRIVER + "')")
    public List<TransportDTO> getAllTransportsByLoggedInUser(@RequestParam(name = "filter", required = false) String filter) {
        AdminUserDTO adminUserDTO = userService
            .getUserWithAuthorities()
            .map(AdminUserDTO::new).get();
        log.debug("REST request to get all Transports By LoggedIn User");

        List<TransportDTO> listRet = new ArrayList<>();

        if (adminUserDTO != null) {
            if (adminUserDTO.getAuthorities().contains("ROLE_MANAGER") || adminUserDTO.getAuthorities().contains("ROLE_ADMIN"))
                listRet = transportService.findAll();
            else if (adminUserDTO.getAuthorities().contains("ROLE_DRIVER"))
                listRet = transportService.getByUserId(adminUserDTO.getId());
        }

        return listRet;
    }
}
