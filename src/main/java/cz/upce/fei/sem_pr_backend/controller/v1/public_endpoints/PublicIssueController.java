package cz.upce.fei.sem_pr_backend.controller.v1.public_endpoints;

import cz.upce.fei.sem_pr_backend.dto.issue.IssueGetDto;
import cz.upce.fei.sem_pr_backend.service.IssueService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/public/issues")
public class PublicIssueController {

    private final IssueService issueService;

    public PublicIssueController(IssueService issueService) {
        this.issueService = issueService;
    }

    @GetMapping("/")
    public List<IssueGetDto> getAllIssues(){
        return issueService.getAllPublicIssues();
    }

    @GetMapping("/{id}")
    public IssueGetDto getIssueById(@PathVariable Long id){
        return issueService.getPublicIssueById(id);
    }

    @GetMapping("/user/{username}")
    public List<IssueGetDto> getIssuesByAuthorUsername(@PathVariable String username){
        return issueService.getPublicIssuesByAuthorName(username);
    }
}
