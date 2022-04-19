package cz.upce.fei.sem_pr_backend.controller.v1;

import cz.upce.fei.sem_pr_backend.dto.issue.IssueCreateDto;
import cz.upce.fei.sem_pr_backend.dto.issue.IssueGetDto;
import cz.upce.fei.sem_pr_backend.dto.issue.IssuePageGetDto;
import cz.upce.fei.sem_pr_backend.dto.issue.IssueUpdateDto;
import cz.upce.fei.sem_pr_backend.service.IssueService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;
import java.security.Principal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/issues")
public class IssueController {

    private final IssueService issueService;

    public IssueController(@Qualifier("issueServiceImpl") IssueService issueService) {
        this.issueService = issueService;
    }

    @GetMapping("/")
    public IssuePageGetDto getAllIssues(Principal principal,
                                        @RequestParam(defaultValue = "0") Integer pageNumber,
                                        @RequestParam(defaultValue = "5") Integer pageSize,
                                        @Valid @Pattern(regexp = "ASC|DESC") @RequestParam(defaultValue = "ASC") String direction,
                                        @RequestParam(defaultValue = "published") String... orderBy){
        return issueService.getAllIssues(principal, pageNumber, pageSize, direction, orderBy);
    }

    @GetMapping("/{id}")
    public IssueGetDto getIssueById(Principal principal, @PathVariable Long id){
        return issueService.getIssueById(principal, id);
    }

    @GetMapping("/user/{username}")
    public IssuePageGetDto getIssuesByAuthorUsername(Principal principal,
                                                         @RequestParam(defaultValue = "0") Integer pageNumber,
                                                         @RequestParam(defaultValue = "5") Integer pageSize,
                                                         @PathVariable String username,
                                                         @Valid @Pattern(regexp = "ASC|DESC") @RequestParam(defaultValue = "ASC") String direction,
                                                         @RequestParam(defaultValue = "published") String... orderBy){
        return issueService.getIssuesByAuthorName(principal, username, pageNumber, pageSize, direction, orderBy);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteIssueById(Principal principal, @PathVariable Long id){
        issueService.deleteIssue(principal, id);
    }

    @PutMapping("/update/{id}")
    public void updateIssueById(Principal principal, @Valid @RequestBody IssueUpdateDto issueUpdateDto, @PathVariable Long id){
        issueService.updateIssue(principal, id, issueUpdateDto);
    }

    @PostMapping("/create")
    public void postIssue(Principal principal, @Valid @RequestBody IssueCreateDto issueCreateDto){
        issueService.createIssue(principal, issueCreateDto);
    }
}
