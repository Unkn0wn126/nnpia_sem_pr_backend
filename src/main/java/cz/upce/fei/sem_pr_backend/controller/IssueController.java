package cz.upce.fei.sem_pr_backend.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/issues")
public class IssueController {

    @GetMapping("/")
    public void getIssues(){

    }

    @GetMapping("/{id}")
    public void getIssueById(@PathVariable Long id){

    }

    @GetMapping("/user/{username}")
    public void getIssueByAuthorUsername(@PathVariable String username){

    }

    @DeleteMapping("/delete/{id}")
    public void deleteIssueById(@PathVariable Long id){

    }

    @PutMapping("/update/{id}")
    public void updateIssueById(@PathVariable Long id){

    }

    @PostMapping("/create")
    public void postIssue(){

    }
}
