package cz.upce.fei.sem_pr_backend.service;

import cz.upce.fei.sem_pr_backend.domain.ApplicationUser;
import cz.upce.fei.sem_pr_backend.repository.ApplicationUserRepository;
import org.springframework.stereotype.Service;

@Service
public class ApplicationUserServiceImpl implements ApplicationUserService {

    private final ApplicationUserRepository userRepository;

    public ApplicationUserServiceImpl(ApplicationUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public ApplicationUser save(ApplicationUser user){
        return userRepository.save(user);
    }
}
