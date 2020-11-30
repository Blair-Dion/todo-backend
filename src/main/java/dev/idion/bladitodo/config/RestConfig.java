package dev.idion.bladitodo.config;

import static java.nio.charset.StandardCharsets.UTF_8;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class RestConfig implements WebMvcConfigurer {

  @Bean
  public Jackson2ObjectMapperBuilder jacksonObjectMapperBuilder() {
    Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder();
    builder.propertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
    return builder;
  }

  // MVC Test에서 한글 깨짐 현상을 방지하는 설정
  @Override
  public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
    converters.stream()
        .filter(converter -> converter instanceof MappingJackson2HttpMessageConverter)
        .findFirst()
        .ifPresent(
            converter ->
                ((MappingJackson2HttpMessageConverter) converter).setDefaultCharset(UTF_8));
  }

  // CORS Issue를 해결하는 설정
  @Override
  public void addCorsMappings(CorsRegistry registry) {
    registry.addMapping("/**")
        .allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE")
        .allowedOrigins("http://localhost:3000");
  }
}
