package cz.upce.fei.sem_pr_backend.service;

import cz.upce.fei.sem_pr_backend.domain.ApplicationUser;
import cz.upce.fei.sem_pr_backend.domain.Issue;
import cz.upce.fei.sem_pr_backend.domain.enum_type.IssueCompletionState;
import cz.upce.fei.sem_pr_backend.domain.enum_type.IssueSeverity;
import cz.upce.fei.sem_pr_backend.domain.enum_type.IssueVisibility;
import cz.upce.fei.sem_pr_backend.domain.enum_type.UserState;
import cz.upce.fei.sem_pr_backend.dto.applicationuser.ApplicationUserCreateDto;
import cz.upce.fei.sem_pr_backend.dto.applicationuser.ApplicationUserGetDto;
import cz.upce.fei.sem_pr_backend.dto.issue.IssueCreateDto;
import cz.upce.fei.sem_pr_backend.dto.issue.IssuePageGetDto;
import cz.upce.fei.sem_pr_backend.dto.profile.ProfileCreateDto;
import cz.upce.fei.sem_pr_backend.dto.profile.ProfileGetDto;
import cz.upce.fei.sem_pr_backend.repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;

import java.security.Principal;
import java.sql.Array;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Arrays;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class IssueServiceTests {

    @Mock
    private ApplicationUserRepository userRepository;

    @Mock
    private IssueRepository issueRepository;

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private ProfileRepository profileRepository;

    ApplicationUserService userService;

    IssueService issueService;

    @BeforeEach
    void initUseCase(){
        AuthorizationService authorizationService = new AuthorizationServiceImpl(userRepository);
        userService = new ApplicationUserServiceImpl(userRepository, roleRepository, profileRepository, new BCryptPasswordEncoder(), new ModelMapper(), authorizationService);
        issueService = new IssueServiceImpl(issueRepository, userRepository, commentRepository, new ModelMapper(), authorizationService);
    }

    @Test
    @WithMockUser(username = "user", password = "P4ssw0rd$", authorities = "ROLE_USER")
    void save_whenIssueIsValid_Success() {
        Principal principal = Mockito.mock(Principal.class);
        when(principal.getName()).thenReturn("user");

        ApplicationUser user = new ApplicationUser();
        user.setEmail("email@example.com");
        user.setPassword("P4ssw0rd$");
        user.setUsername("user");
        user.setState(UserState.ACTIVE);

        ApplicationUserGetDto applicationUserGetDto = new ApplicationUserGetDto();
        applicationUserGetDto.setEmail("email@example.com");
        applicationUserGetDto.setUsername("user");
        applicationUserGetDto.setState(UserState.ACTIVE);
        applicationUserGetDto.setCreated(Timestamp.from(Instant.now()));
        applicationUserGetDto.setLastEdited(Timestamp.from(Instant.now()));
        ProfileGetDto profileGetDto = new ProfileGetDto();
        profileGetDto.setCreated(Timestamp.from(Instant.now()));
        profileGetDto.setLastEdited(Timestamp.from(Instant.now()));
        profileGetDto.setNickname("User");
        profileGetDto.setProfilePicture(null);
        applicationUserGetDto.setProfile(profileGetDto);

        IssueCreateDto issueCreateDto = new IssueCreateDto();
        issueCreateDto.setHeader("Header");
        issueCreateDto.setContent("Content");
        issueCreateDto.setSeverity(IssueSeverity.LOW.name());
        issueCreateDto.setVisibility(IssueVisibility.PUBLIC.name());

        Issue issue = new Issue();
        issue.setCompletionState(IssueCompletionState.TODO);
        issue.setAuthor(user);
        issue.setVisibility(IssueVisibility.PUBLIC);
        issue.setSeverity(IssueSeverity.LOW);
        issue.setContent("Content");
        issue.setHeader("Header");
        issue.setPublished(Timestamp.from(Instant.now()));
        issue.setLastEdited(Timestamp.from(Instant.now()));

        when(userRepository.findByUsername(any(String.class))).thenReturn(Optional.of(user));
        when(issueRepository.save(any(Issue.class))).thenReturn(issue);

        Issue created = issueService.createIssue(principal, issueCreateDto);
        assertThat(created.getAuthor().getUsername()).isSameAs(issue.getAuthor().getUsername());
    }

    @Test
    void getAllPublicIssues_whenNotAuthenticated_Success() {
        ApplicationUser user = new ApplicationUser();
        user.setEmail("email@example.com");
        user.setPassword("P4ssw0rd$");
        user.setUsername("user");
        user.setState(UserState.ACTIVE);

        IssueCreateDto issueCreateDto = new IssueCreateDto();
        issueCreateDto.setHeader("Header");
        issueCreateDto.setContent("Content");
        issueCreateDto.setSeverity(IssueSeverity.LOW.name());
        issueCreateDto.setVisibility(IssueVisibility.PUBLIC.name());

        Issue publicIssue = new Issue();
        publicIssue.setCompletionState(IssueCompletionState.TODO);
        publicIssue.setAuthor(user);
        publicIssue.setVisibility(IssueVisibility.PUBLIC);
        publicIssue.setSeverity(IssueSeverity.LOW);
        publicIssue.setContent("Content");
        publicIssue.setHeader("Header");
        publicIssue.setPublished(Timestamp.from(Instant.now()));
        publicIssue.setLastEdited(Timestamp.from(Instant.now()));

        Issue secondPublicIssue = new Issue();
        secondPublicIssue.setCompletionState(IssueCompletionState.TODO);
        secondPublicIssue.setAuthor(user);
        secondPublicIssue.setVisibility(IssueVisibility.PUBLIC);
        secondPublicIssue.setSeverity(IssueSeverity.LOW);
        secondPublicIssue.setContent("Content");
        secondPublicIssue.setHeader("Header");
        secondPublicIssue.setPublished(Timestamp.from(Instant.now()));
        secondPublicIssue.setLastEdited(Timestamp.from(Instant.now()));

        Issue internalIssue = new Issue();
        internalIssue.setCompletionState(IssueCompletionState.TODO);
        internalIssue.setAuthor(user);
        internalIssue.setVisibility(IssueVisibility.INTERNAL);
        internalIssue.setSeverity(IssueSeverity.LOW);
        internalIssue.setContent("Content");
        internalIssue.setHeader("Header");
        internalIssue.setPublished(Timestamp.from(Instant.now()));
        internalIssue.setLastEdited(Timestamp.from(Instant.now()));

        when(issueRepository.findAllByVisibility(Arrays.asList(IssueVisibility.PUBLIC), null, PageRequest.of(0, 5, Sort.Direction.ASC, "id"))).thenReturn(new PageImpl<>(Arrays.asList(publicIssue, secondPublicIssue)));

        IssuePageGetDto result = issueService.getAllIssues(null, 0, 5, "ASC", "id");
        assertThat(result.getTotalItems()).isEqualTo(2L);
        assertThat(result.getTotalPages()).isEqualTo(1);
        assertThat(result.getCurrentPage()).isEqualTo(0);
        assertThat(result.getIssues().size()).isEqualTo(2);
    }

}
