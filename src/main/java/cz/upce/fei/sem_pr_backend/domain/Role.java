package cz.upce.fei.sem_pr_backend.domain;

import cz.upce.fei.sem_pr_backend.domain.enum_type.RoleType;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "roles")
public class Role {
    @Id
    @Column(name = "id", nullable = false, updatable = false)
    @SequenceGenerator(name = "roles_id_seq", sequenceName = "roles_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "roles_id_seq")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(unique = true, nullable = false)
    private RoleType type;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "roles")
    private Set<ApplicationUser> users;
//    @OneToMany(mappedBy = "role")
//    @ToString.Exclude
//    private Set<UserHasRole> users;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Role role = (Role) o;
        return id != null && Objects.equals(id, role.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
