package com.mycompany.myapp.infrastructure.repository.jpa;

import com.mycompany.myapp.domain.driver.Driver;

import java.util.Optional;

import com.mycompany.myapp.domain.driver.DriverId;
import com.mycompany.myapp.domain.driver.IDriverRepository;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Driver entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DriverRepository extends JpaRepository<Driver, DriverId>, IDriverRepository {
    @Query("SELECT d FROM Driver d " +
        "JOIN d.applicationUser au " +
        "JOIN au.internalUser u " +
        "WHERE u.id = :userId" )
    Optional<Driver> getByUserId(@Param("userId") Long userId);
}
