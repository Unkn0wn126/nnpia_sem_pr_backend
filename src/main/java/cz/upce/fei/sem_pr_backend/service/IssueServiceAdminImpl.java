package cz.upce.fei.sem_pr_backend.service;

import cz.upce.fei.sem_pr_backend.authorization.AuthorizationUtil;
import cz.upce.fei.sem_pr_backend.domain.ApplicationUser;
import cz.upce.fei.sem_pr_backend.domain.Comment;
import cz.upce.fei.sem_pr_backend.domain.Issue;
import cz.upce.fei.sem_pr_backend.domain.enum_type.IssueCompletionState;
import cz.upce.fei.sem_pr_backend.domain.enum_type.IssueVisibility;
import cz.upce.fei.sem_pr_backend.dto.comment.CommentCreateDto;
import cz.upce.fei.sem_pr_backend.dto.comment.CommentGetDto;
import cz.upce.fei.sem_pr_backend.dto.comment.CommentUpdateDto;
import cz.upce.fei.sem_pr_backend.dto.issue.IssueCreateDto;
import cz.upce.fei.sem_pr_backend.dto.issue.IssueGetDto;
import cz.upce.fei.sem_pr_backend.dto.issue.IssueUpdateDto;
import cz.upce.fei.sem_pr_backend.exception.ResourceNotFoundException;
import cz.upce.fei.sem_pr_backend.exception.UnauthorizedException;
import cz.upce.fei.sem_pr_backend.repository.ApplicationUserRepository;
import cz.upce.fei.sem_pr_backend.repository.CommentRepository;
import cz.upce.fei.sem_pr_backend.repository.IssueRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class IssueServiceAdminImpl implements IssueService{

    private final IssueRepository issueRepository;
    private final ApplicationUserRepository userRepository;
    private final CommentRepository commentRepository;
    private final ModelMapper modelMapper;
    private final AuthorizationUtil authorizationUtil;

    public IssueServiceAdminImpl(IssueRepository issueRepository,
                                  ApplicationUserRepository userRepository,
                                  CommentRepository commentRepository,
                                  ModelMapper modelMapper,
                                  AuthorizationUtil authorizationUtil) {
        this.issueRepository = issueRepository;
        this.userRepository = userRepository;
        this.commentRepository = commentRepository;
        this.modelMapper = modelMapper;
        this.authorizationUtil = authorizationUtil;
    }

    @Override
    public Issue createIssue(Principal principal, IssueCreateDto issueCreateDto) {
        Issue issue = modelMapper.map(issueCreateDto, Issue.class);
        ApplicationUser author = userRepository
                .findByUsername(principal.getName())
                .orElseThrow(() -> new UsernameNotFoundException("No such user with name: " + principal.getName()));
        issue.setAuthor(author);
        issue.setCompletionState(IssueCompletionState.TODO);

        return issueRepository.save(issue);
    }

    @Override
    public void updateIssue(Principal principal, Long id, IssueUpdateDto issueUpdateDto) {
        Issue issue = issueRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No such issue with id: " + id));
        issue.setHeader(issueUpdateDto.getHeader());
        issue.setContent(issueUpdateDto.getContent());
        issue.setSeverity(issueUpdateDto.getSeverity());
        issue.setVisibility(issueUpdateDto.getVisibility());
        issue.setCompletionState(issueUpdateDto.getCompletionState());
        issue.setDueDate(issueUpdateDto.getDueDate());
        issue.setCompletionState(issueUpdateDto.getCompletionState());

        issueRepository.save(issue);
    }

    @Override
    public void deleteIssue(Principal principal, Long id) {
        issueRepository.deleteById(id);
    }

    @Override
    public IssueGetDto getIssueById(Principal principal, Long id) {
        Issue issue = issueRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No such issue with id: " + id));

        return modelMapper.map(issue, IssueGetDto.class);
    }

    @Override
    public List<IssueGetDto> getAllIssues(Principal principal, Integer pageNumber, Integer pageSize) {
        return issueRepository.findAll(PageRequest.of(pageNumber, pageSize))
                .stream().map(issue -> modelMapper.map(issue, IssueGetDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<IssueGetDto> getIssuesByAuthorName(Principal principal, String authorName, Integer pageNumber, Integer pageSize) {
        List<IssueVisibility> visibilities = new ArrayList<>();
        visibilities.add(IssueVisibility.PUBLIC);
        visibilities.add(IssueVisibility.INTERNAL);
        visibilities.add(IssueVisibility.PRIVATE);
        return issueRepository.findAllByAuthor(authorName, visibilities, principal.getName(), PageRequest.of(pageNumber, pageSize))
                .stream().map(issue -> modelMapper.map(issue, IssueGetDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public CommentGetDto getCommentById(Long id) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No such comment with id: " + id));
        return modelMapper.map(comment, CommentGetDto.class);
    }

    @Override
    public List<CommentGetDto> getAllComments(Integer pageNumber, Integer pageSize) {
        return commentRepository.findAll(PageRequest.of(pageNumber, pageSize))
                .stream().map(comment -> modelMapper.map(comment, CommentGetDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<CommentGetDto> getCommentsByAuthor(String authorName, Integer pageNumber, Integer pageSize) {
        return commentRepository.findAllByAuthor(authorName, PageRequest.of(pageNumber, pageSize))// TODO filtering based on visibility?
                .stream().map(comment -> modelMapper.map(comment, CommentGetDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<CommentGetDto> getIssueComments(Long id, Integer pageNumber, Integer pageSize) {
        return commentRepository.findAllByIssueId(id, PageRequest.of(pageNumber, pageSize)) // TODO filtering based on visibility?
                .stream().map(comment -> modelMapper.map(comment, CommentGetDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public Comment createCommentToIssue(Principal principal, Long issueId, CommentCreateDto commentCreateDto) {
        Comment comment = modelMapper.map(commentCreateDto, Comment.class);
        ApplicationUser author = userRepository
                .findByUsername(principal.getName())
                .orElseThrow(() -> new UsernameNotFoundException("No such user with name: " + principal.getName()));
        Issue issue = issueRepository.findById(issueId)
                .orElseThrow(() -> new ResourceNotFoundException("No such issue"));
        comment.setAuthor(author);
        comment.setIssue(issue);

        return commentRepository.save(comment);
    }

    @Override
    public void updateComment(Principal principal, Long id, CommentUpdateDto commentUpdateDto) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No such issue with id: " + id));
        comment.setContent(commentUpdateDto.getContent());

        commentRepository.save(comment);
    }

    @Override
    public void deleteComment(Principal principal, Long id) {
        commentRepository.deleteById(id);
    }
}
