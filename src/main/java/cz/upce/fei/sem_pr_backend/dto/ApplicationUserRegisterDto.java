package cz.upce.fei.sem_pr_backend.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

@Data
public class ApplicationUserRegisterDto implements Serializable {
    @NotEmpty
    @NotNull
    private final String username;
    @Email
    @NotNull
    private final String email;
    @NotNull
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$")
    private final String password;
}
