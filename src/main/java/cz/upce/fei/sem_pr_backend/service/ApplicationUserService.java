package cz.upce.fei.sem_pr_backend.service;

import cz.upce.fei.sem_pr_backend.domain.ApplicationUser;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface ApplicationUserService {
    ApplicationUser save(ApplicationUser user);
}
