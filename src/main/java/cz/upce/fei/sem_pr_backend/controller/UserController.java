package cz.upce.fei.sem_pr_backend.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    @GetMapping("/")
    public void getUsers(){

    }

    @GetMapping("/{id}")
    public void getUserById(@PathVariable Long id){

    }

    @GetMapping("/{username}")
    public void getIssueByUsername(@PathVariable String username){

    }

    @DeleteMapping("/delete/{id}")
    public void deleteUserById(@PathVariable Long id){

    }

    @PutMapping("/update/{id}")
    public void updateUserById(@PathVariable Long id){

    }
}
