package cz.upce.fei.sem_pr_backend.dto.comment;

import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;

@Data
public class CommentGetDto implements Serializable {
    private final Long id;
    private final String content;
    private final Timestamp created;
    private final Timestamp lastEdited;
}
