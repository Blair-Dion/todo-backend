package dev.idion.bladitodo;

import static dev.idion.bladitodo.common.TimeConstants.ASIA_SEOUL;

import java.time.Clock;
import java.util.TimeZone;
import javax.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class BladiTodoApplication {

  public static void main(String[] args) {
    SpringApplication.run(BladiTodoApplication.class, args);
  }

  @PostConstruct
  public void setDefaultTimeZone() {
    TimeZone.setDefault(TimeZone.getTimeZone(ASIA_SEOUL));
  }

  @Bean
  public Clock clock() {
    return Clock.system(ASIA_SEOUL);
  }
}
