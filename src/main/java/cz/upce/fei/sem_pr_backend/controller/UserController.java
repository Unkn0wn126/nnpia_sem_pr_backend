package cz.upce.fei.sem_pr_backend.controller;

import cz.upce.fei.sem_pr_backend.dto.applicationuser.ApplicationUserGetDto;
import cz.upce.fei.sem_pr_backend.service.ApplicationUserService;
import org.springframework.web.bind.annotation.*;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final ApplicationUserService userService;

    public UserController(ApplicationUserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public List<ApplicationUserGetDto> getUsers(){
        throw new NotImplementedException(); // TODO
    }

    @GetMapping("/id/{id}")
    public ApplicationUserGetDto getUserById(@PathVariable Long id){
        throw new NotImplementedException(); // TODO
    }

    @GetMapping("/{username}")
    public ApplicationUserGetDto getUserByUsername(@PathVariable String username){
        throw new NotImplementedException(); // TODO
    }

    @DeleteMapping("/delete/{id}")
    public void deleteUserById(@PathVariable Long id){
        throw new NotImplementedException(); // TODO
    }

    @PutMapping("/update/{id}")
    public void updateUserById(@PathVariable Long id){
        throw new NotImplementedException(); // TODO
    }
}
