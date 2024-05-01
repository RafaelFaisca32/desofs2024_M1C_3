package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.ServiceRequest;
import java.util.UUID;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the ServiceRequest entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ServiceRequestRepository extends JpaRepository<ServiceRequest, UUID> {}
