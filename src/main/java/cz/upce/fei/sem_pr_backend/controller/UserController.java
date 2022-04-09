package cz.upce.fei.sem_pr_backend.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    @GetMapping("/")
    public void getUsers(){
        System.out.println("users");
    }

    @GetMapping("/id/{id}")
    public void getUserById(@PathVariable Long id){
        System.out.println("user with id " + id);
    }

    @GetMapping("/{username}")
    public void getIssueByUsername(@PathVariable String username){
        System.out.println("user with name " + username);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteUserById(@PathVariable Long id){
        System.out.println("delete user with id " + id);
    }

    @PutMapping("/update/{id}")
    public void updateUserById(@PathVariable Long id){
        System.out.println("update user with id " + id);
    }
}
