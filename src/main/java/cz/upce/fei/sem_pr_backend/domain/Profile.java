package cz.upce.fei.sem_pr_backend.domain;

import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Blob;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "profiles")
public class Profile {
    @Id
    @Column(name = "id", nullable = false, updatable = false)
    @SequenceGenerator(name = "profiles_id_seq", sequenceName = "profiles_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "profiles_id_seq")
    private Long id;

    @Column(nullable = false)
    @NotNull
    private String nickname;

    @Column(length = 10485760)
    private String profilePicture;

    @Column(nullable = false)
    @CreationTimestamp
    private Timestamp created;

    @UpdateTimestamp
    private Timestamp lastEdited;

    @NotNull
    @OneToOne(optional = false, targetEntity = ApplicationUser.class)
    @JoinColumn(nullable = false, unique = true)
    private ApplicationUser user;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Profile profile = (Profile) o;
        return id != null && Objects.equals(id, profile.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
