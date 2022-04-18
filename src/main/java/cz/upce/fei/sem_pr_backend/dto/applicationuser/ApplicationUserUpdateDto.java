package cz.upce.fei.sem_pr_backend.dto.applicationuser;

import cz.upce.fei.sem_pr_backend.dto.profile.ProfileUpdateDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApplicationUserUpdateDto implements Serializable {
    @Email
    private String email;
    @NotNull
    private ProfileUpdateDto profile;
    @NotNull
    @Pattern(regexp = "ACTIVE|INACTIVE|BANNED")
    private String state;
}
