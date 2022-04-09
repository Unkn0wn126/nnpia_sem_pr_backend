package cz.upce.fei.sem_pr_backend.service;

import cz.upce.fei.sem_pr_backend.domain.ApplicationUser;
import cz.upce.fei.sem_pr_backend.domain.enum_type.UserState;
import cz.upce.fei.sem_pr_backend.dto.ApplicationUserCreateDto;
import cz.upce.fei.sem_pr_backend.dto.ProfileCreateDto;
import cz.upce.fei.sem_pr_backend.repository.ApplicationUserRepository;
import cz.upce.fei.sem_pr_backend.repository.ProfileRepository;
import cz.upce.fei.sem_pr_backend.repository.RoleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTests {

    @Mock
    private ApplicationUserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private ProfileRepository profileRepository;

    ApplicationUserServiceImpl userService;

    @BeforeEach
    void initUseCase(){
        userService = new ApplicationUserServiceImpl(userRepository, roleRepository, profileRepository, new BCryptPasswordEncoder(), new ModelMapper());
    }

    @Test
    void save_whenUserIsValid_Success() {
        ApplicationUser user = new ApplicationUser();
        user.setEmail("email@example.com");
        user.setPassword("P4ssw0rd$");
        user.setUsername("user");
        user.setState(UserState.ACTIVE);

        ApplicationUserCreateDto applicationUserCreateDto = new ApplicationUserCreateDto("user", "email@example.com", "P4ssw0rd$", new ProfileCreateDto("Userr", ""));

        when(userRepository.save(any(ApplicationUser.class))).thenReturn(user);

        ApplicationUser created = userService.saveUser(applicationUserCreateDto);
        assertThat(created.getUsername()).isSameAs(user.getUsername());
    }

}
