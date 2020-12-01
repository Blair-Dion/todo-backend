package dev.idion.bladitodo.domain.log;

import dev.idion.bladitodo.domain.board.Board;
import dev.idion.bladitodo.domain.entity.BaseEntity;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString(of = {"id", "type", "beforeTitle", "afterTitle", "beforeContents", "afterContents",
    "fromListId", "toListId", "board"})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Log extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Enumerated(EnumType.STRING)
  private LogType type;

  private String beforeTitle;
  private String afterTitle;
  private String beforeContents;
  private String afterContents;
  private Long fromListId;
  private Long toListId;

  @ManyToOne
  @JoinColumn(name = "board_id")
  private Board board;

  @Builder(setterPrefix = "with")
  private Log(Long id, LogType type, String beforeTitle, String afterTitle, String beforeContents,
      String afterContents, Long fromListId, Long toListId, Board board) {
    this.id = id;
    this.type = type;
    this.beforeTitle = beforeTitle;
    this.afterTitle = afterTitle;
    this.beforeContents = beforeContents;
    this.afterContents = afterContents;
    this.fromListId = fromListId;
    this.toListId = toListId;
    this.board = board;
  }

  public void setBoard(Board board) {
    if (this.board != null) {
      this.board.getLogs().remove(this);
    }
    this.board = board;

    if (this.board != null && !this.board.getLogs().contains(this)) {
      this.board.getLogs().add(this);
    }
  }
}