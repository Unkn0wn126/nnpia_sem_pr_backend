package cz.upce.fei.sem_pr_backend.dto.issue;

import cz.upce.fei.sem_pr_backend.domain.enum_type.IssueCompletionState;
import cz.upce.fei.sem_pr_backend.domain.enum_type.IssueSeverity;
import cz.upce.fei.sem_pr_backend.domain.enum_type.IssueVisibility;
import lombok.Data;

import javax.validation.constraints.Future;
import java.io.Serializable;
import java.util.Date;

@Data
public class IssueUpdateDto implements Serializable {
    private final String header;
    private final String content;
    private final IssueSeverity severity;
    private final IssueVisibility visibility;
    @Future(message = "Can't set goals for the past")
    private final Date dueDate;
    private final IssueCompletionState completionState;
}
