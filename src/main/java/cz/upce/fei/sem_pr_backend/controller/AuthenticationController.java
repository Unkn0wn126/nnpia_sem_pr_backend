package cz.upce.fei.sem_pr_backend.controller;

import cz.upce.fei.sem_pr_backend.dto.applicationuser.ApplicationUserCreateDto;
import cz.upce.fei.sem_pr_backend.service.ApplicationUserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class AuthenticationController {

    private final ApplicationUserService userService;

    public AuthenticationController(ApplicationUserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public void register(@RequestBody ApplicationUserCreateDto userRegisterDto){
        userService.createNormalUser(userRegisterDto);
    }
}
