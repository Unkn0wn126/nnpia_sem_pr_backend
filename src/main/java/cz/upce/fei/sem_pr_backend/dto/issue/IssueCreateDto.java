package cz.upce.fei.sem_pr_backend.dto.issue;

import com.fasterxml.jackson.annotation.JsonProperty;
import cz.upce.fei.sem_pr_backend.domain.enum_type.IssueSeverity;
import cz.upce.fei.sem_pr_backend.domain.enum_type.IssueVisibility;
import cz.upce.fei.sem_pr_backend.dto.dto_validation.EnumNamePattern;
import cz.upce.fei.sem_pr_backend.dto.dto_validation.IssueSeveritySubset;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IssueCreateDto implements Serializable {
    @NotNull
    @NotEmpty
    private String header;
    @NotNull
    @NotEmpty
    private String content;
    @NotNull
    @Pattern(regexp = "LOW|MEDIUM|HIGH")
    private String severity;
    @NotNull
    @Pattern(regexp = "PUBLIC|INTERNAL|PRIVATE")
    private String visibility;
    @Future(message = "Can't set goals for the past")
    private Date dueDate;
}
