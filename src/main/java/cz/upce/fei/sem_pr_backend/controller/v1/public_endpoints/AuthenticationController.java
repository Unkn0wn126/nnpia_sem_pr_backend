package cz.upce.fei.sem_pr_backend.controller.v1.public_endpoints;

import cz.upce.fei.sem_pr_backend.config.JwtTokenUtil;
import cz.upce.fei.sem_pr_backend.dto.applicationuser.ApplicationUserCreateDto;
import cz.upce.fei.sem_pr_backend.dto.authentication.JwtRequest;
import cz.upce.fei.sem_pr_backend.dto.authentication.JwtResponse;
import cz.upce.fei.sem_pr_backend.service.ApplicationUserService;
import cz.upce.fei.sem_pr_backend.service.ApplicationUserServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Objects;

@RestController
@RequestMapping("/api/v1")
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;

    private final JwtTokenUtil jwtTokenUtil;

    private final ApplicationUserServiceImpl userService;

    public AuthenticationController(AuthenticationManager authenticationManager,
                                    JwtTokenUtil jwtTokenUtil,
                                    ApplicationUserServiceImpl userService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenUtil = jwtTokenUtil;
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody JwtRequest loginRequest, HttpServletRequest request) throws Exception {
        authenticate(loginRequest.getUsername(), loginRequest.getPassword());

        final UserDetails userDetails =userService
                .loadUserByUsername(loginRequest.getUsername());

        final String token = jwtTokenUtil.generateToken(userDetails, request.getRequestURL().toString());
        final String refreshToken = jwtTokenUtil.generateRefreshToken(userDetails, request.getRequestURL().toString());

        return ResponseEntity.ok(new JwtResponse(token, refreshToken));
    }

    private void authenticate(String username, String password){
        Objects.requireNonNull(username);
        Objects.requireNonNull(password);

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
    }

    @PostMapping("/register")
    public void register(@Valid @RequestBody ApplicationUserCreateDto userRegisterDto){
        userService.createNormalUser(userRegisterDto);
    }
}
