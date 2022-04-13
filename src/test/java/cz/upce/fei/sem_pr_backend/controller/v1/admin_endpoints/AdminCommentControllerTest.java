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
public class AdminCommentControllerTest {

    public static final String API_1_0_COMMENTS = "/api/v1/admin/comments";
    public static final String API_1_0_COMMENTS_AUTHOR = API_1_0_COMMENTS + "/user/";
    public static final String API_1_0_COMMENTS_ISSUE = API_1_0_COMMENTS + "/issue/";
    public static final String API_1_0_COMMENTS_CREATE = API_1_0_COMMENTS + "/create";
    public static final String API_1_0_COMMENTS_DELETE = API_1_0_COMMENTS + "/delete/";
    public static final String API_1_0_COMMENTS_UPDATE = API_1_0_COMMENTS + "/update/";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;
}
