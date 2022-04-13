package cz.upce.fei.sem_pr_backend.service;

import cz.upce.fei.sem_pr_backend.authorization.AuthorizationUtil;
import cz.upce.fei.sem_pr_backend.domain.*;
import cz.upce.fei.sem_pr_backend.domain.enum_type.RoleType;
import cz.upce.fei.sem_pr_backend.domain.enum_type.UserState;
import cz.upce.fei.sem_pr_backend.dto.applicationuser.ApplicationUserCreateDto;
import cz.upce.fei.sem_pr_backend.dto.applicationuser.ApplicationUserGetDto;
import cz.upce.fei.sem_pr_backend.dto.applicationuser.ApplicationUserUpdateDto;
import cz.upce.fei.sem_pr_backend.dto.applicationuser.ApplicationUserUpdatePasswordDto;
import cz.upce.fei.sem_pr_backend.dto.profile.ProfileUpdateDto;
import cz.upce.fei.sem_pr_backend.exception.ResourceNotFoundException;
import cz.upce.fei.sem_pr_backend.exception.UnauthorizedException;
import cz.upce.fei.sem_pr_backend.repository.ApplicationUserRepository;
import cz.upce.fei.sem_pr_backend.repository.ProfileRepository;
import cz.upce.fei.sem_pr_backend.repository.RoleRepository;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.BadCredentialsException;
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
    private final AuthorizationUtil authorizationUtil;

    public ApplicationUserServiceImpl(ApplicationUserRepository userRepository,
                                      RoleRepository roleRepository,
                                      ProfileRepository profileRepository,
                                      PasswordEncoder passwordEncoder,
                                      ModelMapper modelMapper,
                                      AuthorizationUtil authorizationUtil) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.profileRepository = profileRepository;
        this.passwordEncoder = passwordEncoder;
        this.modelMapper = modelMapper;
        this.authorizationUtil = authorizationUtil;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        ApplicationUser user = userRepository
                .findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User with name " + username + " not found"));
        List<SimpleGrantedAuthority> grantedAuthorities = user.getRoles()
                .stream()
                .map(role -> new SimpleGrantedAuthority(role.getType().toString()))
                .collect(Collectors.toList());
        return new User(username, user.getPassword(), grantedAuthorities);
    }

    @Override
    public ApplicationUser createNormalUser(ApplicationUserCreateDto userDto) {
        ApplicationUser user = saveUser(userDto);
        Role role = roleRepository.findByType(RoleType.ROLE_USER)
                .orElseThrow(() -> new ResourceNotFoundException("No such role of type: " + RoleType.ROLE_USER));
        user.getRoles().add(role);
        userRepository.save(user);

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
        ApplicationUser user = userRepository
                .findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User with name: " + username + " not found!"));
        Role role = roleRepository
                .findByType(roleName)
                .orElseThrow(() -> new ResourceNotFoundException("No such role with name: " + roleName));
        user.getRoles().add(role);
    }

    @Override
    public ApplicationUserGetDto getUserByUsername(String username) {
        ApplicationUser user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User with name: " + username + " not found!"));
        return modelMapper.map(user, ApplicationUserGetDto.class);
    }

    @Override
    public ApplicationUserGetDto getUserById(Long id) {
        ApplicationUser user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User with id: " + id + " not found!"));
        return modelMapper.map(user, ApplicationUserGetDto.class);
    }

    @Override
    public void deleteUser(String username, Long id) {
        ApplicationUser user = userRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No such user with id: " + id));
        if (authorizationUtil.isAdmin(username) || user.getUsername().equals(username)){
            userRepository.deleteById(id);
        }else{
            throw new UnauthorizedException("Unauthorized!");
        }
    }

    @Override
    public void updateUserInfo(String username, Long id, ApplicationUserUpdateDto userUpdateDto) {
        ApplicationUser user = userRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No such user with id: " + id));
        Profile profile = profileRepository
                .findById(user.getProfile().getId())
                .orElseThrow(() -> new RuntimeException("No such profile with id: " + id));
        if (authorizationUtil.isAdmin(username) || user.getUsername().equals(username)){
            user.setEmail(userUpdateDto.getEmail());
            ProfileUpdateDto profileUpdateDto = userUpdateDto.getProfile();
            profile.setProfilePicturePath(profileUpdateDto.getProfilePicturePath()); // TODO change to base64 and convert
            profile.setNickname(profileUpdateDto.getNickname());

            userRepository.save(user);
            profileRepository.save(profile);

        }else{
            throw new UnauthorizedException("Unauthorized!");
        }
    }

    @Override
    public void updateUserPassword(String username, Long id, ApplicationUserUpdatePasswordDto userUpdatePasswordDto) {
        ApplicationUser user = userRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No such user with id: " + id));
        if (authorizationUtil.isAdmin(username) || user.getUsername().equals(username)){
            if (!doPasswordsMatch(userUpdatePasswordDto.getOldPassword(), user.getPassword()))
                throw new BadCredentialsException("Passwords don't match");

            user.setPassword(passwordEncoder.encode(userUpdatePasswordDto.getNewPassword()));
            userRepository.save(user);
        }else{
            throw new UnauthorizedException("Unauthorized!");
        }
    }

    private boolean doPasswordsMatch(String rawPassword, String encodedPassword){
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

    @Override
    public List<ApplicationUserGetDto> getUsers() {
        return userRepository.findAll()
                .stream().map(applicationUser -> modelMapper.map(applicationUser, ApplicationUserGetDto.class))
                .collect(Collectors.toList());
    }
}
