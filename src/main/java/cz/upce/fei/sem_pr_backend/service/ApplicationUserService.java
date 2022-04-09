package cz.upce.fei.sem_pr_backend.service;

import cz.upce.fei.sem_pr_backend.domain.ApplicationUser;
import cz.upce.fei.sem_pr_backend.domain.Role;
import cz.upce.fei.sem_pr_backend.domain.enum_type.RoleType;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface ApplicationUserService {
    ApplicationUser createNormalUser(ApplicationUser user);
    ApplicationUser saveUser(ApplicationUser user);
    Role saveRole(Role role);
    void addRoleToUser(String username, RoleType roleName);
    ApplicationUser getUser(String username);
    List<ApplicationUser> getUsers();
}
