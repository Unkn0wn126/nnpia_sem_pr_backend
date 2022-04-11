package cz.upce.fei.sem_pr_backend.dto.applicationuser;

import com.fasterxml.jackson.annotation.JsonProperty;
import cz.upce.fei.sem_pr_backend.dto.profile.ProfileCreateDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApplicationUserCreateDto implements Serializable {
    @NotEmpty
    @NotNull
    private String username;
    @Email
    @NotNull
    private String email;
    @NotNull
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$")
    private String password;

    @JsonProperty("profile")
    private ProfileCreateDto profile;
}
