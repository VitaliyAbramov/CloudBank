package com.cloudbank.service.impl;

import com.cloudbank.dto.SignUpUserDto;
import com.cloudbank.mapper.Mapper;
import com.cloudbank.model.UserEntity;
import com.cloudbank.model.UserName;
import com.cloudbank.repository.UserRepository;
import com.cloudbank.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementation for the user service.
 *
 * @author Vitalii Abramov
 * @since 1.1
 */
@Service
public class UserServiceImpl implements UserService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    private UserRepository userRepository;
    private Mapper<SignUpUserDto, UserEntity> signUpUserDtoToUserEntityMapper;

    public UserServiceImpl(UserRepository userRepository,
                           Mapper<SignUpUserDto, UserEntity> signUpUserDtoToUserEntityMapper) {
        this.userRepository = userRepository;
        this.signUpUserDtoToUserEntityMapper = signUpUserDtoToUserEntityMapper;
    }

    @Override
    public UserEntity getUserEntityByLogin(String login) {
        return userRepository.getUserEntityByLogin(login);
    }

    @Override
    public UserEntity saveUser(SignUpUserDto signUpUserDto) {
        UserName userName = new UserName();
        userName.setFirstName(signUpUserDto.getFirstName());
        userName.setLastName(signUpUserDto.getLastName());
        userName.setMiddleName(signUpUserDto.getMiddleName());
        UserEntity userEntity = new UserEntity(userName, signUpUserDto.getLogin(), signUpUserDto.getPassword(),
                signUpUserDto.getEmail(), signUpUserDto.getPhones(), signUpUserDto.getRoles());
        userRepository.save(userEntity);
        return userRepository.getUserEntityByLogin(userEntity.getLogin());
    }

    @Override
    public UserEntity getUserById(Long userId) {
        return userRepository.getUserEntityById(userId);
    }

    @Override
    public List<UserEntity> getUserEntities() {
        return userRepository.getAllBy();
    }
}
