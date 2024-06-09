package com.mycompany.myapp.domain.location;

import com.mycompany.myapp.application.controller.errors.BadRequestAlertException;
import com.mycompany.myapp.domain.customer.CustomerService;
import com.mycompany.myapp.domain.customer.dto.CustomerDTO;
import com.mycompany.myapp.domain.user.UserService;
import com.mycompany.myapp.domain.user.dto.AdminUserDTO;
import com.mycompany.myapp.infrastructure.repository.jpa.LocationRepository;
import com.mycompany.myapp.domain.location.dto.LocationDTO;
import com.mycompany.myapp.domain.location.mapper.LocationMapper;
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
 * Service Implementation for managing {@link Location}.
 */
@Service
@Transactional
public class LocationService {

    private final Logger log = LoggerFactory.getLogger(LocationService.class);

    private final ILocationRepository locationRepository;

    private final UserService userService;

    private final CustomerService customerService;

    private static final String ENTITY_NAME = "location";

    public LocationService(ILocationRepository locationRepository, UserService userService, CustomerService customerService) {
        this.locationRepository = locationRepository;
        this.userService = userService;
        this.customerService = customerService;
    }

    /**
     * Save a location.
     *
     * @param locationDTO the entity to save.
     * @return the persisted entity.
     */
    public LocationDTO save(LocationDTO locationDTO) {
        log.debug("Request to save Location : {}", locationDTO);
        Location location = LocationMapper.toEntity(locationDTO);
        location = locationRepository.save(location);
        return LocationMapper.toDto(location);
    }

    /**
     * Update a location.
     *
     * @param locationDTO the entity to save.
     * @return the persisted entity.
     */
    public LocationDTO update(LocationDTO locationDTO) {
        log.debug("Request to update Location : {}", locationDTO);
        LocationId id = new LocationId(locationDTO.getId());
        if (!locationRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        AdminUserDTO adminUserDTO = userService
            .getUserWithAuthorities()
            .map(AdminUserDTO::new).get();

        Optional<Location> location1 = locationRepository.findById(new LocationId(locationDTO.getId()));

        Optional<CustomerDTO> customer = customerService.getByUserId(adminUserDTO.getId());
        if (customer.isPresent() && customer.get().getId().equals(location1.get().getCustomer().getId().value())) {
            Location location = LocationMapper.toEntity(locationDTO);
            location = locationRepository.save(location);
            return LocationMapper.toDto(location);
        } else {
            throw new BadRequestAlertException("Unauthorized", ENTITY_NAME, "unauthorized");
        }
    }

    /**
     * Partially update a location.
     *
     * @param locationDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<LocationDTO> partialUpdate(LocationDTO locationDTO) {
        log.debug("Request to partially update Location : {}", locationDTO);
        LocationId id = new LocationId(locationDTO.getId());
        if (!locationRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        AdminUserDTO adminUserDTO = userService
            .getUserWithAuthorities()
            .map(AdminUserDTO::new).get();

        Optional<Location> location = locationRepository.findById(new LocationId(locationDTO.getId()));

        Optional<CustomerDTO> customer = customerService.getByUserId(adminUserDTO.getId());
        if (customer.isPresent() && customer.get().getId().equals(location.get().getCustomer().getId().value())) {

            return location
                .map(existingLocation -> {
                    LocationMapper.partialUpdate(existingLocation, locationDTO);

                    return existingLocation;
                })
                .map(locationRepository::save)
                .map(LocationMapper::toDto);
        } else {
            throw new BadRequestAlertException("Unauthorized", ENTITY_NAME, "unauthorized");
        }
    }

    /**
     * Get all the locations.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<LocationDTO> findAll() {
        log.debug("Request to get all Locations");
        return locationRepository.findAll().stream().map(LocationMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     *  Get all the locations where ServiceRequest is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<LocationDTO> findAllWhereServiceRequestIsNull() {
        log.debug("Request to get all locations where ServiceRequest is null");
        return StreamSupport.stream(locationRepository.findAll().spliterator(), false)
            .filter(location -> location.getServiceRequests() == null)
            .map(LocationMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     *  Get all the locations where Transport is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<LocationDTO> findAllWhereTransportIsNull() {
        log.debug("Request to get all locations where Transport is null");
        return StreamSupport.stream(locationRepository.findAll().spliterator(), false)
            .filter(location -> location.getTransports() == null)
            .map(LocationMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one location by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<LocationDTO> findOne(UUID id) {
        log.debug("Request to get Location : {}", id);
        LocationId lId = new LocationId(id);
        return locationRepository.findById(lId).map(LocationMapper::toDto);
    }

    /**
     * Delete the location by id.
     *
     * @param id the id of the entity.
     */
    public void delete(UUID id) {
        log.debug("Request to delete Location : {}", id);
        LocationId lId = new LocationId(id);

        AdminUserDTO adminUserDTO = userService
            .getUserWithAuthorities()
            .map(AdminUserDTO::new).get();

        Optional<CustomerDTO> customer = customerService.getByUserId(adminUserDTO.getId());
        Optional<Location> location = locationRepository.findById(lId);
        if (customer.isPresent() && customer.get().getId().equals(location.get().getCustomer().getId().value())) {
            locationRepository.deleteById(lId);
        } else {
            throw new BadRequestAlertException("Unauthorized", ENTITY_NAME, "unauthorized");
        }
    }

    @Transactional(readOnly = true)
    public List<LocationDTO> getByUserId(Long userId) {
        log.debug("Request to get Location by UserId : {}", userId);
        return StreamSupport.stream(locationRepository.getByUserId(userId).spliterator(), false)
            .map(LocationMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }
}
