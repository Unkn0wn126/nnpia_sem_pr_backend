package cz.upce.fei.sem_pr_backend.repository;

import cz.upce.fei.sem_pr_backend.entity.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileRepository extends JpaRepository<Profile, Long> {
}
