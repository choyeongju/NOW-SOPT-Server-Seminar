package com.sopt.seminar.auth.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sopt.seminar.common.dto.ErrorMessage;
import com.sopt.seminar.common.dto.ErrorResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.Comment;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

/*
인증에 실패한 경우, Unauthorized 상태 반환해 줄 EntryPoint
 */
@Component
@RequiredArgsConstructor
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint { //CustomJwtAuthenticationEntryPoint

    //Java 객체를 JSON 형식으로 변환(직렬화) 하거나 역직렬화 하는 데 사용되는 클래스
    private final ObjectMapper objectMapper;

    @Override
    public void commence(final HttpServletRequest request,
                         final HttpServletResponse response,
                         final AuthenticationException authException)
            throws IOException, ServletException {
    }

    private void setResponse(HttpServletResponse response) throws IOException{
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");
        response.getWriter()
                .write(objectMapper.writeValueAsString(
                        ErrorResponse.of(ErrorMessage.JWT_UNAUTHORIZED_EXCEPTION.getStatus(),
                                ErrorMessage.JWT_UNAUTHORIZED_EXCEPTION.getMessage())
                ));
    }
}
