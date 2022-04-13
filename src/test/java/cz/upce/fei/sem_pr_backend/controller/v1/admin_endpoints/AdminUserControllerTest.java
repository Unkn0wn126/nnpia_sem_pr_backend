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
public class AdminUserControllerTest {

    public static final String API_1_0_USERS = "/api/v1/admin/users";
    public static final String API_1_0_USERS_ID = API_1_0_USERS + "/id";
    public static final String API_1_0_USERS_DELETE = API_1_0_USERS + "/delete/";
    public static final String API_1_0_USERS_UPDATE = API_1_0_USERS + "/update/";
    public static final String API_1_0_USERS_UPDATE_PASSWORD = API_1_0_USERS + "/update/password/";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;
}
