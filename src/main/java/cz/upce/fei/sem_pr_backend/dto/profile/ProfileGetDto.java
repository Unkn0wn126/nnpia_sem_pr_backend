package cz.upce.fei.sem_pr_backend.dto.profile;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProfileGetDto implements Serializable {
    private Long id;
    @NotNull
    private String nickname;
    private String profilePicturePath;
    private Timestamp created;
    private Timestamp lastEdited;
}
