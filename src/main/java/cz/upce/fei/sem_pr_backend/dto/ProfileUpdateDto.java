package cz.upce.fei.sem_pr_backend.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class ProfileUpdateDto implements Serializable {
    private final Long id;
    @NotNull
    private final String nickname;
    private final String profilePicturePath;
}
