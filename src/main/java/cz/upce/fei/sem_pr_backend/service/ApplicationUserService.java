package cz.upce.fei.sem_pr_backend.service;

import cz.upce.fei.sem_pr_backend.domain.ApplicationUser;
import cz.upce.fei.sem_pr_backend.domain.Role;
import cz.upce.fei.sem_pr_backend.domain.enum_type.RoleType;
import cz.upce.fei.sem_pr_backend.dto.applicationuser.*;

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
    ApplicationUserPageGetDto getAllUsers(Integer pageNumber, Integer pageSize, String direction, String... properties);
    ApplicationUserPageGetDto getActiveUsers(Integer pageNumber, Integer pageSize, String direction, String... properties);
}
