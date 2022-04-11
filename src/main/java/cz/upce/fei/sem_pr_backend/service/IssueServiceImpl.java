package cz.upce.fei.sem_pr_backend.service;

import cz.upce.fei.sem_pr_backend.authorization.AuthorizationUtil;
import cz.upce.fei.sem_pr_backend.domain.ApplicationUser;
import cz.upce.fei.sem_pr_backend.domain.Comment;
import cz.upce.fei.sem_pr_backend.domain.Issue;
import cz.upce.fei.sem_pr_backend.domain.enum_type.IssueCompletionState;
import cz.upce.fei.sem_pr_backend.dto.comment.CommentCreateDto;
import cz.upce.fei.sem_pr_backend.dto.comment.CommentGetDto;
import cz.upce.fei.sem_pr_backend.dto.comment.CommentUpdateDto;
import cz.upce.fei.sem_pr_backend.dto.issue.IssueCreateDto;
import cz.upce.fei.sem_pr_backend.dto.issue.IssueGetDto;
import cz.upce.fei.sem_pr_backend.dto.issue.IssueUpdateDto;
import cz.upce.fei.sem_pr_backend.repository.ApplicationUserRepository;
import cz.upce.fei.sem_pr_backend.repository.CommentRepository;
import cz.upce.fei.sem_pr_backend.repository.IssueRepository;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class IssueServiceImpl implements IssueService{

    private final IssueRepository issueRepository;
    private final ApplicationUserRepository userRepository;
    private final CommentRepository commentRepository;
    private final ModelMapper modelMapper;
    private final AuthorizationUtil authorizationUtil;

    public IssueServiceImpl(IssueRepository issueRepository,
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
    public Issue createIssue(String authorName, IssueCreateDto issueCreateDto) {
        Issue issue = modelMapper.map(issueCreateDto, Issue.class);
        ApplicationUser author = userRepository.findByUsername(authorName).orElseThrow(() -> new UsernameNotFoundException("No such user"));
        issue.setAuthor(author);
        issue.setCompletionState(IssueCompletionState.TODO);

        return issueRepository.save(issue);
    }

    @Override
    public void updateIssue(String author, Long id, IssueUpdateDto issueUpdateDto) {
        Issue issue = issueRepository.findById(id).orElseThrow(() -> new RuntimeException("No such issue")); // TODO better exception
        if (authorizationUtil.isAdmin(author) || issue.getAuthor().getUsername().equals(author)){
            issue.setHeader(issueUpdateDto.getHeader());
            issue.setContent(issueUpdateDto.getContent());
            issue.setSeverity(issueUpdateDto.getSeverity());
            issue.setVisibility(issueUpdateDto.getVisibility());
            issue.setCompletionState(issueUpdateDto.getCompletionState());
            issue.setDueDate(issueUpdateDto.getDueDate());
            issue.setCompletionState(issueUpdateDto.getCompletionState());

            issueRepository.save(issue);
        }else{
            throw new RuntimeException("Unauthorized!"); // TODO better exception
        }
    }

    @Override
    public void deleteIssue(String author, Long id) {
        // TODO filtering based on visibility
        Issue issue = issueRepository.findById(id).orElseThrow(() -> new RuntimeException("No such issue")); // TODO better exception
        if (authorizationUtil.isAdmin(author) || issue.getAuthor().getUsername().equals(author)){
            issueRepository.deleteById(id);
        }else{
            throw new RuntimeException("Unauthorized!"); // TODO better exception
        }
    }

    @Override
    public IssueGetDto getIssueById(Long id) {
        // TODO filtering based on visibility
        Issue issue = issueRepository.findById(id).orElseThrow(() -> new RuntimeException("No such issue"));
        return modelMapper.map(issue, IssueGetDto.class);
    }

    @Override
    public List<IssueGetDto> getAllIssues() {
        return issueRepository.findAll() // TODO filtering based on visibility
                .stream().map(issue -> modelMapper.map(issue, IssueGetDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<IssueGetDto> getIssueByAuthorName(String authorName) {
        return issueRepository.findAllByAuthor(authorName) // TODO filtering based on visibility
                .stream().map(issue -> modelMapper.map(issue, IssueGetDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public CommentGetDto getCommentById(Long id) {
        Comment comment = commentRepository.findById(id).orElseThrow(() -> new RuntimeException("No such comment"));// TODO better exception
        return modelMapper.map(comment, CommentGetDto.class);
    }

    @Override
    public List<CommentGetDto> getAllComments() {
        return commentRepository.findAll()
                .stream().map(comment -> modelMapper.map(comment, CommentGetDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<CommentGetDto> getCommentsByAuthor(String authorName) {
        return commentRepository.findAllByAuthor(authorName)// TODO filtering based on visibility
                .stream().map(comment -> modelMapper.map(comment, CommentGetDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<CommentGetDto> getIssueComments(Long id) {
        return commentRepository.findAllByIssueId(id) // TODO filtering based on visibility
                .stream().map(comment -> modelMapper.map(comment, CommentGetDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public Comment createCommentToIssue(String authorName, Long issueId, CommentCreateDto commentCreateDto) {
        // TODO filtering based on visibility
        Comment comment = modelMapper.map(commentCreateDto, Comment.class);
        ApplicationUser author = userRepository.findByUsername(authorName).orElseThrow(() -> new UsernameNotFoundException("No such user"));
        Issue issue = issueRepository.findById(issueId).orElseThrow(() -> new UsernameNotFoundException("No such issue"));
        comment.setAuthor(author);
        comment.setIssue(issue);

        return commentRepository.save(comment);
    }

    @Override
    public void updateComment(String author, Long id, CommentUpdateDto commentUpdateDto) {
        Comment comment = commentRepository.findById(id).orElseThrow(() -> new RuntimeException("No such issue")); // TODO better exception
        if (authorizationUtil.isAdmin(author) || comment.getAuthor().getUsername().equals(author)){
            comment.setContent(commentUpdateDto.getContent());

            commentRepository.save(comment);
        }else{
            throw new RuntimeException("Unauthorized!"); // TODO better exception
        }
    }

    @Override
    public void deleteComment(String author, Long id) {
        // TODO filtering based on visibility
        Comment comment = commentRepository.findById(id).orElseThrow(() -> new RuntimeException("No such comment")); // TODO better exception
        if (authorizationUtil.isAdmin(author) || comment.getAuthor().getUsername().equals(author)){
            commentRepository.deleteById(id);
        }else{
            throw new RuntimeException("Unauthorized!"); // TODO better exception
        }
    }
}
