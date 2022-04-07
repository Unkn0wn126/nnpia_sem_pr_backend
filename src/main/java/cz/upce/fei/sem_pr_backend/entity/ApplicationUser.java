package cz.upce.fei.sem_pr_backend.entity;

import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.sql.Timestamp;
import java.util.Objects;
import java.util.Set;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Table(name = "users")
public class ApplicationUser {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToMany(mappedBy = "users", fetch = FetchType.EAGER)
    private Set<Role> roles;

    @NotNull
    @Column(length = 45, unique = true)
    private String username;

    @NotNull
    @Pattern(regexp = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")
    @Column(length = 45, unique = true)
    private String email;

    @Column
    @NotNull
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$")
    private String password;

    @OneToMany(mappedBy = "author", targetEntity = Issue.class)
    @ToString.Exclude
    private Set<Issue> issues;

    @OneToMany(mappedBy = "author", targetEntity = Comment.class)
    @ToString.Exclude
    private Set<Comment> comments;

    @OneToOne(mappedBy = "user", targetEntity = Profile.class)
    private Profile profile;

    @Enumerated(EnumType.STRING)
    @NotNull
    @Column
    private UserState state;

    @Column
    @CreationTimestamp
    @NotNull
    private Timestamp created;

    @Column
    @UpdateTimestamp
    private Timestamp lastEdited;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        ApplicationUser that = (ApplicationUser) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
