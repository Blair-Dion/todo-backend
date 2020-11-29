package dev.idion.bladitodo.domain;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;

@Getter
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class Board {

  private Long id;
  private String name;
  private java.util.List<List> lists;
  private java.util.List<Log> logs;
}
