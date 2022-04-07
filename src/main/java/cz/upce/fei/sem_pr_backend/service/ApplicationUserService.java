package cz.upce.fei.sem_pr_backend.service;

import cz.upce.fei.sem_pr_backend.entity.ApplicationUser;
import cz.upce.fei.sem_pr_backend.repository.ApplicationUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class ApplicationUserService implements UserDetailsService {

    private final ApplicationUserRepository userRepository;

    @Autowired
    public ApplicationUserService(ApplicationUserRepository userRepository) {
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
