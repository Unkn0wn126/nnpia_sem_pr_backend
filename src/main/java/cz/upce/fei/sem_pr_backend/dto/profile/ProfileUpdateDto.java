package cz.upce.fei.sem_pr_backend.dto.profile;

import com.fasterxml.jackson.annotation.JsonProperty;
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
