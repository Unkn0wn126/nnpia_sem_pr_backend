package cz.upce.fei.sem_pr_backend.service;

import cz.upce.fei.sem_pr_backend.domain.ApplicationUser;
import cz.upce.fei.sem_pr_backend.domain.Profile;
import cz.upce.fei.sem_pr_backend.domain.Role;
import cz.upce.fei.sem_pr_backend.domain.enum_type.RoleType;
import cz.upce.fei.sem_pr_backend.domain.enum_type.UserState;
import cz.upce.fei.sem_pr_backend.dto.applicationuser.ApplicationUserCreateDto;
import cz.upce.fei.sem_pr_backend.repository.ApplicationUserRepository;
import cz.upce.fei.sem_pr_backend.repository.ProfileRepository;
import cz.upce.fei.sem_pr_backend.repository.RoleRepository;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ApplicationUserServiceImpl implements ApplicationUserService, UserDetailsService {

    private final ApplicationUserRepository userRepository;
    private final RoleRepository roleRepository;
    private final ProfileRepository profileRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;

    public ApplicationUserServiceImpl(ApplicationUserRepository userRepository, RoleRepository roleRepository, ProfileRepository profileRepository, PasswordEncoder passwordEncoder, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.profileRepository = profileRepository;
        this.passwordEncoder = passwordEncoder;
        this.modelMapper = modelMapper;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        ApplicationUser user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User with name " + username + " not found"));
        List<SimpleGrantedAuthority> grantedAuthorities = user.getRoles().stream().map(role -> new SimpleGrantedAuthority(role.getType().toString())).collect(Collectors.toList());
        return new User(username, user.getPassword(), grantedAuthorities);
    }

    @Override
    public ApplicationUser createNormalUser(ApplicationUserCreateDto userDto) {
        ApplicationUser user = saveUser(userDto);
        addRoleToUser(user.getUsername(), RoleType.ROLE_USER);
        return user;
    }

    @Override
    public ApplicationUser saveUser(ApplicationUserCreateDto userDto) {
        ApplicationUser user = modelMapper.map(userDto, ApplicationUser.class);
        user.setState(UserState.ACTIVE);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setProfile(null);
        ApplicationUser savedUser = userRepository.save(user);
        Profile profile = modelMapper.map(userDto.getProfile(), Profile.class);
        profile.setUser(savedUser);
        profileRepository.save(profile);

        return savedUser;
    }

    @Override
    public Role saveRole(Role role) {
        return roleRepository.save(role);
    }

    @Override
    @Transactional
    public void addRoleToUser(String username, RoleType roleName) {
        ApplicationUser user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User with name: " + username + " not found!"));
        Role role = roleRepository.findByType(roleName).orElseThrow(() -> new RuntimeException("No such role"));
        user.getRoles().add(role);
    }

    @Override
    public ApplicationUser getUser(String username) {
        return userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User with name: " + username + " not found!"));
    }

    @Override
    public List<ApplicationUser> getUsers() {
        return userRepository.findAll();
    }
}
