package cz.upce.fei.sem_pr_backend;

import cz.upce.fei.sem_pr_backend.domain.ApplicationUser;
import cz.upce.fei.sem_pr_backend.domain.Role;
import cz.upce.fei.sem_pr_backend.domain.enum_type.RoleType;
import cz.upce.fei.sem_pr_backend.dto.ApplicationUserCreateDto;
import cz.upce.fei.sem_pr_backend.service.ApplicationUserService;
import org.modelmapper.ModelMapper;
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
    CommandLineRunner run(ApplicationUserService userService){
        return args -> {
            userService.saveRole(new Role(null, RoleType.ROLE_ADMIN, new HashSet<>()));
            userService.saveRole(new Role(null, RoleType.ROLE_USER, new HashSet<>()));

            userService.saveUser(new ApplicationUserCreateDto("admin", "admin@root.com", "P4ssw0rd$"));
            userService.saveUser(new ApplicationUserCreateDto("rando", "email@example.com", "P4ssw0rd$"));

            userService.addRoleToUser("admin", RoleType.ROLE_ADMIN);
            userService.addRoleToUser("rando", RoleType.ROLE_USER);
        };
    }
}
