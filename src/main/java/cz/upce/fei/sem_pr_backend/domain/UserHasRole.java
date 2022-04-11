package cz.upce.fei.sem_pr_backend.domain;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class UserHasRole {

    @EmbeddedId
    private UserHasRoleId id;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @MapsId("user_id")
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private ApplicationUser user;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @MapsId("role_id")
    @JoinColumn(name = "role_id", referencedColumnName = "id")
    private Role role;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        UserHasRole that = (UserHasRole) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
