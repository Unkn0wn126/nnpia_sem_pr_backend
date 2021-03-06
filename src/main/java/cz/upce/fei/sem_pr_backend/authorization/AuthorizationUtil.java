package cz.upce.fei.sem_pr_backend.authorization;

import cz.upce.fei.sem_pr_backend.domain.ApplicationUser;
import cz.upce.fei.sem_pr_backend.domain.Role;
import cz.upce.fei.sem_pr_backend.domain.enum_type.RoleType;
import cz.upce.fei.sem_pr_backend.repository.ApplicationUserRepository;
import org.springframework.stereotype.Component;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class AuthorizationUtil {

    private final ApplicationUserRepository userRepository;

    public AuthorizationUtil(ApplicationUserRepository applicationUserRepository) {
        this.userRepository = applicationUserRepository;
    }

    public boolean isAdmin(String username){
        ApplicationUser user = userRepository.findByUsername(username).get();
        List<Role> roles = user.getRoles().stream().collect(Collectors.toList());
        for (Role role:
                roles) {
            if (role.getType() == RoleType.ROLE_ADMIN)
                return true;
        }

        return false;
    }

    public boolean canAlterResource(Principal principal, Long authorId){
        if (principal == null)
            return false;
        if (isAdmin(principal.getName()))
            return true;

        ApplicationUser user = userRepository.findByUsername(principal.getName()).get();
        return authorId.equals(user.getId());
    }
}
