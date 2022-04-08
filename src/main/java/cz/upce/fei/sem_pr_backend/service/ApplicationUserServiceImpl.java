package cz.upce.fei.sem_pr_backend.service;

import cz.upce.fei.sem_pr_backend.domain.ApplicationUser;
import cz.upce.fei.sem_pr_backend.repository.ApplicationUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class ApplicationUserServiceImpl implements ApplicationUserService {

    private final ApplicationUserRepository userRepository;

    @Autowired
    public ApplicationUserServiceImpl(ApplicationUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    public ApplicationUser save(ApplicationUser user){
        return userRepository.save(user);
    }
}
