package cz.upce.fei.sem_pr_backend.domain;

import cz.upce.fei.sem_pr_backend.domain.enum_type.UserState;
import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class ApplicationUser{
    @Id
    @Column(name = "id", nullable = false, updatable = false)
    @SequenceGenerator(name = "users_id_seq", sequenceName = "users_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "users_id_seq")
    private Long id;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles",
    joinColumns = {@JoinColumn(name = "user_id")},
    inverseJoinColumns = {@JoinColumn(name = "role_id")})
    private Set<Role> roles = new HashSet<>();

    @NotEmpty
    @Column(length = 45, unique = true, nullable = false)
    private String username;

    @Email
    @Column(length = 45, unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @OneToMany(mappedBy = "author", orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @ToString.Exclude
    private Set<Issue> issues = new java.util.LinkedHashSet<>();

    @OneToMany(mappedBy = "author", orphanRemoval = true)
    @ToString.Exclude
    private Set<Comment> comments = new java.util.LinkedHashSet<>();

    @OneToOne(mappedBy = "user", targetEntity = Profile.class, orphanRemoval = true)
    private Profile profile;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserState state;

    @Column(nullable = false)
    @CreationTimestamp
    private Timestamp created;

    @UpdateTimestamp
    private Timestamp lastEdited;

    public ApplicationUser(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.state = UserState.ACTIVE;
    }

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
