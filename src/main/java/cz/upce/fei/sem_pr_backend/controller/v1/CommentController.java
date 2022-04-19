package cz.upce.fei.sem_pr_backend.controller.v1;

import cz.upce.fei.sem_pr_backend.domain.Comment;
import cz.upce.fei.sem_pr_backend.dto.comment.CommentCreateDto;
import cz.upce.fei.sem_pr_backend.dto.comment.CommentGetDto;
import cz.upce.fei.sem_pr_backend.dto.comment.CommentPageGetDto;
import cz.upce.fei.sem_pr_backend.dto.comment.CommentUpdateDto;
import cz.upce.fei.sem_pr_backend.service.IssueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;
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
    public CommentPageGetDto getComments(Principal principal,
                                         @RequestParam(defaultValue = "0") Integer pageNumber,
                                         @RequestParam(defaultValue = "5") Integer pageSize,
                                         @Valid @Pattern(regexp = "ASC|DESC") @RequestParam(defaultValue = "ASC") String direction,
                                         @RequestParam(defaultValue = "created") String... orderBy){
        return issueService.getAllComments(principal, pageNumber, pageSize, direction, orderBy);
    }

    @GetMapping("/{id}")
    public CommentGetDto getCommentById(Principal principal, @PathVariable Long id){
        return issueService.getCommentById(principal, id);
    }

    @GetMapping("/issue/{id}")
    public CommentPageGetDto getCommentsByIssueId(Principal principal,
                                                    @PathVariable Long id,
                                                    @RequestParam(defaultValue = "0") Integer pageNumber,
                                                    @RequestParam(defaultValue = "5") Integer pageSize,
                                                    @Valid @Pattern(regexp = "ASC|DESC") @RequestParam(defaultValue = "ASC") String direction,
                                                    @RequestParam(defaultValue = "created") String... orderBy){
        return issueService.getIssueComments(principal, id, pageNumber, pageSize, direction, orderBy);
    }

    @GetMapping("/user/{username}")
    public CommentPageGetDto getCommentsByAuthorUsername(Principal principal,
                                                           @PathVariable String username,
                                                           @RequestParam(defaultValue = "0") Integer pageNumber,
                                                           @RequestParam(defaultValue = "5") Integer pageSize,
                                                           @Valid @Pattern(regexp = "ASC|DESC") @RequestParam(defaultValue = "DESC") String direction,
                                                           @RequestParam(defaultValue = "created") String... orderBy){
        return issueService.getCommentsByAuthor(principal, username, pageNumber, pageSize, direction, orderBy);
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
