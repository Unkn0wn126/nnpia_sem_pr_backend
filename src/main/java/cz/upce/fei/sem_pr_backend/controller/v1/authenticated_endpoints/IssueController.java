package cz.upce.fei.sem_pr_backend.controller.v1.authenticated_endpoints;

import cz.upce.fei.sem_pr_backend.dto.issue.IssueCreateDto;
import cz.upce.fei.sem_pr_backend.dto.issue.IssueGetDto;
import cz.upce.fei.sem_pr_backend.dto.issue.IssueUpdateDto;
import cz.upce.fei.sem_pr_backend.service.IssueService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/issues")
public class IssueController {

    private final IssueService issueService;

    public IssueController(@Qualifier("issueServiceImpl") IssueService issueService) {
        this.issueService = issueService;
    }

    @GetMapping("/")
    public List<IssueGetDto> getAllIssues(@RequestParam(defaultValue = "0") Integer pageNumber, @RequestParam(defaultValue = "5") Integer pageSize, Principal principal){
        return issueService.getAllIssues(principal, pageNumber, pageSize);
    }

    @GetMapping("/{id}")
    public IssueGetDto getIssueById(Principal principal, @PathVariable Long id){
        return issueService.getIssueById(principal, id);
    }

    @GetMapping("/user/{username}")
    public List<IssueGetDto> getIssuesByAuthorUsername(@RequestParam(defaultValue = "0") Integer pageNumber, @RequestParam(defaultValue = "5") Integer pageSize, Principal principal, @PathVariable String username){
        return issueService.getIssuesByAuthorName(principal, username, pageNumber, pageSize);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteIssueById(Principal principal, @PathVariable Long id){
        issueService.deleteIssue(principal, id);
    }

    @PutMapping("/update/{id}")
    public void updateIssueById(Principal principal, @RequestBody @Valid IssueUpdateDto issueUpdateDto, @PathVariable Long id){
        issueService.updateIssue(principal, id, issueUpdateDto);
    }

    @PostMapping("/create")
    public void postIssue(Principal principal, @RequestBody @Valid IssueCreateDto issueCreateDto){
        issueService.createIssue(principal, issueCreateDto);
    }
}
