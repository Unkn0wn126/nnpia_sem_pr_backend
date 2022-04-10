package cz.upce.fei.sem_pr_backend.dto.issue;

import cz.upce.fei.sem_pr_backend.domain.ApplicationUser;
import cz.upce.fei.sem_pr_backend.domain.Issue;
import cz.upce.fei.sem_pr_backend.domain.enum_type.IssueCompletionState;
import cz.upce.fei.sem_pr_backend.domain.enum_type.IssueSeverity;
import cz.upce.fei.sem_pr_backend.domain.enum_type.IssueVisibility;
import cz.upce.fei.sem_pr_backend.dto.DTO;
import cz.upce.fei.sem_pr_backend.dto.applicationuser.ApplicationUserGetDto;
import cz.upce.fei.sem_pr_backend.dto.comment.CommentGetDto;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Set;

@Data
@NoArgsConstructor
public class IssueGetDto implements Serializable, DTO {
    private Long id;
    private String header;
    private String content;
    private Timestamp published;
    private Timestamp lastEdited;
    @NotNull
    private IssueSeverity severity;
    @NotNull
    private IssueVisibility visibility;
    @Future(message = "Can't set goals for the past")
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
