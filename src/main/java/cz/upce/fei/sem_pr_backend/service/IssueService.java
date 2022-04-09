package cz.upce.fei.sem_pr_backend.service;

import cz.upce.fei.sem_pr_backend.domain.Issue;
import cz.upce.fei.sem_pr_backend.dto.comment.CommentGetDto;
import cz.upce.fei.sem_pr_backend.dto.issue.IssueCreateDto;
import cz.upce.fei.sem_pr_backend.dto.issue.IssueGetDto;
import cz.upce.fei.sem_pr_backend.dto.issue.IssueUpdateDto;

import java.util.List;

public interface IssueService {
    Issue createIssue(String authorName, IssueCreateDto issueCreateDto);

    void updateIssue(String author, IssueUpdateDto issueUpdateDto);
    void deleteIssue(String author, Long id);

    IssueGetDto getIssueById(Long id);
    List<IssueGetDto> getAllIssues();
    List<IssueGetDto> getIssueByAuthorName(String authorName);
    List<CommentGetDto> getIssueComments(Long id);
}
