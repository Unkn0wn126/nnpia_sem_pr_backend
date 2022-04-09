package cz.upce.fei.sem_pr_backend.controller;

import cz.upce.fei.sem_pr_backend.dto.issue.IssueCreateDto;
import cz.upce.fei.sem_pr_backend.dto.issue.IssueGetDto;
import cz.upce.fei.sem_pr_backend.dto.issue.IssueUpdateDto;
import cz.upce.fei.sem_pr_backend.service.IssueService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/issues")
public class IssueController {

    private final IssueService issueService;

    public IssueController(IssueService issueService) {
        this.issueService = issueService;
    }

    @GetMapping("/")
    public List<IssueGetDto> getIssues(){
        return issueService.getAllIssues();
    }

    @GetMapping("/{id}")
    public void getIssueById(@PathVariable Long id){
        System.out.println("issue with id " + id);
    }

    @GetMapping("/user/{username}")
    public void getIssueByAuthorUsername(@PathVariable String username){
        System.out.println("issue with author " + username);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteIssueById(Principal principal, @PathVariable Long id){
        System.out.println("delete issue with id " + id);
    }

    @PutMapping("/update")
    public void updateIssueById(Principal principal, @RequestBody @Valid IssueUpdateDto issueUpdateDto){
        issueService.updateIssue(principal.getName(), issueUpdateDto);
    }

    @PostMapping("/create")
    public void postIssue(Principal principal, @RequestBody @Valid IssueCreateDto issueCreateDto){
        issueService.createIssue(principal.getName(), issueCreateDto);
    }
}
