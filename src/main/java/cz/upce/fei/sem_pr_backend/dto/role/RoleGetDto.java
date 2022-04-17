package cz.upce.fei.sem_pr_backend.dto.role;

import cz.upce.fei.sem_pr_backend.domain.enum_type.RoleType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleGetDto implements Serializable {
    private RoleType type;
}
