package com.mycompany.myapp.infrastructure.repository.jpa;

import com.mycompany.myapp.domain.manager.IManagerRepository;
import com.mycompany.myapp.domain.manager.Manager;
import java.util.UUID;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Manager entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ManagerRepository extends JpaRepository<Manager, UUID>, IManagerRepository {}
