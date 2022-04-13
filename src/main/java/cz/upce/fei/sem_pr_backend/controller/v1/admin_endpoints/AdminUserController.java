package cz.upce.fei.sem_pr_backend.controller.v1.admin_endpoints;

import cz.upce.fei.sem_pr_backend.dto.applicationuser.ApplicationUserGetDto;
import cz.upce.fei.sem_pr_backend.dto.applicationuser.ApplicationUserUpdateDto;
import cz.upce.fei.sem_pr_backend.dto.applicationuser.ApplicationUserUpdatePasswordDto;
import cz.upce.fei.sem_pr_backend.service.ApplicationUserService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("api/v1/admin/users")
public class AdminUserController {

    private final ApplicationUserService userService;

    public AdminUserController(ApplicationUserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public List<ApplicationUserGetDto> getUsers(@RequestParam(defaultValue = "0") Integer pageNumber, @RequestParam(defaultValue = "5") Integer pageSize){
        return userService.getAllUsers(pageNumber, pageSize);
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
        userService.deleteUser(principal, id);
    }

    @PutMapping("/update/{id}")
    public void updateUserById(Principal principal, @PathVariable Long id, @Valid @RequestBody ApplicationUserUpdateDto userUpdateDto){
        userService.updateUserInfo(principal, id, userUpdateDto);
    }

    @PutMapping("/update/password/{id}")
    public void updateUserPasswordById(Principal principal, @PathVariable Long id, @Valid @RequestBody ApplicationUserUpdatePasswordDto userUpdateDto){
        userService.updateUserPassword(principal, id, userUpdateDto);
    }
}
