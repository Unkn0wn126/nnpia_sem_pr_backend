package cz.upce.fei.sem_pr_backend.service;

import cz.upce.fei.sem_pr_backend.domain.ApplicationUser;
import cz.upce.fei.sem_pr_backend.domain.enum_type.UserState;
import cz.upce.fei.sem_pr_backend.repository.ApplicationUserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTests {

    @Mock
    private ApplicationUserRepository userRepository;

    ApplicationUserServiceImpl userService;

    @BeforeEach
    void initUseCase(){
        userService = new ApplicationUserServiceImpl(userRepository);
    }

    @Test
    void save_whenUserIsValid_Success() {
        ApplicationUser user = new ApplicationUser();
        user.setEmail("email@example.com");
        user.setPassword("P4ssw0rd$");
        user.setUsername("user");
        user.setState(UserState.ACTIVE);

        when(userRepository.save(any(ApplicationUser.class))).thenReturn(user);

        ApplicationUser created = userService.save(user);
        assertThat(created.getUsername()).isSameAs(user.getUsername());
    }

}
