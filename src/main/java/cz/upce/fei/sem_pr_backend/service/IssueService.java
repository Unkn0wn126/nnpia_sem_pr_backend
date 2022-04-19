package cz.upce.fei.sem_pr_backend.service;

import cz.upce.fei.sem_pr_backend.domain.Comment;
import cz.upce.fei.sem_pr_backend.domain.Issue;
import cz.upce.fei.sem_pr_backend.dto.comment.CommentCreateDto;
import cz.upce.fei.sem_pr_backend.dto.comment.CommentGetDto;
import cz.upce.fei.sem_pr_backend.dto.comment.CommentPageGetDto;
import cz.upce.fei.sem_pr_backend.dto.comment.CommentUpdateDto;
import cz.upce.fei.sem_pr_backend.dto.issue.IssueCreateDto;
import cz.upce.fei.sem_pr_backend.dto.issue.IssueGetDto;
import cz.upce.fei.sem_pr_backend.dto.issue.IssuePageGetDto;
import cz.upce.fei.sem_pr_backend.dto.issue.IssueUpdateDto;

import java.security.Principal;
import java.util.List;
import java.util.Map;

public interface IssueService {
    Issue createIssue(Principal principal, IssueCreateDto issueCreateDto);

    void updateIssue(Principal principal, Long id, IssueUpdateDto issueUpdateDto);
    void deleteIssue(Principal principal, Long id);

    IssueGetDto getIssueById(Principal principal, Long id);
    IssuePageGetDto getAllIssues(Principal principal, Integer pageNumber, Integer pageSize, String direction, String... properties);
    IssuePageGetDto getIssuesByAuthorName(Principal principal, String authorName, Integer pageNumber, Integer pageSize, String direction, String... properties);

    CommentGetDto getCommentById(Principal principal, Long id);
    CommentPageGetDto getAllComments(Principal principal, Integer pageNumber, Integer pageSize, String direction, String... properties);
    CommentPageGetDto getCommentsByAuthor(Principal principal, String authorName, Integer pageNumber, Integer pageSize, String direction, String... properties);
    CommentPageGetDto getIssueComments(Principal principal, Long id, Integer pageNumber, Integer pageSize, String direction, String... properties);
    Comment createCommentToIssue(Principal principal, Long issueId, CommentCreateDto commentCreateDto);
    void updateComment(Principal principal, Long id, CommentUpdateDto commentUpdateDto);
    void deleteComment(Principal principal, Long id);
}
