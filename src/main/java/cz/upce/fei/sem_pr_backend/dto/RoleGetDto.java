package cz.upce.fei.sem_pr_backend.dto;

import cz.upce.fei.sem_pr_backend.domain.enum_type.RoleType;
import lombok.Data;

import java.io.Serializable;

@Data
public class RoleGetDto implements Serializable {
    private final RoleType type;
}
