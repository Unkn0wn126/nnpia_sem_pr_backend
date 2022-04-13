package cz.upce.fei.sem_pr_backend.controller.v1.admin_endpoints;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@TestPropertySource(
        locations = "classpath:application-integrationtest.properties")
public class AdminIssueControllerTest {

    public static final String API_1_0_ISSUES = "/api/v1/admin/issues";
    public static final String API_1_0_ISSUES_AUTHOR = API_1_0_ISSUES + "/user/";
    public static final String API_1_0_ISSUES_CREATE = API_1_0_ISSUES + "/create";
    public static final String API_1_0_ISSUES_DELETE = API_1_0_ISSUES + "/delete/";
    public static final String API_1_0_ISSUES_UPDATE = API_1_0_ISSUES + "/update/";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;
}
