package com.cloudbank.repository;

import com.cloudbank.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * User repository.
 *
 * @author Vitalii Abramov
 */
@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    /**
     * Get user by login
     *
     * @param login user login
     * @return the user entity
     */
    //@Query("select u from UserEntity u where u.login = :login")
    UserEntity getUserEntityByLogin(String login);

    /**
     * Get user by id.
     *
     * @param userId user id
     * @return       the user entity
     */
    UserEntity getUserEntityById(Long userId);

    /**
     * Get list of users
     *
     * @return the users list
     */
    List<UserEntity> getAllBy();
}
