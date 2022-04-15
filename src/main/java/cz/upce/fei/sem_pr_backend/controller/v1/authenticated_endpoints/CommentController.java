package cz.upce.fei.sem_pr_backend.controller.v1.authenticated_endpoints;

import cz.upce.fei.sem_pr_backend.domain.Comment;
import cz.upce.fei.sem_pr_backend.dto.comment.CommentCreateDto;
import cz.upce.fei.sem_pr_backend.dto.comment.CommentGetDto;
import cz.upce.fei.sem_pr_backend.dto.comment.CommentUpdateDto;
import cz.upce.fei.sem_pr_backend.service.IssueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/v1/comments")
public class CommentController {

    private final IssueService issueService;

    public CommentController(@Qualifier("issueServiceImpl") IssueService issueService) {
        this.issueService = issueService;
    }

    @GetMapping("/")
    public Map<String, Object> getComments(@RequestParam(defaultValue = "0") Integer pageNumber, @RequestParam(defaultValue = "5") Integer pageSize){
        return issueService.getAllComments(pageNumber, pageSize);
    }

    @GetMapping("/{id}")
    public CommentGetDto getCommentById(@PathVariable Long id){
        return issueService.getCommentById(id);
    }

    @GetMapping("/issue/{id}")
    public Map<String, Object> getCommentsByIssueId(@RequestParam(defaultValue = "0") Integer pageNumber, @RequestParam(defaultValue = "5") Integer pageSize, @PathVariable Long id){
        return issueService.getIssueComments(id, pageNumber, pageSize);
    }

    @GetMapping("/user/{username}")
    public Map<String, Object> getCommentsByAuthorUsername(@RequestParam(defaultValue = "0") Integer pageNumber, @RequestParam(defaultValue = "5") Integer pageSize, @PathVariable String username){
        return issueService.getCommentsByAuthor(username, pageNumber, pageSize);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteCommentById(Principal principal, @PathVariable Long id){
        issueService.deleteComment(principal, id);
    }

    @PutMapping("/update/{id}")
    public void updateCommentById(Principal principal, @PathVariable Long id, @Valid @RequestBody CommentUpdateDto commentUpdateDto){
        issueService.updateComment(principal, id, commentUpdateDto);
    }

    @PostMapping("/create/{id}")
    public void postCommentToIssue(Principal principal, @PathVariable Long id, @Valid @RequestBody CommentCreateDto commentCreateDto){
        issueService.createCommentToIssue(principal, id, commentCreateDto);
    }
}
