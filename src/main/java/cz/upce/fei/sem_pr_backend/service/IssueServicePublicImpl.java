package cz.upce.fei.sem_pr_backend.service;

import cz.upce.fei.sem_pr_backend.authorization.AuthorizationUtil;
import cz.upce.fei.sem_pr_backend.domain.Comment;
import cz.upce.fei.sem_pr_backend.domain.Issue;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class IssueServicePublicImpl implements IssueService{

    private final IssueRepository issueRepository;
    private final ApplicationUserRepository userRepository;
    private final CommentRepository commentRepository;
    private final ModelMapper modelMapper;
    private final AuthorizationUtil authorizationUtil;

    public IssueServicePublicImpl(IssueRepository issueRepository,
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
        throw new UnauthorizedException("Unauthorized");
    }

    @Override
    public void updateIssue(Principal principal, Long id, IssueUpdateDto issueUpdateDto) {
        throw new UnauthorizedException("Unauthorized");
    }

    @Override
    public void deleteIssue(Principal principal, Long id) {
        throw new UnauthorizedException("Unauthorized");
    }

    @Override
    public IssueGetDto getIssueById(Principal principal, Long id) {
        Issue issue = issueRepository
                .findByIdAndVisibility(id, Collections.singletonList(IssueVisibility.PUBLIC), null)
                .orElseThrow(() -> new ResourceNotFoundException("No such issue with id: " + id));

        return modelMapper.map(issue, IssueGetDto.class);
    }

    @Override
    public Map<String, Object> getAllIssues(Principal principal, Integer pageNumber, Integer pageSize) {
        Page<Issue> allIssues = issueRepository.findAllByVisibility(new ArrayList<>(Collections.singletonList(IssueVisibility.PUBLIC)), null, PageRequest.of(pageNumber, pageSize));
        List<IssueGetDto> issueGetDtos = allIssues
                .stream().map(issue -> modelMapper.map(issue, IssueGetDto.class))
                .collect(Collectors.toList());

        Map<String, Object> pageObject = new HashMap<>();
        pageObject.put("issues", issueGetDtos);
        pageObject.put("currentPage", allIssues.getNumber());
        pageObject.put("totalItems", allIssues.getTotalElements());
        pageObject.put("totalPages", allIssues.getTotalPages());

        return pageObject;
    }

    @Override
    public Map<String, Object> getIssuesByAuthorName(Principal principal, String authorName, Integer pageNumber, Integer pageSize) {
        List<IssueVisibility> visibilities = new ArrayList<>();
        visibilities.add(IssueVisibility.PUBLIC);

        Page<Issue> allIssues = issueRepository.findAllByAuthor(authorName, visibilities, null, PageRequest.of(pageNumber, pageSize));
        List<IssueGetDto> issueGetDtos = allIssues
                .stream().map(issue -> modelMapper.map(issue, IssueGetDto.class))
                .collect(Collectors.toList());

        Map<String, Object> pageObject = new HashMap<>();
        pageObject.put("issues", issueGetDtos);
        pageObject.put("currentPage", allIssues.getNumber());
        pageObject.put("totalItems", allIssues.getTotalElements());
        pageObject.put("totalPages", allIssues.getTotalPages());

        return pageObject;
    }

    @Override
    public CommentGetDto getCommentById(Long id) {
        throw new UnauthorizedException("Unauthorized");
    }

    @Override
    public Map<String, Object> getAllComments(Integer pageNumber, Integer PageSize) {
        throw new UnauthorizedException("Unauthorized");
    }

    @Override
    public Map<String, Object> getCommentsByAuthor(String authorName, Integer pageNumber, Integer PageSize) {
        throw new UnauthorizedException("Unauthorized");
    }

    @Override
    public Map<String, Object> getIssueComments(Long id, Integer pageNumber, Integer PageSize) {
        throw new UnauthorizedException("Unauthorized");
    }

    @Override
    public Comment createCommentToIssue(Principal principal, Long issueId, CommentCreateDto commentCreateDto) {
        throw new UnauthorizedException("Unauthorized");
    }

    @Override
    public void updateComment(Principal principal, Long id, CommentUpdateDto commentUpdateDto) {
        throw new UnauthorizedException("Unauthorized");
    }

    @Override
    public void deleteComment(Principal principal, Long id) {
        throw new UnauthorizedException("Unauthorized");
    }
}
