package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.Manager;
import com.mycompany.myapp.repository.ManagerRepository;
import com.mycompany.myapp.service.dto.ManagerDTO;
import com.mycompany.myapp.service.mapper.ManagerMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.mycompany.myapp.domain.Manager}.
 */
@Service
@Transactional
public class ManagerService {

    private final Logger log = LoggerFactory.getLogger(ManagerService.class);

    private final ManagerRepository managerRepository;

    private final ManagerMapper managerMapper;

    public ManagerService(ManagerRepository managerRepository, ManagerMapper managerMapper) {
        this.managerRepository = managerRepository;
        this.managerMapper = managerMapper;
    }

    /**
     * Save a manager.
     *
     * @param managerDTO the entity to save.
     * @return the persisted entity.
     */
    public ManagerDTO save(ManagerDTO managerDTO) {
        log.debug("Request to save Manager : {}", managerDTO);
        Manager manager = managerMapper.toEntity(managerDTO);
        manager = managerRepository.save(manager);
        return managerMapper.toDto(manager);
    }

    /**
     * Update a manager.
     *
     * @param managerDTO the entity to save.
     * @return the persisted entity.
     */
    public ManagerDTO update(ManagerDTO managerDTO) {
        log.debug("Request to update Manager : {}", managerDTO);
        Manager manager = managerMapper.toEntity(managerDTO);
        manager = managerRepository.save(manager);
        return managerMapper.toDto(manager);
    }

    /**
     * Partially update a manager.
     *
     * @param managerDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ManagerDTO> partialUpdate(ManagerDTO managerDTO) {
        log.debug("Request to partially update Manager : {}", managerDTO);

        return managerRepository
            .findById(managerDTO.getId())
            .map(existingManager -> {
                managerMapper.partialUpdate(existingManager, managerDTO);

                return existingManager;
            })
            .map(managerRepository::save)
            .map(managerMapper::toDto);
    }

    /**
     * Get all the managers.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<ManagerDTO> findAll() {
        log.debug("Request to get all Managers");
        return managerRepository.findAll().stream().map(managerMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one manager by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ManagerDTO> findOne(UUID id) {
        log.debug("Request to get Manager : {}", id);
        return managerRepository.findById(id).map(managerMapper::toDto);
    }

    /**
     * Delete the manager by id.
     *
     * @param id the id of the entity.
     */
    public void delete(UUID id) {
        log.debug("Request to delete Manager : {}", id);
        managerRepository.deleteById(id);
    }
}
