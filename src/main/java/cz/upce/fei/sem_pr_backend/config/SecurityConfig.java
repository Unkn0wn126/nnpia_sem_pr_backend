package cz.upce.fei.sem_pr_backend.config;

import cz.upce.fei.sem_pr_backend.filter.CustomAuthenticationFilter;
import cz.upce.fei.sem_pr_backend.filter.CustomAuthorizationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        CustomAuthenticationFilter customAuthenticationFilter = new CustomAuthenticationFilter(authenticationManagerBean());
        customAuthenticationFilter.setFilterProcessesUrl("/api/v1/login");
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.authorizeRequests(authorizeRequests ->
            authorizeRequests
                    .antMatchers("/api/v1/users/**").hasAnyRole("USER", "ADMIN")
                    .antMatchers(HttpMethod.POST, "/api/v1/issues/**").hasAnyRole("USER", "ADMIN")
                    .antMatchers(HttpMethod.PUT, "/api/v1/issues/**").hasAnyRole("USER", "ADMIN")
                    .antMatchers(HttpMethod.DELETE, "/api/v1/issues/**").hasAnyRole("USER", "ADMIN")
                    .antMatchers(HttpMethod.POST, "/api/v1/comments/**").hasAnyRole("USER", "ADMIN")
                    .antMatchers(HttpMethod.PUT, "/api/v1/comments/**").hasAnyRole("USER", "ADMIN")
                    .antMatchers(HttpMethod.DELETE, "/api/v1/comments/**").hasAnyRole("USER", "ADMIN")
                    .antMatchers(HttpMethod.POST, "/**").hasAnyRole("USER", "ADMIN")
                    .antMatchers(HttpMethod.PUT, "/**").hasAnyRole("USER", "ADMIN")
                    .antMatchers(HttpMethod.DELETE, "/**").hasAnyRole("USER", "ADMIN")
                    .antMatchers("/**").permitAll()
        );
        http.addFilter(customAuthenticationFilter);
        http.addFilterBefore(new CustomAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
    }


    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}