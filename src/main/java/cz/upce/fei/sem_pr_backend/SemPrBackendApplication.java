package cz.upce.fei.sem_pr_backend;

import cz.upce.fei.sem_pr_backend.domain.ApplicationUser;
import cz.upce.fei.sem_pr_backend.domain.Issue;
import cz.upce.fei.sem_pr_backend.domain.Role;
import cz.upce.fei.sem_pr_backend.domain.enum_type.IssueSeverity;
import cz.upce.fei.sem_pr_backend.domain.enum_type.IssueVisibility;
import cz.upce.fei.sem_pr_backend.domain.enum_type.RoleType;
import cz.upce.fei.sem_pr_backend.dto.applicationuser.ApplicationUserCreateDto;
import cz.upce.fei.sem_pr_backend.dto.applicationuser.ApplicationUserGetDto;
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
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;

@SpringBootApplication
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
    CommandLineRunner run(ApplicationUserService userService, IssueService issueService){
        return args -> {
            userService.saveRole(new Role(null, RoleType.ROLE_ADMIN, new HashSet<>()));
            userService.saveRole(new Role(null, RoleType.ROLE_USER, new HashSet<>()));

            userService.saveUser(new ApplicationUserCreateDto("admin", "admin@root.com", "P4ssw0rd$", new ProfileCreateDto("Root", null)));
            userService.saveUser(new ApplicationUserCreateDto("rando", "email@example.com", "P4ssw0rd$", new ProfileCreateDto("Rando", null)));

            userService.addRoleToUser("admin", RoleType.ROLE_ADMIN);
            userService.addRoleToUser("rando", RoleType.ROLE_USER);

            issueService.createIssue("admin", new IssueCreateDto("It's not fucking working", "Title", IssueSeverity.LOW, IssueVisibility.PUBLIC, null));
            issueService.createIssue("rando", new IssueCreateDto("It's still not fucking working", "Title...", IssueSeverity.LOW, IssueVisibility.PUBLIC, null));
        };
    }
}
