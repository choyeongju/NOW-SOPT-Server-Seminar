package com.sopt.seminar.auth.filter;

import com.sopt.seminar.auth.UserAuthentication;
import com.sopt.seminar.common.dto.ErrorMessage;
import com.sopt.seminar.common.jwt.JwtTokenProvider;
import com.sopt.seminar.exception.UnauthorizedException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import static com.sopt.seminar.common.jwt.JwtValidationType.VALID_JWT;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    protected void doFilterInternal( @NotNull HttpServletRequest request,
                                     @NotNull HttpServletResponse response,
                                     @NotNull FilterChain filterChain) throws ServletException, IOException {
        try{
            final String token = getJwtFormRequest(request);
            if(jwtTokenProvider.validateToken(token) == VALID_JWT ) {
                Long memberId = jwtTokenProvider.getUserFromJwt(token);

                UserAuthentication authentication = UserAuthentication.createUserAuthentication(memberId);
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (Exception e){
            //토큰 추출 과정에서 에러 발생 시 예외 발생시킴
            throw new UnauthorizedException(ErrorMessage.JWT_UNAUTHORIZED_EXCEPTION);
        }
        filterChain.doFilter(request, response);
    }

    //Request Header에서 Token 추출하는 메서드
    private String getJwtFormRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer")){
            return bearerToken.substring("Bearer ".length());
        }
        return null;
    }
}
