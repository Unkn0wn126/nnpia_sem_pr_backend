package cz.upce.fei.sem_pr_backend.dto.profile;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import cz.upce.fei.sem_pr_backend.dto.JsonTimeStampSerializer;
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
    @JsonSerialize(using = JsonTimeStampSerializer.class)
    private Timestamp created;
    @JsonSerialize(using = JsonTimeStampSerializer.class)
    private Timestamp lastEdited;
}
