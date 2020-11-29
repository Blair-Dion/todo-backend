package dev.idion.bladitodo.domain;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import dev.idion.bladitodo.domain.type.LogType;
import lombok.Getter;

@Getter
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class Log {

  private Long id;
  private LogType type;
  private String beforeContents;
  private String afterContents;
  private Long fromListId;
  private Long toListId;
  private Board board;
}
