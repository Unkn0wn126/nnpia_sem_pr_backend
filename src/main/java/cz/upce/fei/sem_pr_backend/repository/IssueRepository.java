package cz.upce.fei.sem_pr_backend.repository;

import cz.upce.fei.sem_pr_backend.entity.Issue;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IssueRepository extends JpaRepository<Issue, Long> {
}
