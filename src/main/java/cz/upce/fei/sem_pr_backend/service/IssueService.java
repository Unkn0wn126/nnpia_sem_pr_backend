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
    List<IssueGetDto> getAllIssues(Integer pageNumber, Integer PageSize);
    List<IssueGetDto> getAllAccessibleIssues(Principal principal, Integer pageNumber, Integer PageSize);
    List<IssueGetDto> getAllPublicIssues(Integer pageNumber, Integer PageSize);
    List<IssueGetDto> getIssuesByAuthorName(Principal principal, String authorName, Integer pageNumber, Integer PageSize);
    List<IssueGetDto> getPublicIssuesByAuthorName(String authorName, Integer pageNumber, Integer PageSize);

    CommentGetDto getCommentById(Long id);
    List<CommentGetDto> getAllComments(Integer pageNumber, Integer PageSize);
    List<CommentGetDto> getCommentsByAuthor(String authorName, Integer pageNumber, Integer PageSize);
    List<CommentGetDto> getIssueComments(Long id, Integer pageNumber, Integer PageSize);
    Comment createCommentToIssue(String authorName, Long issueId, CommentCreateDto commentCreateDto);
    void updateComment(String author, Long id, CommentUpdateDto commentUpdateDto);
    void deleteComment(String author, Long id);
}
