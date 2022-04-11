package cz.upce.fei.sem_pr_backend.service;

import cz.upce.fei.sem_pr_backend.domain.ApplicationUser;
import cz.upce.fei.sem_pr_backend.domain.Role;
import cz.upce.fei.sem_pr_backend.domain.enum_type.RoleType;
import cz.upce.fei.sem_pr_backend.dto.applicationuser.ApplicationUserCreateDto;
import cz.upce.fei.sem_pr_backend.dto.applicationuser.ApplicationUserGetDto;
import cz.upce.fei.sem_pr_backend.dto.applicationuser.ApplicationUserUpdateDto;
import cz.upce.fei.sem_pr_backend.dto.applicationuser.ApplicationUserUpdatePasswordDto;

import java.util.List;

public interface ApplicationUserService {
    ApplicationUser createNormalUser(ApplicationUserCreateDto userDto);
    ApplicationUser saveUser(ApplicationUserCreateDto userDto);
    Role saveRole(Role role);
    void addRoleToUser(String username, RoleType roleName);
    ApplicationUserGetDto getUserByUsername(String username);
    ApplicationUserGetDto getUserById(Long id);
    void deleteUser(String username, Long id);
    void updateUserInfo(String username, Long id, ApplicationUserUpdateDto userUpdateDto);
    void updateUserPassword(String username, Long id, ApplicationUserUpdatePasswordDto userUpdatePasswordDto);
    List<ApplicationUserGetDto> getUsers();
}
