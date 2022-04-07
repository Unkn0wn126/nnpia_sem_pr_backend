package cz.upce.fei.sem_pr_backend.entity;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Table(name = "issues")
public class Issue {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String header;

    private String content;

    private Timestamp published;

    private Timestamp lastEdited;

    private int severity;

    private int visibility;

    private LocalDateTime dueDate;

    private int completionState;

    @OneToOne(optional = false, mappedBy = "id")
    private ApplicationUser authorId;

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
