package cz.upce.fei.sem_pr_backend.controller.v1.public_endpoints;

import com.fasterxml.jackson.databind.ObjectMapper;
import cz.upce.fei.sem_pr_backend.domain.Role;
import cz.upce.fei.sem_pr_backend.domain.enum_type.IssueSeverity;
import cz.upce.fei.sem_pr_backend.domain.enum_type.IssueVisibility;
import cz.upce.fei.sem_pr_backend.domain.enum_type.RoleType;
import cz.upce.fei.sem_pr_backend.dto.applicationuser.ApplicationUserCreateDto;
import cz.upce.fei.sem_pr_backend.dto.issue.IssueCreateDto;
import cz.upce.fei.sem_pr_backend.dto.profile.ProfileCreateDto;
import cz.upce.fei.sem_pr_backend.service.ApplicationUserService;
import cz.upce.fei.sem_pr_backend.service.IssueService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.security.Principal;
import java.util.HashSet;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@TestPropertySource(
        locations = "classpath:application-test.properties")
@ActiveProfiles(profiles = "test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class PublicIssueControllerTest {

    public static final String API_1_0_PUBLIC = "/api/v1/public";
    public static final String API_1_0_ISSUES = API_1_0_PUBLIC + "/issues";

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

    @Test
    void getAllIssues_whenNotAuthenticated_receiveOk() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get(API_1_0_ISSUES + "/")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void getAllPublicIssues_whenNotAuthenticated_receiveOk() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get(API_1_0_ISSUES + "/")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.issues", hasSize(2)));
    }
}
