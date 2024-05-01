package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.ServiceStatus;
import java.util.UUID;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the ServiceStatus entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ServiceStatusRepository extends JpaRepository<ServiceStatus, UUID> {}
