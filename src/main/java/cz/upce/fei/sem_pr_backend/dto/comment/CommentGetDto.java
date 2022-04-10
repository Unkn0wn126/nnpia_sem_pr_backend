package cz.upce.fei.sem_pr_backend.dto.comment;

import cz.upce.fei.sem_pr_backend.domain.ApplicationUser;
import cz.upce.fei.sem_pr_backend.domain.Comment;
import cz.upce.fei.sem_pr_backend.domain.Issue;
import cz.upce.fei.sem_pr_backend.dto.DTO;
import cz.upce.fei.sem_pr_backend.dto.applicationuser.ApplicationUserGetDto;
import cz.upce.fei.sem_pr_backend.dto.issue.IssueGetDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;

import java.io.Serializable;
import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentGetDto implements Serializable, DTO {
    private Long id;
    private String content;
    private Timestamp created;
    private Timestamp lastEdited;
    private ApplicationUserGetDto author;

    @Override
    public ModelMapper updateModelMapper(ModelMapper modelMapper) {
        Converter<ApplicationUser, ApplicationUserGetDto> convertToUserDto = context -> {
            ApplicationUser user = context.getSource();
            return modelMapper.map(user, ApplicationUserGetDto.class);
        };
        modelMapper.typeMap(Comment.class, CommentGetDto.class)
                .addMappings(mapping -> {
                    mapping.using(convertToUserDto).map(Comment::getAuthor, CommentGetDto::setAuthor);
                });

        return modelMapper;
    }
}
