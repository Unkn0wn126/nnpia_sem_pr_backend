package cz.upce.fei.sem_pr_backend.controller;

import cz.upce.fei.sem_pr_backend.dto.issue.IssueCreateDto;
import cz.upce.fei.sem_pr_backend.dto.issue.IssueGetDto;
import cz.upce.fei.sem_pr_backend.dto.issue.IssueUpdateDto;
import cz.upce.fei.sem_pr_backend.service.IssueService;
import org.springframework.web.bind.annotation.*;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

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
    public List<IssueGetDto> getAllIssues(){
        return issueService.getAllIssues();
    }

    @GetMapping("/{id}")
    public IssueGetDto getIssueById(@PathVariable Long id){
        return issueService.getIssueById(id);
    }

    @GetMapping("/user/{username}")
    public List<IssueGetDto> getIssuesByAuthorUsername(@PathVariable String username){
        return issueService.getIssueByAuthorName(username);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteIssueById(Principal principal, @PathVariable Long id){
        issueService.deleteIssue(principal.getName(), id);
    }

    @PutMapping("/update/{id}")
    public void updateIssueById(Principal principal, @RequestBody @Valid IssueUpdateDto issueUpdateDto, @PathVariable Long id){
        issueService.updateIssue(principal.getName(), id, issueUpdateDto);
    }

    @PostMapping("/create")
    public void postIssue(Principal principal, @RequestBody @Valid IssueCreateDto issueCreateDto){
        issueService.createIssue(principal.getName(), issueCreateDto);
    }
}
