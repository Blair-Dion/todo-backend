package dev.idion.bladitodo.common.config;

import java.util.Optional;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@Configuration
public class JpaConfig {

  // 단일 유저라서 Spring Security 적용 없이 Id를 1번으로 지정
  // TODO: 멀티유저 대응
  @Bean
  public AuditorAware<Long> auditorProvider() {
    return () -> Optional.of(1L);
  }
}
