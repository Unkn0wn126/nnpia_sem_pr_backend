package cz.upce.fei.sem_pr_backend.dto.issue;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import cz.upce.fei.sem_pr_backend.domain.ApplicationUser;
import cz.upce.fei.sem_pr_backend.domain.Issue;
import cz.upce.fei.sem_pr_backend.domain.enum_type.IssueCompletionState;
import cz.upce.fei.sem_pr_backend.domain.enum_type.IssueSeverity;
import cz.upce.fei.sem_pr_backend.domain.enum_type.IssueVisibility;
import cz.upce.fei.sem_pr_backend.dto.DTO;
import cz.upce.fei.sem_pr_backend.dto.JsonDateDeserializer;
import cz.upce.fei.sem_pr_backend.dto.JsonDateSerializer;
import cz.upce.fei.sem_pr_backend.dto.JsonTimeStampSerializer;
import cz.upce.fei.sem_pr_backend.dto.applicationuser.ApplicationUserGetDto;
import cz.upce.fei.sem_pr_backend.dto.comment.CommentGetDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IssueGetDto implements Serializable, DTO {
    private Long id;
    private String header;
    private String content;
    @JsonSerialize(using = JsonTimeStampSerializer.class)
    private Timestamp published;
    @JsonSerialize(using = JsonTimeStampSerializer.class)
    private Timestamp lastEdited;
    @NotNull
    private IssueSeverity severity;
    @NotNull
    private IssueVisibility visibility;
    @Future(message = "Can't set goals for the past")
    @JsonSerialize(using = JsonDateSerializer.class)
    @DateTimeFormat(pattern = "dd-yyy-MM")
    private Date dueDate;
    @NotNull
    private IssueCompletionState completionState;
    private ApplicationUserGetDto author;
    private Set<CommentGetDto> comments;

    @Override
    public ModelMapper updateModelMapper(ModelMapper modelMapper) {
        Converter<ApplicationUser, ApplicationUserGetDto> convertToUserDto = context -> {
            ApplicationUser user = context.getSource();
            return modelMapper.map(user, ApplicationUserGetDto.class);
        };
        modelMapper.typeMap(Issue.class, IssueGetDto.class)
                .addMappings(mapping -> {
                    mapping.using(convertToUserDto).map(Issue::getAuthor, IssueGetDto::setAuthor);
                });

        return modelMapper;
    }
}
