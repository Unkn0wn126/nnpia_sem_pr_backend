package cz.upce.fei.sem_pr_backend;

import cz.upce.fei.sem_pr_backend.domain.ApplicationUser;
import cz.upce.fei.sem_pr_backend.domain.Issue;
import cz.upce.fei.sem_pr_backend.domain.Role;
import cz.upce.fei.sem_pr_backend.domain.enum_type.IssueSeverity;
import cz.upce.fei.sem_pr_backend.domain.enum_type.IssueVisibility;
import cz.upce.fei.sem_pr_backend.domain.enum_type.RoleType;
import cz.upce.fei.sem_pr_backend.dto.applicationuser.ApplicationUserCreateDto;
import cz.upce.fei.sem_pr_backend.dto.applicationuser.ApplicationUserGetDto;
import cz.upce.fei.sem_pr_backend.dto.comment.CommentCreateDto;
import cz.upce.fei.sem_pr_backend.dto.issue.IssueCreateDto;
import cz.upce.fei.sem_pr_backend.dto.issue.IssueGetDto;
import cz.upce.fei.sem_pr_backend.dto.profile.ProfileCreateDto;
import cz.upce.fei.sem_pr_backend.service.ApplicationUserService;
import cz.upce.fei.sem_pr_backend.service.IssueService;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.TypeMap;
import org.modelmapper.spi.MappingContext;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.List;
import java.util.Map;

@SpringBootApplication
@PropertySource("classpath:application-${spring.profiles.active:default}.properties")
public class SemPrBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(SemPrBackendApplication.class, args);
    }

    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    //@Profile("default")
    CommandLineRunner run(ApplicationUserService userService, @Qualifier("issueServiceImpl") IssueService issueService){
        return args -> {
            if (userService.getAllRoles().size() == 0){
                userService.saveRole(new Role(null, RoleType.ROLE_ADMIN, new HashSet<>()));
                userService.saveRole(new Role(null, RoleType.ROLE_USER, new HashSet<>()));
            }

            List<ApplicationUserGetDto> users = (List<ApplicationUserGetDto>) userService.getAllUsers(0, 1, "ASC", "id").getUsers();
            if (users.size() == 0){
                userService.saveUser(new ApplicationUserCreateDto("admin", "admin@root.com", "P4ssw0rd$", new ProfileCreateDto("Root", null)));

                userService.addRoleToUser("admin", RoleType.ROLE_USER);
                userService.addRoleToUser("admin", RoleType.ROLE_ADMIN);
            }

        };
    }
}
