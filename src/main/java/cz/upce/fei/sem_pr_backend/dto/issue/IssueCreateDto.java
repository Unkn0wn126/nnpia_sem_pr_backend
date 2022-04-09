package cz.upce.fei.sem_pr_backend.dto.issue;

import com.fasterxml.jackson.annotation.JsonProperty;
import cz.upce.fei.sem_pr_backend.domain.enum_type.IssueSeverity;
import cz.upce.fei.sem_pr_backend.domain.enum_type.IssueVisibility;
import lombok.Data;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@Data
public class IssueCreateDto implements Serializable {
    private final String header;
    private final String content;
    @NotNull
    private final IssueSeverity severity;
    @NotNull
    private final IssueVisibility visibility;
    @Future(message = "Can't set goals for the past")
    private final Date dueDate;
}
