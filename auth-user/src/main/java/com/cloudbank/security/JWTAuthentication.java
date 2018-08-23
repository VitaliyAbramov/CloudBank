package com.cloudbank.security;

import com.cloudbank.constant.Constant;
import com.cloudbank.model.UserEntity;
import io.jsonwebtoken.Jwts;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import static io.jsonwebtoken.SignatureAlgorithm.HS512;

/**
 * Security utils for token.
 *
 * @author Vitalii Abramov
 * @since 1.1
 */
public class JWTAuthentication {
	private static final long EXPIRATION_TIME_ONE_HOUR = 3_600_000;

	/**
	 * Generate the token with user claims.
	 *
	 * @param userEntity user entity
	 * @return           the token for user
	 */
	public static String generateToken(UserEntity userEntity) {
		Set<String> roles = new HashSet<>();
		userEntity.getRoles().forEach(role -> roles.add(role.getRole()));

		return Jwts.builder()
				.setSubject(userEntity.getLogin())
				.claim(Constant.CLAIM_ROLE, roles)
				.setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME_ONE_HOUR))
				.signWith(HS512, Constant.SECRET)
				.compact();
	}
}
