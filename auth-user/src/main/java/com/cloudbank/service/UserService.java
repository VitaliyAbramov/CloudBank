package com.cloudbank.service;


import com.cloudbank.dto.SignUpUserDto;
import com.cloudbank.model.UserEntity;

import java.util.List;

/**
 * User service.
 *
 * @author Vitalii Abramov
 * @since 1.1
 */
public interface UserService {

    /**
     * Get user by login.
     *
     * @param login user login
     * @return the user entity
     */
    UserEntity getUserEntityByLogin(String login);

    /**
     * Save the user to db.
     *
     * @param signUpUserDto representation of user entity without id
     * @return              the user from db
     */
    UserEntity saveUser(SignUpUserDto signUpUserDto);

    /**
     * Get user by id.
     *
     * @param userId user id
     * @return       the user entity
     */
    UserEntity getUserById(Long userId);

    /**
     * Get list of user entities
     *
     * @return the users list
     */
    List<UserEntity> getUserEntities();
}
