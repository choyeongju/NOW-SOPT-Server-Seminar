package com.sopt.seminar.auth;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

//Spring Security에서 Username과 Password로 인증을 수행하기 위해 필요한 토큰
public class UserAuthentication extends UsernamePasswordAuthenticationToken {

    public UserAuthentication(Object principal, Object credentials, Collection<? extends GrantedAuthority> authorities) {
        super(principal, credentials, authorities);
    }

    public static UserAuthentication createUserAuthentication(Long userId) {
        return new UserAuthentication(userId, null, null);
    }
}
