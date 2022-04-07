package cz.upce.fei.sem_pr_backend.repository;

import cz.upce.fei.sem_pr_backend.entity.ApplicationUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplicationUserRepository extends JpaRepository<ApplicationUser, Long> {
}
