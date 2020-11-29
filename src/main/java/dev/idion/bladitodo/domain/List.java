package dev.idion.bladitodo.domain;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class List {

  private Long id;
  private String name;
  private boolean isArchived = false;
  private int order;
  private Board board;
  private LocalDateTime archivedDatetime;
  private java.util.List<Card> cards;

  @Builder(setterPrefix = "with")
  private List(Long id, String name, boolean isArchived, int order,
      Board board, LocalDateTime archivedDatetime,
      java.util.List<Card> cards) {
    this.id = id;
    this.name = name;
    this.isArchived = isArchived;
    this.order = order;
    this.board = board;
    this.archivedDatetime = archivedDatetime;
    this.cards = cards;
  }
}
