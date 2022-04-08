package cz.upce.fei.sem_pr_backend.controller;

import cz.upce.fei.sem_pr_backend.dto.ApplicationUserCreateDto;
import cz.upce.fei.sem_pr_backend.domain.ApplicationUser;
import cz.upce.fei.sem_pr_backend.domain.enum_type.UserState;
import cz.upce.fei.sem_pr_backend.repository.ApplicationUserRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class AuthenticationController {

    private final ApplicationUserRepository userRepository;

    public AuthenticationController(ApplicationUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping("/register")
    public void register(@RequestBody ApplicationUserCreateDto userRegisterDto){
        ApplicationUser user = new ApplicationUser();
        user.setState(UserState.ACTIVE);
        user.setUsername(userRegisterDto.getUsername());
        user.setPassword(userRegisterDto.getPassword());
        user.setEmail(userRegisterDto.getEmail());
        userRepository.save(user);
    }

    @PostMapping("/login")
    public void login(){

    }

    @PostMapping("/logout")
    public void logout(){

    }
}
