package cz.upce.fei.sem_pr_backend.controller.v1;

import cz.upce.fei.sem_pr_backend.dto.applicationuser.ApplicationUserGetDto;
import cz.upce.fei.sem_pr_backend.dto.applicationuser.ApplicationUserPageGetDto;
import cz.upce.fei.sem_pr_backend.dto.applicationuser.ApplicationUserUpdateDto;
import cz.upce.fei.sem_pr_backend.dto.applicationuser.ApplicationUserUpdatePasswordDto;
import cz.upce.fei.sem_pr_backend.service.ApplicationUserService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;
import java.security.Principal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final ApplicationUserService userService;

    public UserController(ApplicationUserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public ApplicationUserPageGetDto getUsers(@RequestParam(defaultValue = "0") Integer pageNumber,
                                              @RequestParam(defaultValue = "5") Integer pageSize,
                                              @Valid @Pattern(regexp = "ASC|DESC") @RequestParam(defaultValue = "ASC") String direction,
                                              @RequestParam(defaultValue = "published") String... orderBy){
        return userService.getAllUsers(pageNumber, pageSize, direction, orderBy);
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
