package cz.upce.fei.sem_pr_backend.repository;

import cz.upce.fei.sem_pr_backend.domain.UserHasRole;
import cz.upce.fei.sem_pr_backend.domain.UserHasRoleId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRoleRepository extends JpaRepository<UserHasRole, UserHasRoleId> {
}
