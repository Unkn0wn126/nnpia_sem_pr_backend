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
    Issue createIssue(String authorName, IssueCreateDto issueCreateDto);

    void updateIssue(String author, Long id, IssueUpdateDto issueUpdateDto);
    void deleteIssue(String author, Long id);

    IssueGetDto getIssueById(Principal principal, Long id);
    IssueGetDto getPublicIssueById(Long id);
    List<IssueGetDto> getAllIssues();
    List<IssueGetDto> getAllAccessibleIssues(Principal principal);
    List<IssueGetDto> getAllPublicIssues();
    List<IssueGetDto> getIssuesByAuthorName(Principal principal, String authorName);
    List<IssueGetDto> getPublicIssuesByAuthorName(String authorName);

    CommentGetDto getCommentById(Long id);
    List<CommentGetDto> getAllComments();
    List<CommentGetDto> getCommentsByAuthor(String authorName);
    List<CommentGetDto> getIssueComments(Long id);
    Comment createCommentToIssue(String authorName, Long issueId, CommentCreateDto commentCreateDto);
    void updateComment(String author, Long id, CommentUpdateDto commentUpdateDto);
    void deleteComment(String author, Long id);
}
