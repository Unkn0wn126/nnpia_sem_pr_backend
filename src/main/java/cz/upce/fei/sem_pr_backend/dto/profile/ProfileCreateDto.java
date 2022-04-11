package cz.upce.fei.sem_pr_backend.dto.profile;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProfileCreateDto implements Serializable {
    @NotNull
    private String nickname;
    private String profilePicturePath;
}
