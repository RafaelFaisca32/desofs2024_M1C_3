package com.mycompany.myapp.domain.transport;

import com.mycompany.myapp.application.controller.errors.BadRequestAlertException;
import com.mycompany.myapp.infrastructure.repository.jpa.TransportRepository;
import com.mycompany.myapp.domain.transport.dto.TransportDTO;
import com.mycompany.myapp.domain.transport.mapper.TransportMapper;
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
 * Service Implementation for managing {@link Transport}.
 */
@Service
@Transactional
public class TransportService {

    private final Logger log = LoggerFactory.getLogger(TransportService.class);

    private final ITransportRepository transportRepository;

    private static final String ENTITY_NAME = "transport";

    public TransportService(ITransportRepository transportRepository) {
        this.transportRepository = transportRepository;
    }

    /**
     * Save a transport.
     *
     * @param transportDTO the entity to save.
     * @return the persisted entity.
     */
    public TransportDTO save(TransportDTO transportDTO) {
        log.debug("Request to save Transport : {}", transportDTO);
        Transport transport = TransportMapper.toEntity(transportDTO);
        transport = transportRepository.save(transport);
        return TransportMapper.toDto(transport);
    }

    /**
     * Update a transport.
     *
     * @param transportDTO the entity to save.
     * @return the persisted entity.
     */
    public TransportDTO update(TransportDTO transportDTO) {
        log.debug("Request to update Transport : {}", transportDTO);
        if (!transportRepository.existsById(transportDTO.getId())) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }
        Transport transport = TransportMapper.toEntity(transportDTO);
        transport = transportRepository.save(transport);
        return TransportMapper.toDto(transport);
    }

    /**
     * Partially update a transport.
     *
     * @param transportDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<TransportDTO> partialUpdate(TransportDTO transportDTO) {
        log.debug("Request to partially update Transport : {}", transportDTO);

        if (!transportRepository.existsById(transportDTO.getId())) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        return transportRepository
            .findById(transportDTO.getId())
            .map(existingTransport -> {
                TransportMapper.partialUpdate(existingTransport, transportDTO);

                return existingTransport;
            })
            .map(transportRepository::save)
            .map(TransportMapper::toDto);
    }

    /**
     * Get all the transports.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<TransportDTO> findAll() {
        log.debug("Request to get all Transports");
        return transportRepository.findAll().stream().map(TransportMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
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
        return transportRepository.findById(id).map(TransportMapper::toDto);
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
