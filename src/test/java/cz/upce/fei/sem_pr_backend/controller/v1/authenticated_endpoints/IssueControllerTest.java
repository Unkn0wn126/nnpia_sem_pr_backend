package cz.upce.fei.sem_pr_backend.controller.v1.authenticated_endpoints;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import cz.upce.fei.sem_pr_backend.domain.ApplicationUser;
import cz.upce.fei.sem_pr_backend.domain.Role;
import cz.upce.fei.sem_pr_backend.domain.enum_type.IssueSeverity;
import cz.upce.fei.sem_pr_backend.domain.enum_type.IssueVisibility;
import cz.upce.fei.sem_pr_backend.domain.enum_type.RoleType;
import cz.upce.fei.sem_pr_backend.dto.applicationuser.ApplicationUserCreateDto;
import cz.upce.fei.sem_pr_backend.dto.issue.IssueCreateDto;
import cz.upce.fei.sem_pr_backend.dto.profile.ProfileCreateDto;
import cz.upce.fei.sem_pr_backend.service.ApplicationUserService;
import cz.upce.fei.sem_pr_backend.service.ApplicationUserServiceImpl;
import cz.upce.fei.sem_pr_backend.service.IssueService;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
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
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@TestPropertySource(
        locations = "classpath:application-test.properties")
@ActiveProfiles(profiles = "test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
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

    @BeforeAll
    public void initIssues(){
        userService.saveRole(new Role(null, RoleType.ROLE_ADMIN, new HashSet<>()));
        userService.saveRole(new Role(null, RoleType.ROLE_USER, new HashSet<>()));

        ApplicationUserCreateDto userCreateDto =
                new ApplicationUserCreateDto("rando", "email@example.com", "P4ssw0rd$",
                        new ProfileCreateDto("Rando", null));
        userService.saveUser(userCreateDto);
        userService.addRoleToUser("rando", RoleType.ROLE_USER);

        Principal principal = Mockito.mock(Principal.class);
        when(principal.getName()).thenReturn("rando");

        issueService.createIssue(principal,
                new IssueCreateDto("It's not working", "Title", IssueSeverity.LOW, IssueVisibility.PUBLIC, null));
    }

    @Before
    public void init(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getIssues_whenNotAuthenticated_receiveUnAuthorized() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(API_1_0_ISSUES + "/")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }

    @Test
    @WithMockUser(username = "rando", password = "P4ssw0rd$", authorities = "ROLE_USER")
    void getIssues_whenAuthenticated_receiveOk() throws Exception {
        Principal principal = Mockito.mock(Principal.class);
        when(principal.getName()).thenReturn("rando");

        mockMvc.perform(MockMvcRequestBuilders
                .get(API_1_0_ISSUES + "/")
                .principal(principal)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @WithMockUser(username = "rando", password = "P4ssw0rd$", authorities = "ROLE_USER")
    void createIssue_whenAuthenticated_receiveOk() throws Exception {
        Principal principal = Mockito.mock(Principal.class);
        when(principal.getName()).thenReturn("rando");

        IssueCreateDto issueCreateDto = new IssueCreateDto("Testing that create works", "Title", IssueSeverity.LOW, IssueVisibility.PUBLIC, null);
        String body = objectMapper.writeValueAsString(issueCreateDto);

        mockMvc.perform(MockMvcRequestBuilders
                        .post(API_1_0_ISSUES_CREATE + "/")
                        .content(body)
                        .principal(principal)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}

