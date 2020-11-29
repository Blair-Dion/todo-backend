package dev.idion.bladitodo.domain;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class List {

  private Long id;
  private String name;
  private boolean isArchived;
  private int order;
  private Board board;
  private LocalDateTime archivedDateTime;
}
