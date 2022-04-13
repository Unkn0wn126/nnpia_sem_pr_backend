package cz.upce.fei.sem_pr_backend.controller.v1.public_endpoints;

import com.fasterxml.jackson.databind.ObjectMapper;
import cz.upce.fei.sem_pr_backend.dto.applicationuser.ApplicationUserCreateDto;
import cz.upce.fei.sem_pr_backend.dto.profile.ProfileCreateDto;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@TestPropertySource(
        locations = "classpath:application-integrationtest.properties")
class AuthenticationControllerTest {

    public static final String API_1_0 = "/api/v1";
    public static final String API_1_0_USERS = API_1_0 + "/users";
    public static final String API_1_0_LOGIN = API_1_0 + "/login";
    public static final String API_1_0_REGISTER = API_1_0 + "/register";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void postUser_whenUserIsValid_receiveOk() throws Exception {
        ApplicationUserCreateDto userCreateDto = new ApplicationUserCreateDto();
        userCreateDto.setUsername("user");
        userCreateDto.setPassword("P4ssw0rd$");
        userCreateDto.setEmail("email@exampl.com");
        ProfileCreateDto profileCreateDto = new ProfileCreateDto();
        profileCreateDto.setProfilePicturePath(null);
        profileCreateDto.setNickname("User");
        userCreateDto.setProfile(profileCreateDto);
        String body = objectMapper.writeValueAsString(userCreateDto);

        mockMvc.perform(MockMvcRequestBuilders.post(API_1_0_REGISTER)
                .content(body)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }


    @Test
    void postUser_whenUserHasInvalidPassword_receiveBadRequest() throws Exception {
        ApplicationUserCreateDto userCreateDto = new ApplicationUserCreateDto();
        userCreateDto.setUsername("user");
        userCreateDto.setPassword("password");
        userCreateDto.setEmail("email@exampl.com");
        ProfileCreateDto profileCreateDto = new ProfileCreateDto();
        profileCreateDto.setProfilePicturePath(null);
        profileCreateDto.setNickname("User");
        userCreateDto.setProfile(profileCreateDto);
        String body = objectMapper.writeValueAsString(userCreateDto);

        mockMvc.perform(MockMvcRequestBuilders.post(API_1_0_REGISTER)
                        .content(body)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

}
