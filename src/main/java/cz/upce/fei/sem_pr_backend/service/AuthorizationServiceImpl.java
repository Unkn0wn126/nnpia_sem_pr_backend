package cz.upce.fei.sem_pr_backend.service;

import cz.upce.fei.sem_pr_backend.domain.ApplicationUser;
import cz.upce.fei.sem_pr_backend.domain.Role;
import cz.upce.fei.sem_pr_backend.domain.enum_type.RoleType;
import cz.upce.fei.sem_pr_backend.domain.enum_type.UserState;
import cz.upce.fei.sem_pr_backend.repository.ApplicationUserRepository;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AuthorizationServiceImpl implements AuthorizationService{
    private final ApplicationUserRepository userRepository;

    public AuthorizationServiceImpl(ApplicationUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean isAuthenticated(Principal principal) {
        if(principal == null){
            return false;
        }

        Optional<ApplicationUser> userOptional = userRepository.findByUsername(principal.getName());

        return userOptional.isPresent() && userOptional.get().getState() == UserState.ACTIVE;
    }

    @Override
    public boolean isAdmin(Principal principal) {
        if(!isAuthenticated(principal)){
            return false;
        }
        Optional<ApplicationUser> userOptional = userRepository.findByUsername(principal.getName());
        if(!userOptional.isPresent()){
            return false;
        }
        ApplicationUser user = userOptional.get();
        List<Role> roles = new ArrayList<>(user.getRoles());
        for (Role role:
                roles) {
            if (role.getType() == RoleType.ROLE_ADMIN)
                return true;
        }

        return false;
    }

    @Override
    public boolean canAlterResource(Principal principal, Long authorId) {
        if (!isAuthenticated(principal))
            return false;
        if (isAdmin(principal))
            return true;

        Optional<ApplicationUser> userOptional = userRepository.findByUsername(principal.getName());
        if(!userOptional.isPresent()){
            return false;
        }
        ApplicationUser user = userOptional.get();

        return authorId.equals(user.getId());
    }

    @Override
    public String getPrincipalName(Principal principal) {
        return principal != null ? principal.getName() : null;
    }
}
