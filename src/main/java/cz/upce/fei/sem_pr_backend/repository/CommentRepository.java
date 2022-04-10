package cz.upce.fei.sem_pr_backend.repository;

import cz.upce.fei.sem_pr_backend.domain.Comment;
import cz.upce.fei.sem_pr_backend.domain.Issue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    @Query(value = "select c from Comment c where c.issue.id = :issueId")
    List<Comment> findAllByIssueId(@Param("issueId") Long id);

    @Query(value = "SELECT c from Comment c where c.author.username = :authorName")
    List<Issue> findAllByAuthor(@Param("authorName") String authorname);
}
