package cz.upce.fei.sem_pr_backend.dto.applicationuser;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import cz.upce.fei.sem_pr_backend.domain.enum_type.UserState;
import cz.upce.fei.sem_pr_backend.dto.JsonTimeStampSerializer;
import cz.upce.fei.sem_pr_backend.dto.profile.ProfileGetDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApplicationUserGetDto implements Serializable {
    private Long id;
    @NotEmpty
    @NotNull
    private String username;
    @Email
    @NotNull
    private String email;
    private ProfileGetDto profile;
    @NotNull
    private UserState state;
    @JsonSerialize(using = JsonTimeStampSerializer.class)
    private Timestamp created;
    @JsonSerialize(using = JsonTimeStampSerializer.class)
    private Timestamp lastEdited;
}
