package com.sopt.seminar.auth;

import com.sopt.seminar.auth.filter.CustomAccessDeniedHandler;
import com.sopt.seminar.auth.filter.JwtAuthenticationEntryPoint;
import com.sopt.seminar.auth.filter.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.RequestCacheConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig {

    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final CustomAccessDeniedHandler customAccessDeniedHandler;

    private static final String[] AUTH_WHITE_LIST = {"/**"};

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .requestCache(RequestCacheConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(registry ->
                        registry.requestMatchers(AUTH_WHITE_LIST).permitAll()
                                .anyRequest().authenticated()
                )
                .exceptionHandling((exceptionHandling) ->
                        exceptionHandling.authenticationEntryPoint(jwtAuthenticationEntryPoint)
                                .accessDeniedHandler(customAccessDeniedHandler)
                )
                .addFilterBefore(
                        jwtAuthenticationFilter,
                        UsernamePasswordAuthenticationFilter.class)
                .getOrBuild();
    }
}
