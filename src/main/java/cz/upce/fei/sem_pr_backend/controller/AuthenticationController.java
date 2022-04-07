package cz.upce.fei.sem_pr_backend.controller;

import cz.upce.fei.sem_pr_backend.dto.ApplicationUserRegisterDto;
import cz.upce.fei.sem_pr_backend.entity.ApplicationUser;
import cz.upce.fei.sem_pr_backend.entity.enum_type.UserState;
import cz.upce.fei.sem_pr_backend.repository.ApplicationUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
    public void register(@RequestBody ApplicationUserRegisterDto userRegisterDto){
        ApplicationUser user = new ApplicationUser();
        user.setState(UserState.ACTIVE);
        user.setUsername(userRegisterDto.getUsername());
        user.setPassword(userRegisterDto.getPassword());
        user.setEmail(userRegisterDto.getEmail());
        userRepository.save(user);
    }
}
