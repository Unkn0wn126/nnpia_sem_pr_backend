package cz.upce.fei.sem_pr_backend.entity;

import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Table(name = "issues")
public class Issue {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String header;

    private String content;

    @CreationTimestamp
    @NotNull
    private Timestamp published;

    @UpdateTimestamp
    private Timestamp lastEdited;

    @Enumerated(EnumType.STRING)
    @NotNull
    @Column
    private IssueSeverity severity;

    @Enumerated(EnumType.STRING)
    @NotNull
    @Column
    private IssueVisibility visibility;

    private LocalDateTime dueDate;

    @Enumerated(EnumType.STRING)
    @NotNull
    @Column
    private IssueCompletionState completionState;

    @ManyToOne(optional = false)
    @JoinColumn(name = "author_id", referencedColumnName = "id")
    private ApplicationUser author;

    @OneToMany(mappedBy = "issue", targetEntity = Comment.class)
    @ToString.Exclude
    private Set<Comment> comments;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Issue issue = (Issue) o;
        return id != null && Objects.equals(id, issue.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
