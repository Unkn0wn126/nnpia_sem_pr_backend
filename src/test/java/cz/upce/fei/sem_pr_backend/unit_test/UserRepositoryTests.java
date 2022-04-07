package cz.upce.fei.sem_pr_backend.unit_test;

import cz.upce.fei.sem_pr_backend.entity.ApplicationUser;
import cz.upce.fei.sem_pr_backend.entity.enum_type.UserState;
import cz.upce.fei.sem_pr_backend.repository.ApplicationUserRepository;
import cz.upce.fei.sem_pr_backend.service.ApplicationUserService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Optional;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserRepositoryTests {

    @Mock
    private ApplicationUserRepository userRepository;

    @InjectMocks
    ApplicationUserService userService;

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
