package cz.upce.fei.sem_pr_backend.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/issues")
public class IssueController {

    @GetMapping("/")
    public void getIssues(){
        System.out.println("issues");
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
    public void deleteIssueById(@PathVariable Long id){
        System.out.println("delete issue with id " + id);
    }

    @PutMapping("/update/{id}")
    public void updateIssueById(@PathVariable Long id){
        System.out.println("update issue with id " + id);
    }

    @PostMapping("/create")
    public void postIssue(){
        System.out.println("create issue");
    }
}
