package cz.upce.fei.sem_pr_backend.controller.v1.public_endpoints;

import cz.upce.fei.sem_pr_backend.dto.issue.IssueGetDto;
import cz.upce.fei.sem_pr_backend.service.IssueService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/public/issues")
public class PublicIssueController {

    private final IssueService issueService;

    public PublicIssueController(IssueService issueService) {
        this.issueService = issueService;
    }

    @GetMapping("/")
    public List<IssueGetDto> getAllIssues(@RequestParam(defaultValue = "0") Integer pageNumber, @RequestParam(defaultValue = "5") Integer pageSize){
        return issueService.getAllPublicIssues(pageNumber, pageSize);
    }

    @GetMapping("/{id}")
    public IssueGetDto getIssueById(@PathVariable Long id){
        return issueService.getPublicIssueById(id);
    }

    @GetMapping("/user/{username}")
    public List<IssueGetDto> getIssuesByAuthorUsername(@RequestParam(defaultValue = "0") Integer pageNumber, @RequestParam(defaultValue = "5") Integer pageSize, @PathVariable String username){
        return issueService.getPublicIssuesByAuthorName(username, pageNumber, pageSize);
    }
}
