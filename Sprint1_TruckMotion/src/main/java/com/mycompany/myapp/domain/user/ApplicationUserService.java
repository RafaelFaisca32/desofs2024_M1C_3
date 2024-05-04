package com.mycompany.myapp.domain.user;

import com.mycompany.myapp.infrastructure.repository.jpa.ApplicationUserRepository;
import com.mycompany.myapp.infrastructure.repository.jpa.UserRepository;
import com.mycompany.myapp.domain.user.dto.ApplicationUserDTO;
import com.mycompany.myapp.domain.user.mapper.ApplicationUserMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ApplicationUser}.
 */
@Service
@Transactional
public class ApplicationUserService {

    private final Logger log = LoggerFactory.getLogger(ApplicationUserService.class);

    private final ApplicationUserRepository applicationUserRepository;

    private final ApplicationUserMapper applicationUserMapper;

    private final UserRepository userRepository;

    public ApplicationUserService(
        ApplicationUserRepository applicationUserRepository,
        ApplicationUserMapper applicationUserMapper,
        UserRepository userRepository
    ) {
        this.applicationUserRepository = applicationUserRepository;
        this.applicationUserMapper = applicationUserMapper;
        this.userRepository = userRepository;
    }

    /**
     * Save a applicationUser.
     *
     * @param applicationUserDTO the entity to save.
     * @return the persisted entity.
     */
    public ApplicationUserDTO save(ApplicationUserDTO applicationUserDTO) {
        log.debug("Request to save ApplicationUser : {}", applicationUserDTO);
        ApplicationUser applicationUser = applicationUserMapper.toEntity(applicationUserDTO);
        Long userId = applicationUser.getInternalUser().getId();
        userRepository.findById(userId).ifPresent(applicationUser::internalUser);
        applicationUser = applicationUserRepository.save(applicationUser);
        return applicationUserMapper.toDto(applicationUser);
    }

    /**
     * Update a applicationUser.
     *
     * @param applicationUserDTO the entity to save.
     * @return the persisted entity.
     */
    public ApplicationUserDTO update(ApplicationUserDTO applicationUserDTO) {
        log.debug("Request to update ApplicationUser : {}", applicationUserDTO);
        ApplicationUser applicationUser = applicationUserMapper.toEntity(applicationUserDTO);
        applicationUser = applicationUserRepository.save(applicationUser);
        return applicationUserMapper.toDto(applicationUser);
    }

    /**
     * Partially update a applicationUser.
     *
     * @param applicationUserDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ApplicationUserDTO> partialUpdate(ApplicationUserDTO applicationUserDTO) {
        log.debug("Request to partially update ApplicationUser : {}", applicationUserDTO);

        return applicationUserRepository
            .findById(applicationUserDTO.getId())
            .map(existingApplicationUser -> {
                applicationUserMapper.partialUpdate(existingApplicationUser, applicationUserDTO);

                return existingApplicationUser;
            })
            .map(applicationUserRepository::save)
            .map(applicationUserMapper::toDto);
    }

    /**
     * Get all the applicationUsers.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<ApplicationUserDTO> findAll() {
        log.debug("Request to get all ApplicationUsers");
        return applicationUserRepository
            .findAll()
            .stream()
            .map(applicationUserMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get all the applicationUsers with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<ApplicationUserDTO> findAllWithEagerRelationships(Pageable pageable) {
        return applicationUserRepository.findAllWithEagerRelationships(pageable).map(applicationUserMapper::toDto);
    }

    /**
     *  Get all the applicationUsers where Driver is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<ApplicationUserDTO> findAllWhereDriverIsNull() {
        log.debug("Request to get all applicationUsers where Driver is null");
        return StreamSupport.stream(applicationUserRepository.findAll().spliterator(), false)
            .filter(applicationUser -> applicationUser.getDriver() == null)
            .map(applicationUserMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     *  Get all the applicationUsers where Manager is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<ApplicationUserDTO> findAllWhereManagerIsNull() {
        log.debug("Request to get all applicationUsers where Manager is null");
        return StreamSupport.stream(applicationUserRepository.findAll().spliterator(), false)
            .filter(applicationUser -> applicationUser.getManager() == null)
            .map(applicationUserMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     *  Get all the applicationUsers where Customer is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<ApplicationUserDTO> findAllWhereCustomerIsNull() {
        log.debug("Request to get all applicationUsers where Customer is null");
        return StreamSupport.stream(applicationUserRepository.findAll().spliterator(), false)
            .filter(applicationUser -> applicationUser.getCustomer() == null)
            .map(applicationUserMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one applicationUser by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ApplicationUserDTO> findOne(Long id) {
        log.debug("Request to get ApplicationUser : {}", id);
        return applicationUserRepository.findOneWithEagerRelationships(id).map(applicationUserMapper::toDto);
    }

    /**
     * Delete the applicationUser by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ApplicationUser : {}", id);
        applicationUserRepository.deleteById(id);
    }
}
