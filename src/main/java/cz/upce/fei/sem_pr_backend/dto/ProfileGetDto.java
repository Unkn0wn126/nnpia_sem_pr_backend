package cz.upce.fei.sem_pr_backend.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.sql.Timestamp;

@Data
public class ProfileGetDto implements Serializable {
    private final Long id;
    @NotNull
    private final String nickname;
    private final String profilePicturePath;
    private final Timestamp created;
    private final Timestamp lastEdited;
}
