package com.mycompany.myapp.domain.serviceRequest;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IServiceRequestRepository {
    List<ServiceRequest> findAll();

    ServiceRequest save(ServiceRequest serviceRequest);

    Optional<ServiceRequest> findById(ServiceRequestId id);

    void deleteById(ServiceRequestId id);

    ServiceRequest saveAndFlush(ServiceRequest serviceRequest);

    long count();

    boolean existsById(ServiceRequestId id);
}
