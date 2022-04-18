package cz.upce.fei.sem_pr_backend.service;

import cz.upce.fei.sem_pr_backend.domain.ApplicationUser;
import cz.upce.fei.sem_pr_backend.domain.Role;
import cz.upce.fei.sem_pr_backend.domain.enum_type.RoleType;
import cz.upce.fei.sem_pr_backend.dto.applicationuser.ApplicationUserCreateDto;
import cz.upce.fei.sem_pr_backend.dto.applicationuser.ApplicationUserGetDto;
import cz.upce.fei.sem_pr_backend.dto.applicationuser.ApplicationUserUpdateDto;
import cz.upce.fei.sem_pr_backend.dto.applicationuser.ApplicationUserUpdatePasswordDto;

import java.security.Principal;
import java.util.List;
import java.util.Map;

public interface ApplicationUserService {
    ApplicationUser createNormalUser(ApplicationUserCreateDto userDto);
    ApplicationUser saveUser(ApplicationUserCreateDto userDto);
    List<Role> getAllRoles();
    Role saveRole(Role role);
    void addRoleToUser(String username, RoleType roleName);
    ApplicationUserGetDto getUserByUsername(String username);
    ApplicationUserGetDto getUserById(Long id);
    void deleteUser(Principal principal, Long id);
    void updateUserInfo(Principal principal, Long id, ApplicationUserUpdateDto userUpdateDto);
    void updateUserPassword(Principal principal, Long id, ApplicationUserUpdatePasswordDto userUpdatePasswordDto);
    Map<String, Object> getAllUsers(Integer pageNumber, Integer pageSize);
    Map<String, Object> getActiveUsers(Integer pageNumber, Integer pageSize);
}
