package cz.upce.fei.sem_pr_backend.controller.v1.authenticated_endpoints;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import cz.upce.fei.sem_pr_backend.domain.ApplicationUser;
import cz.upce.fei.sem_pr_backend.domain.Role;
import cz.upce.fei.sem_pr_backend.domain.enum_type.IssueCompletionState;
import cz.upce.fei.sem_pr_backend.domain.enum_type.IssueSeverity;
import cz.upce.fei.sem_pr_backend.domain.enum_type.IssueVisibility;
import cz.upce.fei.sem_pr_backend.domain.enum_type.RoleType;
import cz.upce.fei.sem_pr_backend.dto.applicationuser.ApplicationUserCreateDto;
import cz.upce.fei.sem_pr_backend.dto.issue.IssueCreateDto;
import cz.upce.fei.sem_pr_backend.dto.issue.IssueUpdateDto;
import cz.upce.fei.sem_pr_backend.dto.profile.ProfileCreateDto;
import cz.upce.fei.sem_pr_backend.service.ApplicationUserService;
import cz.upce.fei.sem_pr_backend.service.ApplicationUserServiceImpl;
import cz.upce.fei.sem_pr_backend.service.IssueService;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.jupiter.api.*;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.security.Principal;
import java.time.LocalDate;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@TestPropertySource(
        locations = "classpath:application-test.properties")
@ActiveProfiles(profiles = "test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class IssueControllerTest {

    public static final String API_1_0_ISSUES = "/api/v1/issues";
    public static final String API_1_0_ISSUES_AUTHOR = API_1_0_ISSUES + "/user/";
    public static final String API_1_0_ISSUES_CREATE = API_1_0_ISSUES + "/create";
    public static final String API_1_0_ISSUES_DELETE = API_1_0_ISSUES + "/delete/";
    public static final String API_1_0_ISSUES_UPDATE = API_1_0_ISSUES + "/update/";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ApplicationUserService userService;

    @Qualifier("issueServiceImpl")
    @Autowired
    private IssueService issueService;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void initIssues(){
        ApplicationUserCreateDto userCreateDto =
                new ApplicationUserCreateDto("rando", "email@example.com", "P4ssw0rd$",
                        new ProfileCreateDto("Rando", null));
        userService.saveUser(userCreateDto);
        userService.addRoleToUser("rando", RoleType.ROLE_USER);

        Principal adminPrincipal = Mockito.mock(Principal.class);
        when(adminPrincipal.getName()).thenReturn("admin");

        issueService.createIssue(adminPrincipal,
                new IssueCreateDto("Admin 1", "Admin 1", IssueSeverity.LOW.name(), IssueVisibility.PUBLIC.name(), null));
        issueService.createIssue(adminPrincipal,
                new IssueCreateDto("Admin 2", "Admin 2", IssueSeverity.LOW.name(), IssueVisibility.INTERNAL.name(), null));
        issueService.createIssue(adminPrincipal,
                new IssueCreateDto("Admin 3", "Admin 3", IssueSeverity.LOW.name(), IssueVisibility.PRIVATE.name(), null));

        Principal randoPrincipal = Mockito.mock(Principal.class);
        when(randoPrincipal.getName()).thenReturn("rando");

        issueService.createIssue(randoPrincipal,
                new IssueCreateDto("Rando 1", "Rando 1", IssueSeverity.LOW.name(), IssueVisibility.PUBLIC.name(), null));
        issueService.createIssue(randoPrincipal,
                new IssueCreateDto("Rando 2", "Rando 2", IssueSeverity.LOW.name(), IssueVisibility.INTERNAL.name(), null));
        issueService.createIssue(randoPrincipal,
                new IssueCreateDto("Rando 3", "Rando 3", IssueSeverity.LOW.name(), IssueVisibility.PRIVATE.name(), null));
    }

    @Before
    public void init(){
        MockitoAnnotations.openMocks(this);
    }

    // Get all

    @Test
    void getAllIssues_whenNotAuthenticated_receiveUnAuthorized() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(API_1_0_ISSUES + "/")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }

    @Test
    @WithMockUser(username = "rando", password = "P4ssw0rd$", authorities = "ROLE_USER")
    void getAllIssues_whenAuthenticated_receiveOk() throws Exception {
        Principal principal = Mockito.mock(Principal.class);
        when(principal.getName()).thenReturn("rando");

        mockMvc.perform(MockMvcRequestBuilders
                .get(API_1_0_ISSUES + "/")
                .principal(principal)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(5)));
    }

    // Get by id

    @Test
    @WithMockUser(username = "rando", password = "P4ssw0rd$", authorities = "ROLE_USER")
    void getIssueById_whenAuthenticated_receiveOk() throws Exception {
        Principal principal = Mockito.mock(Principal.class);
        when(principal.getName()).thenReturn("rando");

        mockMvc.perform(MockMvcRequestBuilders
                        .get(API_1_0_ISSUES + "/4")
                        .principal(principal)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @WithMockUser(username = "rando", password = "P4ssw0rd$", authorities = "ROLE_USER")
    void getNonExistingIssueById_whenAuthenticated_receiveNotFound() throws Exception {
        Principal principal = Mockito.mock(Principal.class);
        when(principal.getName()).thenReturn("rando");

        mockMvc.perform(MockMvcRequestBuilders
                        .get(API_1_0_ISSUES + "/10")
                        .principal(principal)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    @WithMockUser(username = "rando", password = "P4ssw0rd$", authorities = "ROLE_USER")
    void getInaccessibleIssueById_whenAuthenticated_receiveNotFound() throws Exception {
        Principal principal = Mockito.mock(Principal.class);
        when(principal.getName()).thenReturn("rando");

        mockMvc.perform(MockMvcRequestBuilders
                        .get(API_1_0_ISSUES + "/3")
                        .principal(principal)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void getIssueById_whenNotAuthenticated_receiveUnAuthorized() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(API_1_0_ISSUES + "/4")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }

    // Get by author

    @Test
    @WithMockUser(username = "rando", password = "P4ssw0rd$", authorities = "ROLE_USER")
    void getOwnIssuesByAuthor_whenAuthenticated_receiveOk() throws Exception {
        Principal principal = Mockito.mock(Principal.class);
        when(principal.getName()).thenReturn("rando");

        mockMvc.perform(MockMvcRequestBuilders
                        .get(API_1_0_ISSUES_AUTHOR + "/rando")
                        .principal(principal)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(3)));
    }

    @Test
    @WithMockUser(username = "rando", password = "P4ssw0rd$", authorities = "ROLE_USER")
    void getOthersIssuesByAuthor_whenAuthenticated_receiveOk() throws Exception {
        Principal principal = Mockito.mock(Principal.class);
        when(principal.getName()).thenReturn("rando");

        mockMvc.perform(MockMvcRequestBuilders
                        .get(API_1_0_ISSUES_AUTHOR + "/admin")
                        .principal(principal)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(2)));
    }

    @Test
    void getIssuesByAuthor_whenNotAuthenticated_receiveUnAuthorized() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(API_1_0_ISSUES + "/4")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }

    // Create

    @Test
    @WithMockUser(username = "rando", password = "P4ssw0rd$", authorities = "ROLE_USER")
    void createIssue_whenAuthenticated_receiveOk() throws Exception {
        Principal principal = Mockito.mock(Principal.class);
        when(principal.getName()).thenReturn("rando");

        IssueCreateDto issueCreateDto = new IssueCreateDto("Testing that create works", "Title", IssueSeverity.LOW.name(), IssueVisibility.PUBLIC.name(), null);
        String body = objectMapper.writeValueAsString(issueCreateDto);

        mockMvc.perform(MockMvcRequestBuilders
                        .post(API_1_0_ISSUES_CREATE + "/")
                        .content(body)
                        .principal(principal)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    // Update

    @Test
    @WithMockUser(username = "rando", password = "P4ssw0rd$", authorities = "ROLE_USER")
    void updateOwnIssue_whenAuthenticated_receiveOk() throws Exception {
        Principal principal = Mockito.mock(Principal.class);
        when(principal.getName()).thenReturn("rando");

        IssueUpdateDto issueUpdateDto = new IssueUpdateDto("Rando 1 Edited", "Rando 1 edited", IssueSeverity.LOW.name(), IssueVisibility.PUBLIC.name(), null, IssueCompletionState.IN_PROGRESS.name());
        String body = objectMapper.writeValueAsString(issueUpdateDto);

        mockMvc.perform(MockMvcRequestBuilders
                        .put(API_1_0_ISSUES_UPDATE + "/4")
                        .content(body)
                        .principal(principal)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @WithMockUser(username = "rando", password = "P4ssw0rd$", authorities = "ROLE_USER")
    void updateOwnIssue_whenAuthenticatedButDueDateIsInPast_receiveBadRequest() throws Exception {
        Principal principal = Mockito.mock(Principal.class);
        when(principal.getName()).thenReturn("rando");

        IssueUpdateDto issueUpdateDto = new IssueUpdateDto("Rando 1 Edited", "Rando 1 edited", IssueSeverity.LOW.name(), IssueVisibility.PUBLIC.name(), new Date(0), IssueCompletionState.IN_PROGRESS.name());
        String body = objectMapper.writeValueAsString(issueUpdateDto);

        mockMvc.perform(MockMvcRequestBuilders
                        .put(API_1_0_ISSUES_UPDATE + "/4")
                        .content(body)
                        .principal(principal)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    @WithMockUser(username = "rando", password = "P4ssw0rd$", authorities = "ROLE_USER")
    void updateOwnIssue_whenAuthenticatedButSeverityIsNull_receiveBadRequest() throws Exception {
        Principal principal = Mockito.mock(Principal.class);
        when(principal.getName()).thenReturn("rando");

        IssueUpdateDto issueUpdateDto = new IssueUpdateDto("Rando 1 Edited", "Rando 1 edited", null, IssueVisibility.PUBLIC.name(), null, IssueCompletionState.IN_PROGRESS.name());
        String body = objectMapper.writeValueAsString(issueUpdateDto);

        mockMvc.perform(MockMvcRequestBuilders
                        .put(API_1_0_ISSUES_UPDATE + "/4")
                        .content(body)
                        .principal(principal)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    @WithMockUser(username = "rando", password = "P4ssw0rd$", authorities = "ROLE_USER")
    void updateOwnIssue_whenAuthenticatedButVisibilityIsNull_receiveBadRequest() throws Exception {
        Principal principal = Mockito.mock(Principal.class);
        when(principal.getName()).thenReturn("rando");

        IssueUpdateDto issueUpdateDto = new IssueUpdateDto("Rando 1 Edited", "Rando 1 edited", IssueSeverity.LOW.name(), null, null, IssueCompletionState.IN_PROGRESS.name());
        String body = objectMapper.writeValueAsString(issueUpdateDto);

        mockMvc.perform(MockMvcRequestBuilders
                        .put(API_1_0_ISSUES_UPDATE + "/4")
                        .content(body)
                        .principal(principal)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    @WithMockUser(username = "rando", password = "P4ssw0rd$", authorities = "ROLE_USER")
    void updateOwnIssue_whenAuthenticatedButCompletionStateIsNull_receiveBadRequest() throws Exception {
        Principal principal = Mockito.mock(Principal.class);
        when(principal.getName()).thenReturn("rando");

        IssueUpdateDto issueUpdateDto = new IssueUpdateDto("Rando 1 Edited", "Rando 1 edited", IssueSeverity.LOW.name(), IssueVisibility.PUBLIC.name(), null, null);
        String body = objectMapper.writeValueAsString(issueUpdateDto);

        mockMvc.perform(MockMvcRequestBuilders
                        .put(API_1_0_ISSUES_UPDATE + "/4")
                        .content(body)
                        .principal(principal)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    @WithMockUser(username = "rando", password = "P4ssw0rd$", authorities = "ROLE_USER")
    void updateOwnIssue_whenAuthenticatedButSeverityIsInvalid_receiveBadRequest() throws Exception {
        Principal principal = Mockito.mock(Principal.class);
        when(principal.getName()).thenReturn("rando");

        IssueUpdateDto issueUpdateDto = new IssueUpdateDto("Rando 1 Edited", "Rando 1 edited", "a", IssueVisibility.PUBLIC.name(), null, IssueCompletionState.TODO.name());
        String body = objectMapper.writeValueAsString(issueUpdateDto);

        mockMvc.perform(MockMvcRequestBuilders
                        .put(API_1_0_ISSUES_UPDATE + "/4")
                        .content(body)
                        .principal(principal)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    @WithMockUser(username = "rando", password = "P4ssw0rd$", authorities = "ROLE_USER")
    void updateOwnIssue_whenAuthenticatedButVisibilityIsInvalid_receiveBadRequest() throws Exception {
        Principal principal = Mockito.mock(Principal.class);
        when(principal.getName()).thenReturn("rando");

        IssueUpdateDto issueUpdateDto = new IssueUpdateDto("Rando 1 Edited", "Rando 1 edited", IssueSeverity.LOW.name(), "a", null, IssueCompletionState.TODO.name());
        String body = objectMapper.writeValueAsString(issueUpdateDto);

        mockMvc.perform(MockMvcRequestBuilders
                        .put(API_1_0_ISSUES_UPDATE + "/4")
                        .content(body)
                        .principal(principal)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    @WithMockUser(username = "rando", password = "P4ssw0rd$", authorities = "ROLE_USER")
    void updateOwnIssue_whenAuthenticatedButCompletionStateIsInvalid_receiveBadRequest() throws Exception {
        Principal principal = Mockito.mock(Principal.class);
        when(principal.getName()).thenReturn("rando");

        IssueUpdateDto issueUpdateDto = new IssueUpdateDto("Rando 1 Edited", "Rando 1 edited", IssueSeverity.LOW.name(), IssueVisibility.PRIVATE.name(), null, "a");
        String body = objectMapper.writeValueAsString(issueUpdateDto);

        mockMvc.perform(MockMvcRequestBuilders
                        .put(API_1_0_ISSUES_UPDATE + "/4")
                        .content(body)
                        .principal(principal)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    @WithMockUser(username = "rando", password = "P4ssw0rd$", authorities = "ROLE_USER")
    void updateNonExistingIssue_whenAuthenticated_receiveNotFound() throws Exception {
        Principal principal = Mockito.mock(Principal.class);
        when(principal.getName()).thenReturn("rando");

        IssueUpdateDto issueUpdateDto = new IssueUpdateDto("Rando 1 Edited", "Rando 1 edited", IssueSeverity.LOW.name(), IssueVisibility.PUBLIC.name(), null, IssueCompletionState.IN_PROGRESS.name());
        String body = objectMapper.writeValueAsString(issueUpdateDto);

        mockMvc.perform(MockMvcRequestBuilders
                        .put(API_1_0_ISSUES_UPDATE + "/10")
                        .content(body)
                        .principal(principal)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    @WithMockUser(username = "rando", password = "P4ssw0rd$", authorities = "ROLE_USER")
    void updateOtherUsersIssue_whenAuthenticated_receiveUnAuthorized() throws Exception {
        Principal principal = Mockito.mock(Principal.class);
        when(principal.getName()).thenReturn("rando");

        IssueUpdateDto issueUpdateDto = new IssueUpdateDto("Rando 1 Edited", "Rando 1 edited", IssueSeverity.LOW.name(), IssueVisibility.PUBLIC.name(), null, IssueCompletionState.IN_PROGRESS.name());
        String body = objectMapper.writeValueAsString(issueUpdateDto);

        mockMvc.perform(MockMvcRequestBuilders
                        .put(API_1_0_ISSUES_UPDATE + "/1")
                        .content(body)
                        .principal(principal)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }

    @Test
    void updateIssue_whenNotAuthenticated_receiveUnAuthorized() throws Exception {
        IssueUpdateDto issueUpdateDto = new IssueUpdateDto("Rando 1 Edited", "Rando 1 edited", IssueSeverity.LOW.name(), IssueVisibility.PUBLIC.name(), null, IssueCompletionState.IN_PROGRESS.name());
        String body = objectMapper.writeValueAsString(issueUpdateDto);

        mockMvc.perform(MockMvcRequestBuilders
                        .put(API_1_0_ISSUES_UPDATE + "/1")
                        .content(body)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }

    // Delete

    @Test
    void deleteIssue_whenNotAuthenticated_receiveUnAuthorized() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .delete(API_1_0_ISSUES_DELETE + "/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }

    @Test
    @WithMockUser(username = "rando", password = "P4ssw0rd$", authorities = "ROLE_USER")
    void deleteOwnIssue_whenAuthenticated_receiveOk() throws Exception {
        Principal principal = Mockito.mock(Principal.class);
        when(principal.getName()).thenReturn("rando");

        mockMvc.perform(MockMvcRequestBuilders
                        .delete(API_1_0_ISSUES_DELETE + "/4")
                        .principal(principal)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }


    @Test
    @WithMockUser(username = "rando", password = "P4ssw0rd$", authorities = "ROLE_USER")
    void deleteNonExistingIssue_whenAuthenticated_receiveNotFound() throws Exception {
        Principal principal = Mockito.mock(Principal.class);
        when(principal.getName()).thenReturn("rando");

        mockMvc.perform(MockMvcRequestBuilders
                        .delete(API_1_0_ISSUES_DELETE + "/10")
                        .principal(principal)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    @WithMockUser(username = "rando", password = "P4ssw0rd$", authorities = "ROLE_USER")
    void deleteOtherUsersIssue_whenAuthenticated_receiveUnAuthorized() throws Exception {
        Principal principal = Mockito.mock(Principal.class);
        when(principal.getName()).thenReturn("rando");

        mockMvc.perform(MockMvcRequestBuilders
                        .delete(API_1_0_ISSUES_DELETE + "/1")
                        .principal(principal)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }
}

