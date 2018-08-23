package com.cloudbank.controller;

import com.cloudbank.dto.SignInUserDto;
import com.cloudbank.dto.SignUpUserDto;
import com.cloudbank.model.UserEntity;
import com.cloudbank.security.JWTAuthentication;
import com.cloudbank.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.cloudbank.constant.Constant.HEADER_STRING;
import static com.cloudbank.constant.Constant.TOKEN_PREFIX;


/**
 * User controller.
 *
 * @author Vitalii Abramov
 * @since 1.1
 * @version 1.1, ${date}
 */
@RestController
@RequestMapping("/app")
public class UserController {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);
    private static final String INVOKE_MSG = "Invoke the {} endpoint.";
    private static final String USER_TOKEN_KEY = "jwt";
    private static final String WHITE_SPACE = " ";
    private static final String EMPTY_STRING = "";

    private UserService userService;

    /**
     * Create UserController instance
     *
     * @param userService UserService implementation
     */
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Sign in user
     *
     * @param signInUserDto sign in representation
     * @param request       request from user
     * @return              the user id and user token
     */
    @PostMapping("/sign-in")
    public Map signInUser(@RequestBody SignInUserDto signInUserDto,
                             HttpServletRequest request,
                             HttpServletResponse response) {
        LOGGER.info(INVOKE_MSG, request.getRequestURL());
        UserEntity userEntity = userService.getUserEntityByLogin(signInUserDto.getLogin());
        response.addHeader(HEADER_STRING, TOKEN_PREFIX + " " +
                JWTAuthentication.generateToken(userEntity));
        Map<String, String> responseMap = new HashMap<>();
        responseMap.put(USER_TOKEN_KEY, response.getHeader(HEADER_STRING)
                .replace(TOKEN_PREFIX, EMPTY_STRING)
                .replace(WHITE_SPACE, EMPTY_STRING));
        return responseMap;
    }

    /**
     * Get the list of users
     *
     * @param request request from user
     * @return        the users list
     */
    @GetMapping("/users")
    public List<UserEntity> usersList(HttpServletRequest request) {
        LOGGER.info(INVOKE_MSG, request.getRequestURL());
        return userService.getUserEntities();
    }

    /**
     * Create and save the user.
     *
     * @param signUpUserDto representation of user entity without user id and role
     * @param request       request from user
     * @return              the saved user with full information from db
     */
    @PostMapping("/user")
    public UserEntity createUser(@RequestBody SignUpUserDto signUpUserDto,
                                 HttpServletRequest request) {
        LOGGER.info(INVOKE_MSG, request.getRequestURL());
        return userService.saveUser(signUpUserDto);
    }

    /**
     * Get the user by id.
     *
     * @param userId  user id
     * @param request request from user
     * @return        the user entity
     */
    @GetMapping("/user/{id}")
    public UserEntity getUser(@PathVariable("id") Long userId,
                              HttpServletRequest request) {
        LOGGER.info(INVOKE_MSG, request.getRequestURL());
        return userService.getUserById(userId);
    }

}
