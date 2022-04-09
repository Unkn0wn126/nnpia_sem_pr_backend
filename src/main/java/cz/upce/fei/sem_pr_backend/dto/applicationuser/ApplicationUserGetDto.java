package cz.upce.fei.sem_pr_backend.dto.applicationuser;

import cz.upce.fei.sem_pr_backend.domain.enum_type.UserState;
import cz.upce.fei.sem_pr_backend.dto.profile.ProfileGetDto;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.sql.Timestamp;

@Data
public class ApplicationUserGetDto implements Serializable {
    private final Long id;
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
