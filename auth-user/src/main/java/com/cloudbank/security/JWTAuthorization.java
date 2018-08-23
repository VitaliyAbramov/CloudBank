package com.cloudbank.security;

import com.cloudbank.constant.Constant;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

import static com.cloudbank.constant.Constant.HEADER_STRING;
import static com.cloudbank.constant.Constant.TOKEN_PREFIX;

/**
 * Filter which is responsible for the authorization process.
 *
 * @author Vitalii_Abramov
 * @since 1.1
 */
public class JWTAuthorization extends BasicAuthenticationFilter {
    private static final String PREFIX_SCOPE = "[";
    private static final String POSTFIX_SCOPE = "]";
    private static final String WHITE_SPACE = " ";
    private static final String EMPTY_STRING = "";
    private static final String COMMA = ",";

    JWTAuthorization(AuthenticationManager authManager) {
        super(authManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req,
                                    HttpServletResponse res,
                                    FilterChain chain) throws IOException, ServletException {
        String header = req.getHeader(HEADER_STRING);

        if (StringUtils.isEmpty(header) || !header.startsWith(TOKEN_PREFIX)) {
            chain.doFilter(req, res);
            return;
        }

        Authentication authentication = getAuthentication(header);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(req, res);
    }

    /**
     * Get the authentication token
     *
     * @param token the user token from header
     * @return the authentication token
     */
    private Authentication getAuthentication(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(Constant.SECRET)
                .parseClaimsJws(token.replace(TOKEN_PREFIX, EMPTY_STRING))
                .getBody();

        String principal = claims.getSubject();

        return new UsernamePasswordAuthenticationToken(
                principal,
                Optional.empty(),
                userAuthorities(claims)
        );
    }

    /**
     * Get the user authorities
     *
     * @param claims token claims
     * @return user authorities
     */
    private Set<GrantedAuthority> userAuthorities(Claims claims) {
        Collection userRoles = Collections.singleton(claims.get(Constant.CLAIM_ROLE));
        Set<GrantedAuthority> roles = new HashSet<>();
        userRoles.forEach(role -> roles.add(new SimpleGrantedAuthority(role.toString())));
        return getUserRoles(roles);
    }

    /**
     * Get user authorities via list of granted authorities
     *
     * @param auth list of user authorities
     * @return user authorities
     */
    private Set<GrantedAuthority> getUserRoles(Set<GrantedAuthority> auth) {
        Set<GrantedAuthority> roles = new HashSet<>();
        String[] arrRoles;
        for (GrantedAuthority grant : auth) {
            arrRoles = grant.getAuthority().split(COMMA);
            for (int i = 0; i < arrRoles.length; i++) {
                String str;
                if (i == 0) {
                    str = arrRoles[i].replace(PREFIX_SCOPE, EMPTY_STRING).replace(POSTFIX_SCOPE, EMPTY_STRING);
                    roles.add(new SimpleGrantedAuthority(str));
                } else {
                    str = arrRoles[i].replace(POSTFIX_SCOPE, EMPTY_STRING).replace(WHITE_SPACE, EMPTY_STRING);
                    roles.add(new SimpleGrantedAuthority(str));
                }
            }
        }
        return roles;
    }
}
