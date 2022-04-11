package cz.upce.fei.sem_pr_backend.dto.issue;

import com.fasterxml.jackson.annotation.JsonProperty;
import cz.upce.fei.sem_pr_backend.domain.enum_type.IssueSeverity;
import cz.upce.fei.sem_pr_backend.domain.enum_type.IssueVisibility;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IssueCreateDto implements Serializable {
    private String header;
    private String content;
    @NotNull
    private IssueSeverity severity;
    @NotNull
    private IssueVisibility visibility;
    @Future(message = "Can't set goals for the past")
    private Date dueDate;
}
