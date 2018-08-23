package com.cloudbank.service.impl;

import com.cloudbank.model.UserEntity;
import com.cloudbank.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Implementation for user service details
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserDetailsServiceImpl.class);
    private static final String USER_NOT_EXISTS_MSG = "User not exists with such username: {}";
    private static final String ROLE_PREFIX = "ROLE_";
    private UserService userService;

    /**
     * @param userService UserService implementation
     */
    public UserDetailsServiceImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userService.getUserEntityByLogin(username);
        if (Objects.isNull(user)) {
            LOGGER.info(USER_NOT_EXISTS_MSG, username);
            throw new UsernameNotFoundException(username);
        }
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(ROLE_PREFIX + user.getRoles());
        List<SimpleGrantedAuthority> updatedAuthorities = new ArrayList<>();
        updatedAuthorities.add(authority);
        return new User(user.getLogin(), user.getPassword(), updatedAuthorities);
    }
}
