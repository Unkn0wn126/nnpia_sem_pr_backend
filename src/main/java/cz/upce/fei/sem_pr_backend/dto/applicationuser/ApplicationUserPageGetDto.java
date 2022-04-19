package cz.upce.fei.sem_pr_backend.dto.applicationuser;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationUserPageGetDto {
    private Long totalItems;
    private Integer totalPages;
    private Integer currentPage;
    private List<ApplicationUserGetDto> users;
}
