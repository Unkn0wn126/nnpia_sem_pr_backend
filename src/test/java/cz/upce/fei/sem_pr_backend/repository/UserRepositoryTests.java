package cz.upce.fei.sem_pr_backend.repository;

import cz.upce.fei.sem_pr_backend.domain.ApplicationUser;
import cz.upce.fei.sem_pr_backend.domain.enum_type.UserState;
import cz.upce.fei.sem_pr_backend.repository.ApplicationUserRepository;
import cz.upce.fei.sem_pr_backend.service.ApplicationUserServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserRepositoryTests {

    @Mock
    private ApplicationUserRepository userRepository;

    @InjectMocks
    ApplicationUserServiceImpl userService;

    @Test
    void postUser_whenUserIsValid_receiveOk() {
        when(userRepository.save(any(ApplicationUser.class))).thenReturn(new ApplicationUser());

        ApplicationUser user = new ApplicationUser();
        user.setEmail("email@example.com");
        user.setPassword("P4ssw0rd$");
        user.setUsername("user");
        user.setState(UserState.ACTIVE);

        ApplicationUser created = userService.save(user);
        assertThat(created.getUsername()).isSameAs(user.getUsername());
    }

}
