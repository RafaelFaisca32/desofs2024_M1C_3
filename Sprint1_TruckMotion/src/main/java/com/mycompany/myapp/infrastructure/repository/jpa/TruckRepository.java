package com.mycompany.myapp.infrastructure.repository.jpa;

import com.mycompany.myapp.domain.truck.ITruckRepository;
import com.mycompany.myapp.domain.truck.Truck;
import java.util.UUID;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Truck entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TruckRepository extends JpaRepository<Truck, UUID>, ITruckRepository {}
