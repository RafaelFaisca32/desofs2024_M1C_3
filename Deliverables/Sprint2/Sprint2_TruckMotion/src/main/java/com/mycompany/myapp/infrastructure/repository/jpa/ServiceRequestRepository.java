package com.mycompany.myapp.infrastructure.repository.jpa;

import com.mycompany.myapp.domain.serviceRequest.IServiceRequestRepository;
import com.mycompany.myapp.domain.serviceRequest.ServiceRequest;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.mycompany.myapp.domain.serviceRequest.ServiceRequestId;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the ServiceRequest entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ServiceRequestRepository extends JpaRepository<ServiceRequest, ServiceRequestId>, IServiceRequestRepository {
    @Query("SELECT c FROM ServiceRequest c " +
        "JOIN c.customer au " +
        "JOIN au.applicationUser u " +
        "WHERE u.id = :userId" )
    List<ServiceRequest> getByUserId(@Param("userId") Long userId);
}
