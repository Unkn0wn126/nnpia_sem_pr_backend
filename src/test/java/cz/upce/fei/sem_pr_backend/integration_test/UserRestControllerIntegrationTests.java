package cz.upce.fei.sem_pr_backend.integration_test;

import cz.upce.fei.sem_pr_backend.repository.ApplicationUserRepository;
import org.junit.jupiter.api.Test;
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
class UserRestControllerIntegrationTests {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ApplicationUserRepository userRepository;

    @Test
    void contextLoads() {
    }

}
