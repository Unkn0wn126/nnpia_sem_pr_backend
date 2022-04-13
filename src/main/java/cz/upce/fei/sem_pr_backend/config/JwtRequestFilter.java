package cz.upce.fei.sem_pr_backend.config;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.auth0.jwt.interfaces.DecodedJWT;
import cz.upce.fei.sem_pr_backend.exception.ExpiredJWTTokenException;
import cz.upce.fei.sem_pr_backend.exception.InvalidJwtException;
import cz.upce.fei.sem_pr_backend.exception.MissingJWTTokenException;
import cz.upce.fei.sem_pr_backend.service.ApplicationUserService;
import cz.upce.fei.sem_pr_backend.service.ApplicationUserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;


import io.jsonwebtoken.ExpiredJwtException;

import static java.util.Arrays.stream;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    @Autowired
    private ApplicationUserServiceImpl jwtUserDetailsService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        final String requestTokenHeader = request.getHeader("Authorization");

        String username = null;
        String jwtToken = null;
        DecodedJWT decodedJWT = null;
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        // JWT Token is in the form "Bearer token". Remove Bearer word and get only the Token
        if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
            jwtToken = requestTokenHeader.substring(7);
            try {
                decodedJWT = jwtTokenUtil.getDecodedJWT(jwtToken);
                // username = jwtTokenUtil.getUsernameFromToken(jwtToken);
                username = decodedJWT.getSubject();
                String[] roles = decodedJWT.getClaim("roles").asArray(String.class);
                stream(roles).forEach(role -> {
                    authorities.add(new SimpleGrantedAuthority(role));
                });
            } catch (IllegalArgumentException e) {
                throw new MissingJWTTokenException("Unable to get JWT Token");
            } catch (ExpiredJwtException e) {
                throw new ExpiredJWTTokenException("JWT Token has expired");
            } catch (Exception e){
                throw new InvalidJwtException("Invalid JWT token");
            }
        } else {
            logger.warn("JWT Token does not begin with Bearer String");
        }

        //Once we get the token validate it.
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            UserDetails userDetails = this.jwtUserDetailsService.loadUserByUsername(username);

            // if token is valid configure Spring Security to manually set authentication
            if (/*jwtTokenUtil.validateToken(jwtToken, userDetails)*/ decodedJWT.getExpiresAt().after(new Date())) {

                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, authorities);
                usernamePasswordAuthenticationToken
                        .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                // After setting the Authentication in the context, we specify
                // that the current user is authenticated. So it passes the Spring Security Configurations successfully.
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }
        chain.doFilter(request, response);
    }

}
