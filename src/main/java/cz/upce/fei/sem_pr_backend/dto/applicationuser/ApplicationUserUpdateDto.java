package cz.upce.fei.sem_pr_backend.dto.applicationuser;

import cz.upce.fei.sem_pr_backend.dto.profile.ProfileUpdateDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApplicationUserUpdateDto implements Serializable {
    private Long id;
    private ProfileUpdateDto profile;
}
