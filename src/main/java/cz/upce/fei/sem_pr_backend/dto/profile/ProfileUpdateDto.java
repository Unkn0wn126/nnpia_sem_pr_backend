package cz.upce.fei.sem_pr_backend.dto.profile;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProfileUpdateDto implements Serializable {
    private Long id;
    @NotNull
    private String nickname;
    private String profilePicturePath;
}
