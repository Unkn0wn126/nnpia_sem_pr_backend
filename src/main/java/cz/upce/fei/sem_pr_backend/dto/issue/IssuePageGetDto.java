package cz.upce.fei.sem_pr_backend.dto.issue;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IssuePageGetDto {
    private Long totalItems;
    private Integer totalPages;
    private Integer currentPage;
    private List<IssueGetDto> issues;
}
