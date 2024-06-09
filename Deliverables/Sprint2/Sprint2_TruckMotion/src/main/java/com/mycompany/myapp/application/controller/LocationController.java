package com.mycompany.myapp.application.controller;

import com.mycompany.myapp.domain.customer.CustomerService;
import com.mycompany.myapp.domain.customer.dto.CustomerDTO;
import com.mycompany.myapp.domain.location.Location;
import com.mycompany.myapp.domain.user.UserService;
import com.mycompany.myapp.domain.user.dto.AdminUserDTO;
import com.mycompany.myapp.infrastructure.repository.jpa.LocationRepository;
import com.mycompany.myapp.domain.location.LocationService;
import com.mycompany.myapp.domain.location.dto.LocationDTO;
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
 * REST controller for managing {@link Location}.
 */
@RestController
@RequestMapping("/api/locations")
public class LocationController {

    private final Logger log = LoggerFactory.getLogger(LocationController.class);

    private static final String ENTITY_NAME = "location";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LocationService locationService;
    private final UserService userService;

    private final CustomerService customerService;

    public LocationController(LocationService locationService, UserService userService, CustomerService customerService) {
        this.locationService = locationService;
        this.userService = userService;
        this.customerService = customerService;
    }

    /**
     * {@code POST  /locations} : Create a new location.
     *
     * @param locationDTO the locationDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new locationDTO, or with status {@code 400 (Bad Request)} if the location has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<LocationDTO> createLocation(@RequestBody LocationDTO locationDTO) throws URISyntaxException {
        log.debug("REST request to save Location : {}", locationDTO);
        if (locationDTO.getId() != null) {
            throw new BadRequestAlertException("A new location cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AdminUserDTO adminUserDTO = userService
            .getUserWithAuthorities()
            .map(AdminUserDTO::new).get();

        Optional<CustomerDTO> customer = customerService.getByUserId(adminUserDTO.getId());
        if (customer.isPresent()) {
            locationDTO.setCustomer(customer.get());
            locationDTO = locationService.save(locationDTO);
            return ResponseEntity.created(new URI("/api/locations/" + locationDTO.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, locationDTO.getId().toString()))
                .body(locationDTO);
        } else {
            throw new BadRequestAlertException("Unauthorized", ENTITY_NAME, "unauthorized");
        }
    }

    /**
     * {@code PUT  /locations/:id} : Updates an existing location.
     *
     * @param id the id of the locationDTO to save.
     * @param locationDTO the locationDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated locationDTO,
     * or with status {@code 400 (Bad Request)} if the locationDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the locationDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<LocationDTO> updateLocation(
        @PathVariable(value = "id", required = false) final UUID id,
        @RequestBody LocationDTO locationDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Location : {}, {}", id, locationDTO);
        if (locationDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, locationDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        locationDTO = locationService.update(locationDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, locationDTO.getId().toString()))
            .body(locationDTO);
    }

    /**
     * {@code GET  /locations} : get all the locations.
     *
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of locations in body.
     */
    @GetMapping("")
    public List<LocationDTO> getAllLocations(@RequestParam(name = "filter", required = false) String filter) {
        if ("servicerequest-is-null".equals(filter)) {
            log.debug("REST request to get all Locations where serviceRequest is null");
            return locationService.findAllWhereServiceRequestIsNull();
        }

        if ("transport-is-null".equals(filter)) {
            log.debug("REST request to get all Locations where transport is null");
            return locationService.findAllWhereTransportIsNull();
        }
        log.debug("REST request to get all Locations");
        return locationService.findAll();
    }

    /**
     * {@code GET  /locations/:id} : get the "id" location.
     *
     * @param id the id of the locationDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the locationDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<LocationDTO> getLocation(@PathVariable("id") UUID id) {
        log.debug("REST request to get Location : {}", id);
        Optional<LocationDTO> locationDTO = locationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(locationDTO);
    }

    /**
     * {@code DELETE  /locations/:id} : delete the "id" location.
     *
     * @param id the id of the locationDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLocation(@PathVariable("id") UUID id) {
        log.debug("REST request to delete Location : {}", id);
        locationService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }


    @GetMapping("/getByUserLoggedIn")
    @PreAuthorize("hasAnyAuthority('" + AuthoritiesConstants.ADMIN + "', '" + AuthoritiesConstants.MANAGER + "', '" + AuthoritiesConstants.CUSTOMER + "')")
    public List<LocationDTO> getAllLocationsByLoggedInUser(@RequestParam(name = "filter", required = false) String filter) {
        AdminUserDTO adminUserDTO = userService
            .getUserWithAuthorities()
            .map(AdminUserDTO::new).get();
        log.debug("REST request to get all Locations By LoggedIn User");

        List<LocationDTO> listRet = new ArrayList<>();

        if (adminUserDTO != null) {
            if (adminUserDTO.getAuthorities().contains("ROLE_MANAGER") || adminUserDTO.getAuthorities().contains("ROLE_ADMIN"))
                listRet = locationService.findAll();
            else if (adminUserDTO.getAuthorities().contains("ROLE_CUSTOMER"))
                listRet = locationService.getByUserId(adminUserDTO.getId());
        }

        return listRet;
    }
}
