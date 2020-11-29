package dev.idion.bladitodo.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class List {

  private Long id;
  private String name;
  private boolean isArchived = false;
  private Board board;
  private final java.util.List<Card> cards = new ArrayList<>();
  private LocalDateTime archivedDatetime = null;

  @Builder(setterPrefix = "with")
  private List(Long id, String name, boolean isArchived, Board board) {
    this.id = id;
    this.name = name;
    this.isArchived = isArchived;
    this.board = board;
  }

  public void archiveList() {
    this.isArchived = true;
    this.archivedDatetime = LocalDateTime.now();
  }

  public void setBoard(Board board) {
    if (this.board != null) {
      this.board.getLists().remove(this);
    }
    this.board = board;

    if (this.board != null && !this.board.getLists().contains(this)) {
      this.board.getLists().add(this);
    }
  }
}
