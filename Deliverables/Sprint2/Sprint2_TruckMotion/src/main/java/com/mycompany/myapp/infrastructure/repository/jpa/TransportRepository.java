package com.mycompany.myapp.infrastructure.repository.jpa;

import com.mycompany.myapp.domain.transport.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Transport entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TransportRepository extends JpaRepository<Transport, TransportId>, ITransportRepository {

    @Query("SELECT DISTINCT d.id,  CONCAT(u.internalUser.firstName, ' ', u.internalUser.lastName) as name " +
        "FROM Driver d " +
        "JOIN d.applicationUser u " +
        "WHERE d.id NOT IN (" +
        "SELECT t.driver.id FROM Transport t WHERE " +
        "(t.startTime <= :endDate AND t.endTime >= :startDate))")
    List<Object[]> findFreeDrivers(TransportEndTime startDate, TransportStartTime endDate);
}
