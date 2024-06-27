package com.sopt.seminar.config;

import jakarta.persistence.Column;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing //JPA가 엔티티를 감시할 수 있게 해주는 어노테이션
public class JpaAuditingConfig {
}
