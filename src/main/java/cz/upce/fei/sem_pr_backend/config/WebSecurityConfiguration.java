package cz.upce.fei.sem_pr_backend.config;

import cz.upce.fei.sem_pr_backend.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder());
        provider.setUserDetailsService(userDetailsService);
        return provider;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests(authorizeRequests ->
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
                )
                .httpBasic().realmName("Issue realm")
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean

    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
