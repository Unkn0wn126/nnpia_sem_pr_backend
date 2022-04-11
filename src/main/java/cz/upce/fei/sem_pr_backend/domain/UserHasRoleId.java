package cz.upce.fei.sem_pr_backend.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.GeneratedValue;
import java.io.Serializable;

@Data
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class UserHasRoleId implements Serializable {
    @GeneratedValue
    @Column(name = "user_id")
    private Long userId;

    @GeneratedValue
    @Column(name = "role_id")
    private Long userRoleId;
}
