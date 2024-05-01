package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.Transport;
import com.mycompany.myapp.repository.TransportRepository;
import com.mycompany.myapp.service.dto.TransportDTO;
import com.mycompany.myapp.service.mapper.TransportMapper;
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
 * Service Implementation for managing {@link com.mycompany.myapp.domain.Transport}.
 */
@Service
@Transactional
public class TransportService {

    private final Logger log = LoggerFactory.getLogger(TransportService.class);

    private final TransportRepository transportRepository;

    private final TransportMapper transportMapper;

    public TransportService(TransportRepository transportRepository, TransportMapper transportMapper) {
        this.transportRepository = transportRepository;
        this.transportMapper = transportMapper;
    }

    /**
     * Save a transport.
     *
     * @param transportDTO the entity to save.
     * @return the persisted entity.
     */
    public TransportDTO save(TransportDTO transportDTO) {
        log.debug("Request to save Transport : {}", transportDTO);
        Transport transport = transportMapper.toEntity(transportDTO);
        transport = transportRepository.save(transport);
        return transportMapper.toDto(transport);
    }

    /**
     * Update a transport.
     *
     * @param transportDTO the entity to save.
     * @return the persisted entity.
     */
    public TransportDTO update(TransportDTO transportDTO) {
        log.debug("Request to update Transport : {}", transportDTO);
        Transport transport = transportMapper.toEntity(transportDTO);
        transport = transportRepository.save(transport);
        return transportMapper.toDto(transport);
    }

    /**
     * Partially update a transport.
     *
     * @param transportDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<TransportDTO> partialUpdate(TransportDTO transportDTO) {
        log.debug("Request to partially update Transport : {}", transportDTO);

        return transportRepository
            .findById(transportDTO.getId())
            .map(existingTransport -> {
                transportMapper.partialUpdate(existingTransport, transportDTO);

                return existingTransport;
            })
            .map(transportRepository::save)
            .map(transportMapper::toDto);
    }

    /**
     * Get all the transports.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<TransportDTO> findAll() {
        log.debug("Request to get all Transports");
        return transportRepository.findAll().stream().map(transportMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one transport by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<TransportDTO> findOne(UUID id) {
        log.debug("Request to get Transport : {}", id);
        return transportRepository.findById(id).map(transportMapper::toDto);
    }

    /**
     * Delete the transport by id.
     *
     * @param id the id of the entity.
     */
    public void delete(UUID id) {
        log.debug("Request to delete Transport : {}", id);
        transportRepository.deleteById(id);
    }
}
