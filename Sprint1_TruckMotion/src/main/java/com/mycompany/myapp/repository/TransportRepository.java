package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Transport;
import java.util.UUID;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Transport entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TransportRepository extends JpaRepository<Transport, UUID> {}
