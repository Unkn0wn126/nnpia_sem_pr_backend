package cz.upce.fei.sem_pr_backend.controller.v1.authenticated_endpoints;

import cz.upce.fei.sem_pr_backend.dto.applicationuser.ApplicationUserGetDto;
import cz.upce.fei.sem_pr_backend.dto.applicationuser.ApplicationUserUpdateDto;
import cz.upce.fei.sem_pr_backend.dto.applicationuser.ApplicationUserUpdatePasswordDto;
import cz.upce.fei.sem_pr_backend.service.ApplicationUserService;
import org.springframework.web.bind.annotation.*;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.security.Principal;
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
        return userService.getUsers();
    }

    @GetMapping("/id/{id}")
    public ApplicationUserGetDto getUserById(@PathVariable Long id){
        return userService.getUserById(id);
    }

    @GetMapping("/{username}")
    public ApplicationUserGetDto getUserByUsername(@PathVariable String username){
        return userService.getUserByUsername(username);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteUserById(Principal principal, @PathVariable Long id){
        userService.deleteUser(principal.getName(), id);
    }

    @PutMapping("/update/{id}")
    public void updateUserById(Principal principal, @PathVariable Long id, @RequestBody ApplicationUserUpdateDto userUpdateDto){
        userService.updateUserInfo(principal.getName(), id, userUpdateDto);
    }

    @PutMapping("/update/password/{id}")
    public void updateUserPasswordById(Principal principal, @PathVariable Long id, @RequestBody ApplicationUserUpdatePasswordDto userUpdateDto){
        userService.updateUserPassword(principal.getName(), id, userUpdateDto);
    }
}
