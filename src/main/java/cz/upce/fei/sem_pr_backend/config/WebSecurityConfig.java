package cz.upce.fei.sem_pr_backend.config;

import cz.upce.fei.sem_pr_backend.filter.CustomAuthenticationFilter;
import cz.upce.fei.sem_pr_backend.filter.CustomAuthorizationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Autowired
    private JwtRequestFilter jwtRequestFilter;

    private final UserDetailsService userDetailsService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        // configure AuthenticationManager so that it knows from where to load
        // user for matching credentials
        // Use BCryptPasswordEncoder
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
    }

    @Bean
    public InternalResourceViewResolver defaultViewResolver() {
        return new InternalResourceViewResolver();
    }


    private static final String[] AUTH_WHITELIST = {
            // -- Swagger UI v2
            "/v2/api-docs",
            "/swagger-resources",
            "/swagger-resources/**",
            "/configuration/ui",
            "/configuration/security",
            "/swagger-ui.html",
            "/webjars/**",
            // -- Swagger UI v3 (OpenAPI)
            "/v3/api-docs/**",
            "/swagger-ui/**"
            // other public endpoints of your API may be appended to this array
    };

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        CustomAuthenticationFilter customAuthenticationFilter = new CustomAuthenticationFilter(authenticationManagerBean());
        customAuthenticationFilter.setFilterProcessesUrl("/api/v1/login");
        http.cors().and().csrf().disable(); // TODO: remove
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.authorizeRequests(authorizeRequests ->
            authorizeRequests
                    .antMatchers(AUTH_WHITELIST).permitAll()
                    .antMatchers("/api/v1/login/**", "/api/v1/register/**", "/api/v1/token/refresh/**").permitAll()
                    .antMatchers(HttpMethod.GET, "/api/v1/public/**").permitAll()
                    .antMatchers("/api/v1/users/**").hasAnyAuthority("ROLE_USER", "ROLE_ADMIN")
                    .antMatchers("/api/v1/issues/**").hasAnyAuthority("ROLE_USER", "ROLE_ADMIN")
                    .antMatchers("/api/v1/comments/**").hasAnyAuthority("ROLE_USER", "ROLE_ADMIN")
                    .antMatchers(HttpMethod.POST, "/**").hasAnyAuthority("ROLE_USER", "ROLE_ADMIN")
                    .antMatchers(HttpMethod.PUT, "/**").hasAnyAuthority("ROLE_USER", "ROLE_ADMIN")
                    .antMatchers(HttpMethod.DELETE, "/**").hasAnyAuthority("ROLE_USER", "ROLE_ADMIN")
                    .antMatchers(HttpMethod.GET, "/**").permitAll()
                    .anyRequest().authenticated()
        );
        http.exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint);
        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
    }


    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
