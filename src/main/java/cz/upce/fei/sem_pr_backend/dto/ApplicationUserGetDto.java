package cz.upce.fei.sem_pr_backend.dto;

import cz.upce.fei.sem_pr_backend.dto.IssueGetDto;
import cz.upce.fei.sem_pr_backend.dto.ProfileGetDto;
import cz.upce.fei.sem_pr_backend.dto.RoleGetDto;
import cz.upce.fei.sem_pr_backend.entity.enum_type.UserState;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Set;

@Data
public class ApplicationUserGetDto implements Serializable {
    private final Long id;
    private final Set<RoleGetDto> roles;
    @NotEmpty
    @NotNull
    private final String username;
    @Email
    @NotNull
    private final String email;
    private final ProfileGetDto profile;
    @NotNull
    private final UserState state;
    private final Timestamp created;
    private final Timestamp lastEdited;
}
