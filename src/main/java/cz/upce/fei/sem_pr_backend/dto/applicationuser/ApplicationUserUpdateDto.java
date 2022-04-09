package cz.upce.fei.sem_pr_backend.dto.applicationuser;

import cz.upce.fei.sem_pr_backend.dto.profile.ProfileUpdateDto;
import lombok.Data;

import java.io.Serializable;

@Data
public class ApplicationUserUpdateDto implements Serializable {
    private final Long id;
    private final ProfileUpdateDto profile;
}
