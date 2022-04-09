package cz.upce.fei.sem_pr_backend.service;

import cz.upce.fei.sem_pr_backend.authorization.AuthorizationUtil;
import cz.upce.fei.sem_pr_backend.domain.ApplicationUser;
import cz.upce.fei.sem_pr_backend.domain.Issue;
import cz.upce.fei.sem_pr_backend.domain.enum_type.IssueCompletionState;
import cz.upce.fei.sem_pr_backend.dto.comment.CommentGetDto;
import cz.upce.fei.sem_pr_backend.dto.issue.IssueCreateDto;
import cz.upce.fei.sem_pr_backend.dto.issue.IssueGetDto;
import cz.upce.fei.sem_pr_backend.dto.issue.IssueUpdateDto;
import cz.upce.fei.sem_pr_backend.repository.ApplicationUserRepository;
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
    private final ModelMapper modelMapper;
    private final AuthorizationUtil authorizationUtil;

    public IssueServiceImpl(IssueRepository issueRepository, ApplicationUserRepository userRepository, ModelMapper modelMapper, AuthorizationUtil authorizationUtil) {
        this.issueRepository = issueRepository;
        this.userRepository = userRepository;
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
    public void updateIssue(String author, IssueUpdateDto issueUpdateDto) {
        Issue issue = issueRepository.findById(issueUpdateDto.getId()).orElseThrow(() -> new RuntimeException("No such issue")); // TODO better exception
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
            throw new RuntimeException("Unauthorized!");
        }
    }

    @Override
    public void deleteIssue(String author, Long id) {

    }

    @Override
    public IssueGetDto getIssueById(Long id) {
        return null;
    }

    @Override
    public List<IssueGetDto> getAllIssues() {
        return issueRepository.findAll().stream().map(issue -> modelMapper.map(issue, IssueGetDto.class)).collect(Collectors.toList());
    }

    @Override
    public List<IssueGetDto> getIssueByAuthorName(String authorName) {
        return null;
    }

    @Override
    public List<CommentGetDto> getIssueComments(Long id) {
        return null;
    }
}
