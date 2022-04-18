package cz.upce.fei.sem_pr_backend.dto.profile;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProfileUpdateDto implements Serializable {
    @NotNull
    @NotEmpty
    private String nickname;
    private String profilePicture;
}
