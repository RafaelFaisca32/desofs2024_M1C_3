package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.Manager;
import com.mycompany.myapp.repository.ManagerRepository;
import com.mycompany.myapp.service.ManagerService;
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
public class ManagerServiceImpl implements ManagerService {

    private final Logger log = LoggerFactory.getLogger(ManagerServiceImpl.class);

    private final ManagerRepository managerRepository;

    private final ManagerMapper managerMapper;

    public ManagerServiceImpl(ManagerRepository managerRepository, ManagerMapper managerMapper) {
        this.managerRepository = managerRepository;
        this.managerMapper = managerMapper;
    }

    @Override
    public ManagerDTO save(ManagerDTO managerDTO) {
        log.debug("Request to save Manager : {}", managerDTO);
        Manager manager = managerMapper.toEntity(managerDTO);
        manager = managerRepository.save(manager);
        return managerMapper.toDto(manager);
    }

    @Override
    public ManagerDTO update(ManagerDTO managerDTO) {
        log.debug("Request to update Manager : {}", managerDTO);
        Manager manager = managerMapper.toEntity(managerDTO);
        manager = managerRepository.save(manager);
        return managerMapper.toDto(manager);
    }

    @Override
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

    @Override
    @Transactional(readOnly = true)
    public List<ManagerDTO> findAll() {
        log.debug("Request to get all Managers");
        return managerRepository.findAll().stream().map(managerMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ManagerDTO> findOne(UUID id) {
        log.debug("Request to get Manager : {}", id);
        return managerRepository.findById(id).map(managerMapper::toDto);
    }

    @Override
    public void delete(UUID id) {
        log.debug("Request to delete Manager : {}", id);
        managerRepository.deleteById(id);
    }
}
