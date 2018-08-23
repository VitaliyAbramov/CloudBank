package com.cloudbank.constant;

/**
 * Constants for application.
 *
 * Provides the global constants which will be use more than one time in
 * the application in the different places. It was done to allow the
 * duplication in the application code.
 *
 * @author Vitalii Abramov
 * @since 1.1
 */
public class Constant {
    private Constant(){
    }

    public static final String USER_ID_KEY = "userId";
    public static final String USER_TOKEN_KEY = "jwt";

    public static final String SECRET = "SecretKeyToGenJWTs";
    public static final String CLAIM_ROLE = "role";
    public static final String ROLE_PREFIX = "ROLE_";
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
}
