package cz.upce.fei.sem_pr_backend.dto.comment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentPageGetDto {
    private Long totalItems;
    private Integer totalPages;
    private Integer currentPage;
    private List<CommentGetDto> comments;
}
