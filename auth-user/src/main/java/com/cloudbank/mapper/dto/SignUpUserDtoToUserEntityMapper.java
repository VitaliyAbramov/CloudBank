package com.cloudbank.mapper.dto;

import com.cloudbank.dto.SignUpUserDto;
import com.cloudbank.exception.MappingException;
import com.cloudbank.mapper.Mapper;
import com.cloudbank.model.UserEntity;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Component;

/**
 * Maps sign up dto to user entity mapper
 *
 * @author Vitalii Abramov
 */
@Component
public class SignUpUserDtoToUserEntityMapper implements Mapper<SignUpUserDto, UserEntity> {
    private static final String MAPPING_EXCEPTION_MESSAGE = "Can not map UserDto to UserEntity "
            + "because one or all arguments is null";

    @Override
    public UserEntity map(SignUpUserDto signUpUserDto, UserEntity userEntity) {
        if (ObjectUtils.allNotNull(signUpUserDto, userEntity)) {
//            userEntity.setFirstName(signUpUserDto.getFirstName());
//            userEntity.setLastName(signUpUserDto.getLastName());
//            userEntity.setMiddleName(signUpUserDto.getMiddleName());
            userEntity.setLogin(signUpUserDto.getLogin());
            userEntity.setPassword(signUpUserDto.getPassword());
            userEntity.setEmail(signUpUserDto.getEmail());
            userEntity.setPhones(signUpUserDto.getPhones());
            userEntity.setRoles(signUpUserDto.getRoles());
            return userEntity;
        } else {
            throw new MappingException(MAPPING_EXCEPTION_MESSAGE);
        }
    }
}
