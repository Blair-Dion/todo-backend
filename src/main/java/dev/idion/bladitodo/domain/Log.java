package dev.idion.bladitodo.domain;

import dev.idion.bladitodo.domain.type.LogType;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class Log {

  private Long id;
  private LogType type;
  private String beforeContents;
  private String afterContents;
  private Long fromListId;
  private Long toListId;
  private Board board;

  @Builder(setterPrefix = "with")
  private Log(Long id, LogType type, String beforeContents, String afterContents,
      Long fromListId, Long toListId, Board board) {
    this.id = id;
    this.type = type;
    this.beforeContents = beforeContents;
    this.afterContents = afterContents;
    this.fromListId = fromListId;
    this.toListId = toListId;
    this.board = board;
  }
}
