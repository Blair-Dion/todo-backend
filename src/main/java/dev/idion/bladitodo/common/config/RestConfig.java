package dev.idion.bladitodo.common.config;

import static java.nio.charset.StandardCharsets.UTF_8;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.ZonedDateTimeSerializer;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class RestConfig implements WebMvcConfigurer {

  private static final String dateFormat = "yyyy-MM-dd";
  private static final String dateTimeFormat = "yyyy-MM-dd HH:mm:ss";
  private static final String timeZone = "Asia/Seoul";

  @Bean
  public Jackson2ObjectMapperBuilder jacksonObjectMapperBuilder() {
    return new Jackson2ObjectMapperBuilder()
        .propertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE)
        .simpleDateFormat(dateTimeFormat)
        .serializers(new LocalDateSerializer(DateTimeFormatter.ofPattern(dateFormat)))
        .serializers(new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(dateTimeFormat)))
        .serializers(new ZonedDateTimeSerializer(DateTimeFormatter.ofPattern(dateFormat,
            Locale.KOREA)))
        .timeZone(TimeZone.getTimeZone(timeZone));
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
