package com.mycompany.myapp.infrastructure.repository.jpa;

import com.mycompany.myapp.domain.location.ILocationRepository;
import com.mycompany.myapp.domain.location.Location;
import java.util.UUID;

import com.mycompany.myapp.domain.location.LocationId;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Location entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LocationRepository extends JpaRepository<Location, LocationId>, ILocationRepository {}
