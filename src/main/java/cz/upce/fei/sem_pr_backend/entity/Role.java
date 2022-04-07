package cz.upce.fei.sem_pr_backend.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Role {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(unique = true, nullable = false)
    private RoleType type;
}
