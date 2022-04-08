package cz.upce.fei.sem_pr_backend.repository;

import cz.upce.fei.sem_pr_backend.domain.ApplicationUser;
import cz.upce.fei.sem_pr_backend.domain.enum_type.UserState;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ApplicationUserRepository extends JpaRepository<ApplicationUser, Long> {
    Optional<ApplicationUser> findByUsername(String username);

    Optional<ApplicationUser> findByEmail(String email);

    void deleteById(Long id);

    Page<ApplicationUser> findAllByState(UserState state, Pageable pageable);
}
