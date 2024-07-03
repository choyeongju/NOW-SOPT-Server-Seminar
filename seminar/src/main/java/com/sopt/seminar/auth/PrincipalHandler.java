package com.sopt.seminar.auth;

import com.sopt.seminar.common.dto.ErrorMessage;
import com.sopt.seminar.exception.UnauthorizedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class PrincipalHandler {

    private static final String ANONYMOUS_USER = "anonymousUser";

    public Long getUserIdFromPrincipal() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        isPrincipalNull(principal); //익명 사용자인지 확인한다.
        return Long.valueOf(principal.toString());
    }

    public void isPrincipalNull(final Object principal) {
        if (principal.toString().equals(ANONYMOUS_USER)) {
            throw new UnauthorizedException(ErrorMessage.JWT_UNAUTHORIZED_EXCEPTION);
        }
    }
}
