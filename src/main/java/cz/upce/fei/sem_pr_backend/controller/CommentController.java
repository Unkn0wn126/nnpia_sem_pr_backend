package cz.upce.fei.sem_pr_backend.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/comments")
public class CommentController {

    @GetMapping("/")
    public void getComments(){

    }

    @GetMapping("/{id}")
    public void getCommentById(@PathVariable Long id){

    }

    @GetMapping("/issue/{id}")
    public void getCommentsByIssueId(@PathVariable Long id){

    }

    @GetMapping("/user/{username}")
    public void getCommentsByAuthorUsername(@PathVariable String username){

    }

    @DeleteMapping("/delete/{id}")
    public void deleteCommentById(@PathVariable Long id){

    }

    @PutMapping("/update/{id}")
    public void updateCommentById(@PathVariable Long id){

    }

    @PostMapping("/create/{id}")
    public void postCommentToIssue(@PathVariable String id){

    }
}
