package cz.upce.fei.sem_pr_backend.domain;

import cz.upce.fei.sem_pr_backend.domain.enum_type.UserState;
import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Objects;
import java.util.Set;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Table(name = "users")
public class ApplicationUser implements UserDetails {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "users", cascade = CascadeType.REMOVE)
    private Set<Role> roles = new java.util.LinkedHashSet<>();

    @NotEmpty
    @NotNull
    @Column(length = 45, unique = true, nullable = false)
    private String username;

    @Email
    @NotNull
    @Column(length = 45, unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    @NotNull
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$")
    private String password;

    @OneToMany(mappedBy = "author", orphanRemoval = true)
    @ToString.Exclude
    private Set<Issue> issues = new java.util.LinkedHashSet<>();

    @OneToMany(mappedBy = "author", orphanRemoval = true)
    @ToString.Exclude
    private Set<Comment> comments = new java.util.LinkedHashSet<>();

    @OneToOne(mappedBy = "user", targetEntity = Profile.class, orphanRemoval = true)
    private Profile profile;

    @Enumerated(EnumType.STRING)
    @NotNull
    @Column(nullable = false)
    private UserState state;

    @Column(nullable = false)
    @CreationTimestamp
    private Timestamp created;

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

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
