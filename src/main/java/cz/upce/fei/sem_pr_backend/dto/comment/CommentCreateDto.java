package cz.upce.fei.sem_pr_backend.dto.comment;

import cz.upce.fei.sem_pr_backend.domain.ApplicationUser;
import cz.upce.fei.sem_pr_backend.domain.Comment;
import cz.upce.fei.sem_pr_backend.dto.DTO;
import cz.upce.fei.sem_pr_backend.dto.applicationuser.ApplicationUserGetDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentCreateDto implements Serializable, DTO {
    @NotNull
    @NotEmpty
    private String content;
}
