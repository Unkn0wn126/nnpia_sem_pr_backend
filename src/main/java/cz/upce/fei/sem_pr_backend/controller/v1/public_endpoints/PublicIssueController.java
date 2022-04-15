package cz.upce.fei.sem_pr_backend.controller.v1.public_endpoints;

import cz.upce.fei.sem_pr_backend.dto.issue.IssueGetDto;
import cz.upce.fei.sem_pr_backend.service.IssueService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/public/issues")
public class PublicIssueController {

    private final IssueService issueService;

    public PublicIssueController(@Qualifier("issueServicePublicImpl") IssueService issueService) {
        this.issueService = issueService;
    }

    @GetMapping("/")
    public Map<String, Object> getAllIssues(@RequestParam(defaultValue = "0") Integer pageNumber, @RequestParam(defaultValue = "5") Integer pageSize){
        return issueService.getAllIssues(null, pageNumber, pageSize);
    }

    @GetMapping("/{id}")
    public IssueGetDto getIssueById(@PathVariable Long id){
        return issueService.getIssueById(null, id);
    }

    @GetMapping("/user/{username}")
    public Map<String, Object> getIssuesByAuthorUsername(@RequestParam(defaultValue = "0") Integer pageNumber, @RequestParam(defaultValue = "5") Integer pageSize, @PathVariable String username){
        return issueService.getIssuesByAuthorName(null, username, pageNumber, pageSize);
    }
}
