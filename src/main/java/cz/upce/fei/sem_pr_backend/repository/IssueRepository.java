package cz.upce.fei.sem_pr_backend.repository;

import cz.upce.fei.sem_pr_backend.domain.Issue;
import cz.upce.fei.sem_pr_backend.domain.enum_type.IssueVisibility;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface IssueRepository extends JpaRepository<Issue, Long> {
    @Query(value = "select i from Issue i " +
            "where i.id = :id " +
            "and (i.visibility in (:visibility) or i.author.username = :accessorName) " +
            "order by i.published")
    Optional<Issue> findByIdAndVisibility(@Param("id") Long id, @Param("visibility") List<IssueVisibility> issueVisibility, @Param("accessorName") String accessorName);

    @Query(value = "SELECT i from Issue i " +
            "where (i.author.username = :authorName and i.visibility in (:visibility)) " +
            "or (i.author.username = :authorName and i.author.username = :accessorName) " +
            "order by i.published")
    Page<Issue> findAllByAuthor(@Param("authorName") String authorname, @Param("visibility") List<IssueVisibility> issueVisibility, @Param("accessorName") String accessorName, Pageable pageable);

    @Query(value = "SELECT i from Issue i " +
            "where i.visibility in (:visibility) or i.author.username = :accessorName " +
            "order by i.published")
    Page<Issue> findAllByVisibility(@Param("visibility") List<IssueVisibility> issueVisibility, @Param("accessorName") String accessorName, Pageable pageable);
}
