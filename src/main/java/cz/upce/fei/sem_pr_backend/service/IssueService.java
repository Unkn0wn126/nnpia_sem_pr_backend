package cz.upce.fei.sem_pr_backend.service;

import cz.upce.fei.sem_pr_backend.domain.Comment;
import cz.upce.fei.sem_pr_backend.domain.Issue;
import cz.upce.fei.sem_pr_backend.dto.comment.CommentCreateDto;
import cz.upce.fei.sem_pr_backend.dto.comment.CommentGetDto;
import cz.upce.fei.sem_pr_backend.dto.comment.CommentUpdateDto;
import cz.upce.fei.sem_pr_backend.dto.issue.IssueCreateDto;
import cz.upce.fei.sem_pr_backend.dto.issue.IssueGetDto;
import cz.upce.fei.sem_pr_backend.dto.issue.IssueUpdateDto;

import java.security.Principal;
import java.util.List;

public interface IssueService {
    Issue createIssue(Principal principal, IssueCreateDto issueCreateDto);

    void updateIssue(Principal principal, Long id, IssueUpdateDto issueUpdateDto);
    void deleteIssue(Principal principal, Long id);

    IssueGetDto getIssueById(Principal principal, Long id);
    List<IssueGetDto> getAllIssues(Principal principal, Integer pageNumber, Integer pageSize);
    List<IssueGetDto> getIssuesByAuthorName(Principal principal, String authorName, Integer pageNumber, Integer pageSize);

    CommentGetDto getCommentById(Long id);
    List<CommentGetDto> getAllComments(Integer pageNumber, Integer pageSize);
    List<CommentGetDto> getCommentsByAuthor(String authorName, Integer pageNumber, Integer pageSize);
    List<CommentGetDto> getIssueComments(Long id, Integer pageNumber, Integer pageSize);
    Comment createCommentToIssue(Principal principal, Long issueId, CommentCreateDto commentCreateDto);
    void updateComment(Principal principal, Long id, CommentUpdateDto commentUpdateDto);
    void deleteComment(Principal principal, Long id);
}
