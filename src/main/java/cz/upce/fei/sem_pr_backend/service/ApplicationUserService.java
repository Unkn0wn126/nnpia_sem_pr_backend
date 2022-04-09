package cz.upce.fei.sem_pr_backend.service;

import cz.upce.fei.sem_pr_backend.domain.ApplicationUser;
import cz.upce.fei.sem_pr_backend.domain.Role;
import cz.upce.fei.sem_pr_backend.domain.enum_type.RoleType;
import cz.upce.fei.sem_pr_backend.dto.ApplicationUserCreateDto;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface ApplicationUserService {
    ApplicationUser createNormalUser(ApplicationUserCreateDto userDto);
    ApplicationUser saveUser(ApplicationUserCreateDto userDto);
    Role saveRole(Role role);
    void addRoleToUser(String username, RoleType roleName);
    ApplicationUser getUser(String username);
    List<ApplicationUser> getUsers();
}
