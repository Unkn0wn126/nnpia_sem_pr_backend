package cz.upce.fei.sem_pr_backend.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/comments")
public class CommentController {

    @GetMapping("/")
    public void getComments(){
        System.out.println("comments");
    }

    @GetMapping("/{id}")
    public void getCommentById(@PathVariable Long id){
        System.out.println("comment with id " + id);
    }

    @GetMapping("/issue/{id}")
    public void getCommentsByIssueId(@PathVariable Long id){
        System.out.println("comment with issue id " + id);
    }

    @GetMapping("/user/{username}")
    public void getCommentsByAuthorUsername(@PathVariable String username){
        System.out.println("comment with author " + username);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteCommentById(@PathVariable Long id){
        System.out.println("delete comment with id " + id);
    }

    @PutMapping("/update/{id}")
    public void updateCommentById(@PathVariable Long id){
        System.out.println("update comment with id " + id);

    }

    @PostMapping("/create/{id}")
    public void postCommentToIssue(@PathVariable String id){
        System.out.println("create comment to issue with id " + id);
    }
}
