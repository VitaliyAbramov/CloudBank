package com.cloudbank.security;

import com.cloudbank.service.UserService;
import com.cloudbank.service.impl.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

/**
 * Configuration class for authentication and authorization the user.
 *
 * @author Vitalii_Abramov
 * @since 1.1
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private static final String SIGN_IN_ENDPOINT =    "/app/sign-in";
    private static final String SIGN_UP_ENDPOINT =    "/app/user";
    private static final String USERS_LIST_ENDPOINT = "/app/users";
    private static final String USER_ENDPOINT =       "/app/user/{id}";
    private static final String ADMIN_ROLE = "ADMIN";
    private static final String USER_ROLE =  "USER";

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private UserService userService;

    @Bean
    public UserDetailsService userDetailsService() {
        return new UserDetailsServiceImpl(userService);
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .cors()
                .and()
                .csrf().disable()
                .authorizeRequests()
                .antMatchers(permittedEndpoints()).permitAll()
                .antMatchers(adminEndpoints()).hasRole(ADMIN_ROLE)
                .anyRequest().authenticated()
                .and()
                .addFilter(new JWTAuthorization(authenticationManager()))
                .sessionManagement().sessionCreationPolicy(STATELESS);
    }

    @Override
    public void configure(AuthenticationManagerBuilder managerBuilder) throws Exception {
        managerBuilder
                .userDetailsService(userDetailsService)
                .passwordEncoder(bCryptPasswordEncoder)
                .and();
    }

    private String[] adminEndpoints() {
        return new String[]{
                USERS_LIST_ENDPOINT,
                USER_ENDPOINT
        };
    }

    private String[] permittedEndpoints() {
        return new String[]{
                SIGN_IN_ENDPOINT,
                SIGN_UP_ENDPOINT
        };
    }
}
