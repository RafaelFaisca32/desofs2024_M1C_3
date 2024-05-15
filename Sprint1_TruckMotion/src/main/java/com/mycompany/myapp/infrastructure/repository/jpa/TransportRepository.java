package com.mycompany.myapp.infrastructure.repository.jpa;

import com.mycompany.myapp.domain.transport.ITransportRepository;
import com.mycompany.myapp.domain.transport.Transport;
import java.util.UUID;

import com.mycompany.myapp.domain.transport.TransportId;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Transport entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TransportRepository extends JpaRepository<Transport, TransportId>, ITransportRepository {}
