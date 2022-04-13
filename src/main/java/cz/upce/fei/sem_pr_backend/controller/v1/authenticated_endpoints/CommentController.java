package cz.upce.fei.sem_pr_backend.controller.v1.authenticated_endpoints;

import cz.upce.fei.sem_pr_backend.domain.Comment;
import cz.upce.fei.sem_pr_backend.dto.comment.CommentCreateDto;
import cz.upce.fei.sem_pr_backend.dto.comment.CommentGetDto;
import cz.upce.fei.sem_pr_backend.dto.comment.CommentUpdateDto;
import cz.upce.fei.sem_pr_backend.service.IssueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("api/v1/comments")
public class CommentController {

    private final IssueService issueService;

    public CommentController(IssueService issueService) {
        this.issueService = issueService;
    }

    @GetMapping("/")
    public List<CommentGetDto> getComments(@RequestParam(defaultValue = "0") Integer pageNumber, @RequestParam(defaultValue = "5") Integer pageSize){
        return issueService.getAllComments(pageNumber, pageSize);
    }

    @GetMapping("/{id}")
    public CommentGetDto getCommentById(@PathVariable Long id){
        return issueService.getCommentById(id);
    }

    @GetMapping("/issue/{id}")
    public List<CommentGetDto> getCommentsByIssueId(@RequestParam(defaultValue = "0") Integer pageNumber, @RequestParam(defaultValue = "5") Integer pageSize, @PathVariable Long id){
        return issueService.getIssueComments(id, pageNumber, pageSize);
    }

    @GetMapping("/user/{username}")
    public List<CommentGetDto> getCommentsByAuthorUsername(@RequestParam(defaultValue = "0") Integer pageNumber, @RequestParam(defaultValue = "5") Integer pageSize, @PathVariable String username){
        return issueService.getCommentsByAuthor(username, pageNumber, pageSize);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteCommentById(Principal principal, @PathVariable Long id){
        issueService.deleteComment(principal.getName(), id);
    }

    @PutMapping("/update/{id}")
    public void updateCommentById(Principal principal, @PathVariable Long id, @RequestBody CommentUpdateDto commentUpdateDto){
        issueService.updateComment(principal.getName(), id, commentUpdateDto);
    }

    @PostMapping("/create/{id}")
    public void postCommentToIssue(Principal principal, @PathVariable Long id, @RequestBody CommentCreateDto commentCreateDto){
        issueService.createCommentToIssue(principal.getName(), id, commentCreateDto);
    }
}