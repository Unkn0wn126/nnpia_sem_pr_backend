package cz.upce.fei.sem_pr_backend.controller;

import cz.upce.fei.sem_pr_backend.dto.applicationuser.ApplicationUserCreateDto;
import cz.upce.fei.sem_pr_backend.dto.profile.ProfileCreateDto;
import cz.upce.fei.sem_pr_backend.repository.ApplicationUserRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@TestPropertySource(
        locations = "classpath:application-integrationtest.properties")
class UserRestControllerIntegrationTests {

    public static final String API_1_0_USERS = "/api/v1";

    @Autowired
    private MockMvc mvc;

    @Autowired
    TestRestTemplate testRestTemplate;

    @Autowired
    private ApplicationUserRepository userRepository;

    @Test
    void postUser_whenUserIsValid_receiveOk() {
        ApplicationUserCreateDto userCreateDto = new ApplicationUserCreateDto();
        userCreateDto.setUsername("user");
        userCreateDto.setPassword("P4ssw0rd$");
        userCreateDto.setEmail("email@exampl.com");
        ProfileCreateDto profileCreateDto = new ProfileCreateDto();
        profileCreateDto.setProfilePicturePath(null);
        profileCreateDto.setNickname("User");
        userCreateDto.setProfile(profileCreateDto);

        ResponseEntity<Object> response = testRestTemplate.postForEntity(API_1_0_USERS + "/register", userCreateDto, Object.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

}
