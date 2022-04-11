package cz.upce.fei.sem_pr_backend.dto.applicationuser;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApplicationUserUpdatePasswordDto implements Serializable {
    private Long id;
    private String oldPassword;
    private String newPassword;
}
