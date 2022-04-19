package cz.upce.fei.sem_pr_backend.service;

import cz.upce.fei.sem_pr_backend.authorization.AuthorizationUtil;
import cz.upce.fei.sem_pr_backend.domain.ApplicationUser;
import cz.upce.fei.sem_pr_backend.domain.Comment;
import cz.upce.fei.sem_pr_backend.domain.Issue;
import cz.upce.fei.sem_pr_backend.domain.enum_type.IssueCompletionState;
import cz.upce.fei.sem_pr_backend.domain.enum_type.IssueSeverity;
import cz.upce.fei.sem_pr_backend.domain.enum_type.IssueVisibility;
import cz.upce.fei.sem_pr_backend.dto.comment.CommentCreateDto;
import cz.upce.fei.sem_pr_backend.dto.comment.CommentGetDto;
import cz.upce.fei.sem_pr_backend.dto.comment.CommentPageGetDto;
import cz.upce.fei.sem_pr_backend.dto.comment.CommentUpdateDto;
import cz.upce.fei.sem_pr_backend.dto.issue.IssueCreateDto;
import cz.upce.fei.sem_pr_backend.dto.issue.IssueGetDto;
import cz.upce.fei.sem_pr_backend.dto.issue.IssuePageGetDto;
import cz.upce.fei.sem_pr_backend.dto.issue.IssueUpdateDto;
import cz.upce.fei.sem_pr_backend.exception.ResourceNotFoundException;
import cz.upce.fei.sem_pr_backend.exception.UnauthorizedException;
import cz.upce.fei.sem_pr_backend.repository.ApplicationUserRepository;
import cz.upce.fei.sem_pr_backend.repository.CommentRepository;
import cz.upce.fei.sem_pr_backend.repository.IssueRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class IssueServiceImpl implements IssueService{

    private final IssueRepository issueRepository;
    private final ApplicationUserRepository userRepository;
    private final CommentRepository commentRepository;
    private final ModelMapper modelMapper;
    private final AuthorizationService authorizationService;

    public IssueServiceImpl(IssueRepository issueRepository,
                            ApplicationUserRepository userRepository,
                            CommentRepository commentRepository,
                            ModelMapper modelMapper,
                            AuthorizationService authorizationService) {
        this.issueRepository = issueRepository;
        this.userRepository = userRepository;
        this.commentRepository = commentRepository;
        this.modelMapper = modelMapper;
        this.authorizationService = authorizationService;
    }

    @Override
    public Issue createIssue(Principal principal, IssueCreateDto issueCreateDto) {
        if(!authorizationService.isAuthenticated(principal)){
            throw new UnauthorizedException("Unauthorized");
        }

        Issue issue = modelMapper.map(issueCreateDto, Issue.class);
        ApplicationUser author = userRepository
                .findByUsername(authorizationService.getPrincipalName(principal))
                .orElseThrow(() -> new UsernameNotFoundException("No such user with name: " + authorizationService.getPrincipalName(principal)));
        issue.setAuthor(author);
        issue.setCompletionState(IssueCompletionState.TODO);

        return issueRepository.save(issue);
    }

    @Override
    public void updateIssue(Principal principal, Long id, IssueUpdateDto issueUpdateDto) {
        Issue issue = issueRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No such issue with id: " + id));
        if (authorizationService.canAlterResource(principal, issue.getAuthor().getId())){
            issue.setHeader(issueUpdateDto.getHeader());
            issue.setContent(issueUpdateDto.getContent());
            issue.setSeverity(IssueSeverity.fromString(issueUpdateDto.getSeverity()));
            issue.setVisibility(IssueVisibility.fromString(issueUpdateDto.getVisibility()));
            issue.setCompletionState(IssueCompletionState.fromString(issueUpdateDto.getCompletionState()));
            issue.setDueDate(issueUpdateDto.getDueDate());
            issue.setCompletionState(IssueCompletionState.fromString(issueUpdateDto.getCompletionState()));

            issueRepository.save(issue);
        }else{
            throw new UnauthorizedException("Unauthorized!");
        }
    }

    @Override
    public void deleteIssue(Principal principal, Long id) {
        Issue issue = issueRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No such issue with id: " + id));
        if (authorizationService.canAlterResource(principal, issue.getAuthor().getId())){
            issueRepository.deleteById(id);
        }else{
            throw new UnauthorizedException("Unauthorized!");
        }
    }

    @Override
    public IssueGetDto getIssueById(Principal principal, Long id) {
        Issue issue = issueRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No such issue with id: " + id));

        if(issue.getVisibility() != IssueVisibility.PUBLIC && !authorizationService.isAuthenticated(principal)){
            throw new UnauthorizedException("Unauthorized");
        }else if(issue.getVisibility() == IssueVisibility.PRIVATE && !authorizationService.canAlterResource(principal, issue.getAuthor().getId())){
            throw new UnauthorizedException("Unauthorized");
        }

        return modelMapper.map(issue, IssueGetDto.class);
    }

    private List<IssueVisibility> getAuthenticadedVisibilities(){
        List<IssueVisibility> visibilities = new ArrayList<>();
        visibilities.add(IssueVisibility.PUBLIC);
        visibilities.add(IssueVisibility.INTERNAL);

        return visibilities;
    }

    @Override
    public IssuePageGetDto getAllIssues(Principal principal, Integer pageNumber, Integer pageSize, String direction, String... properties) {
        List<IssueVisibility> visibilities = new ArrayList<>();
        visibilities.add(IssueVisibility.PUBLIC);
        if(authorizationService.isAuthenticated(principal))
            visibilities.add(IssueVisibility.INTERNAL);
        if(authorizationService.isAdmin(principal))
            visibilities.add(IssueVisibility.PRIVATE);

        Page<Issue> allIssues = issueRepository.findAllByVisibility(
                visibilities,
                authorizationService.getPrincipalName(principal),
                PageRequest.of(pageNumber, pageSize, Sort.Direction.valueOf(direction), properties));
        List<IssueGetDto> issueGetDtos = allIssues
                .stream().map(issue -> modelMapper.map(issue, IssueGetDto.class))
                .collect(Collectors.toList());

        IssuePageGetDto pageObject = new IssuePageGetDto();
        pageObject.setIssues(issueGetDtos);
        pageObject.setCurrentPage(allIssues.getNumber());
        pageObject.setTotalItems(allIssues.getTotalElements());
        pageObject.setTotalPages(allIssues.getTotalPages());

        return pageObject;
    }

    @Override
    public IssuePageGetDto getIssuesByAuthorName(Principal principal, String authorName, Integer pageNumber, Integer pageSize, String direction, String... properties) {
        List<IssueVisibility> visibilities = new ArrayList<>();
        visibilities.add(IssueVisibility.PUBLIC);
        if(authorizationService.isAuthenticated(principal))
            visibilities.add(IssueVisibility.INTERNAL);
        if(authorizationService.isAdmin(principal))
            visibilities.add(IssueVisibility.PRIVATE);

        Page<Issue> allIssues = issueRepository.findAllByAuthor(authorName,
                visibilities, authorizationService.getPrincipalName(principal),
                PageRequest.of(pageNumber, pageSize, Sort.Direction.valueOf(direction), properties));
        List<IssueGetDto> issueGetDtos = allIssues
                .stream().map(issue -> modelMapper.map(issue, IssueGetDto.class))
                .collect(Collectors.toList());

        IssuePageGetDto pageObject = new IssuePageGetDto();
        pageObject.setIssues(issueGetDtos);
        pageObject.setCurrentPage(allIssues.getNumber());
        pageObject.setTotalItems(allIssues.getTotalElements());
        pageObject.setTotalPages(allIssues.getTotalPages());

        return pageObject;
    }

    @Override
    public CommentGetDto getCommentById(Principal principal, Long id) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No such comment with id: " + id));
        return modelMapper.map(comment, CommentGetDto.class);
    }

    @Override
    public CommentPageGetDto getAllComments(Principal principal, Integer pageNumber, Integer pageSize, String direction, String... properties) {
        Page<Comment> allComments = commentRepository.findAll(PageRequest.of(pageNumber, pageSize, Sort.Direction.valueOf(direction), properties)); // TODO sorting based on visibility
        List<CommentGetDto> commentGetDtos = allComments
                .stream().map(comment -> modelMapper.map(comment, CommentGetDto.class))
                .collect(Collectors.toList());

        CommentPageGetDto pageObject = new CommentPageGetDto();
        pageObject.setComments(commentGetDtos);
        pageObject.setCurrentPage(allComments.getNumber());
        pageObject.setTotalItems(allComments.getTotalElements());
        pageObject.setTotalPages(allComments.getTotalPages());

        return pageObject;
    }

    @Override
    public CommentPageGetDto getCommentsByAuthor(Principal principal, String authorName, Integer pageNumber, Integer pageSize, String direction, String... properties) {
        Page<Comment> allComments = commentRepository.findAllByAuthor(authorName, PageRequest.of(pageNumber, pageSize, Sort.Direction.valueOf(direction), properties));// TODO filtering based on visibility?
        List<CommentGetDto> commentGetDtos = allComments
                .stream().map(comment -> modelMapper.map(comment, CommentGetDto.class))
                .collect(Collectors.toList());

        CommentPageGetDto pageObject = new CommentPageGetDto();
        pageObject.setComments(commentGetDtos);
        pageObject.setCurrentPage(allComments.getNumber());
        pageObject.setTotalItems(allComments.getTotalElements());
        pageObject.setTotalPages(allComments.getTotalPages());

        return pageObject;
    }

    @Override
    public CommentPageGetDto getIssueComments(Principal principal, Long id, Integer pageNumber, Integer pageSize, String direction, String... properties) {
        Issue issue = issueRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No such issue with id " + id));
        if (issue.getVisibility() != IssueVisibility.PUBLIC && !authorizationService.isAuthenticated(principal)){
            throw new UnauthorizedException("Unauthorized!");
        } else if(issue.getVisibility() == IssueVisibility.PRIVATE && !authorizationService.canAlterResource(principal, issue.getAuthor().getId())){
            throw new UnauthorizedException("Unauthorized!");
        }
        Page<Comment> allComments = commentRepository.findAllByIssueId(id, PageRequest.of(pageNumber, pageSize, Sort.Direction.valueOf(direction), properties));
        List<CommentGetDto> commentGetDtos = allComments
                .stream().map(comment -> modelMapper.map(comment, CommentGetDto.class))
                .collect(Collectors.toList());

        CommentPageGetDto pageObject = new CommentPageGetDto();
        pageObject.setComments(commentGetDtos);
        pageObject.setCurrentPage(allComments.getNumber());
        pageObject.setTotalItems(allComments.getTotalElements());
        pageObject.setTotalPages(allComments.getTotalPages());

        return pageObject;
    }

    @Override
    public Comment createCommentToIssue(Principal principal, Long issueId, CommentCreateDto commentCreateDto) {
        Issue issue = issueRepository.findById(issueId).orElseThrow(() -> new ResourceNotFoundException("No such issue with id " + issueId));
        if(!authorizationService.isAuthenticated(principal)){
            throw new UnauthorizedException("Not authorized!");
        }
        else if(issue.getVisibility() == IssueVisibility.PRIVATE && !authorizationService.canAlterResource(principal, issue.getAuthor().getId())){
            throw new UnauthorizedException("Unauthorized!");
        }
        Comment comment = modelMapper.map(commentCreateDto, Comment.class);
        ApplicationUser author = userRepository
                .findByUsername(authorizationService.getPrincipalName(principal))
                .orElseThrow(() -> new UsernameNotFoundException("No such user with name: " + authorizationService.getPrincipalName(principal)));
        comment.setAuthor(author);
        comment.setIssue(issue);

        return commentRepository.save(comment);
    }

    @Override
    public void updateComment(Principal principal, Long id, CommentUpdateDto commentUpdateDto) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No such issue with id: " + id));
        if (authorizationService.canAlterResource(principal, comment.getAuthor().getId())){
            comment.setContent(commentUpdateDto.getContent());

            commentRepository.save(comment);
        }else{
            throw new UnauthorizedException("Unauthorized!");
        }
    }

    @Override
    public void deleteComment(Principal principal, Long id) {
        Comment comment = commentRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No such comment with id: " + id));
        if (authorizationService.canAlterResource(principal, comment.getAuthor().getId())){
            commentRepository.deleteById(id);
        }else{
            throw new UnauthorizedException("Unauthorized!");
        }
    }
}
