package cz.upce.fei.sem_pr_backend.dto;

import cz.upce.fei.sem_pr_backend.domain.enum_type.IssueCompletionState;
import cz.upce.fei.sem_pr_backend.domain.enum_type.IssueSeverity;
import cz.upce.fei.sem_pr_backend.domain.enum_type.IssueVisibility;
import lombok.Data;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Set;

@Data
public class IssueGetDto implements Serializable {
    private final Long id;
    private final String header;
    private final String content;
    private final Timestamp published;
    private final Timestamp lastEdited;
    @NotNull
    private final IssueSeverity severity;
    @NotNull
    private final IssueVisibility visibility;
    @Future(message = "Can't set goals for the past")
    private final Date dueDate;
    @NotNull
    private final IssueCompletionState completionState;
    private final ApplicationUserGetDto author;
    private final Set<CommentGetDto> comments;
}
