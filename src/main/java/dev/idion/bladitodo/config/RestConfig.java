package dev.idion.bladitodo.config;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

@Configuration
public class RestConfig {

  @Bean
  public Jackson2ObjectMapperBuilder jacksonObjectMapperBuilder() {
    Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder();
    builder.propertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
    return builder;
  }
}
