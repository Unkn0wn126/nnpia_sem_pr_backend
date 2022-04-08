package cz.upce.fei.sem_pr_backend.repository;

import cz.upce.fei.sem_pr_backend.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
