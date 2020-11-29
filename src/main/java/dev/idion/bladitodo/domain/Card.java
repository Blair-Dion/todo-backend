package dev.idion.bladitodo.domain;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class Card {

  private Long id;
  private String title;
  private String contents;
  private boolean isArchived;
  private User user;
  private int order;
  private List list;
  private LocalDateTime archivedDatetime;
}
