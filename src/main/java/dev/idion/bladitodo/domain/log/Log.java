package dev.idion.bladitodo.domain.log;

import dev.idion.bladitodo.domain.base.BaseEntity;
import dev.idion.bladitodo.domain.board.Board;
import dev.idion.bladitodo.domain.card.Card;
import dev.idion.bladitodo.domain.list.List;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
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
@ToString(callSuper = true, of = {"id", "type", "beforeTitle", "afterTitle", "beforeContents",
    "afterContents", "fromListId", "toListId", "board"})
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

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "board_id")
  private Board board;

  @Builder(setterPrefix = "with")
  private Log(LogType type, String beforeTitle, String afterTitle, String beforeContents,
      String afterContents, Long fromListId, Long toListId, Board board) {
    this.type = type;
    this.beforeTitle = beforeTitle;
    this.afterTitle = afterTitle;
    this.beforeContents = beforeContents;
    this.afterContents = afterContents;
    this.fromListId = fromListId;
    this.toListId = toListId;
    this.board = board;
  }

  public static Log cardAddLog(Long listId, Card card, Board board) {
    return Log.builder()
        .withType(LogType.CARD_ADD)
        .withFromListId(listId)
        .withToListId(listId)
        .withAfterTitle(card.getTitle())
        .withAfterContents(card.getContents())
        .withBoard(board)
        .build();
  }

  public static Log cardUpdateLog(Long listId, String beforeTitle, String beforeContents,
      Card card) {
    return Log.builder()
        .withType(LogType.CARD_TITLE_AND_CONTENT_UPDATE)
        .withFromListId(listId)
        .withToListId(listId)
        .withBeforeTitle(beforeTitle)
        .withAfterTitle(card.getTitle())
        .withBeforeContents(beforeContents)
        .withAfterContents(card.getContents())
        .withBoard(card.getList().getBoard())
        .build();
  }

  public static Log cardArchiveLog(Long listId, String beforeTitle, String beforeContents,
      Card card) {
    return Log.builder()
        .withType(LogType.CARD_ARCHIVE)
        .withFromListId(listId)
        .withBeforeTitle(beforeTitle)
        .withBeforeContents(beforeContents)
        .withBoard(card.getList().getBoard())
        .build();
  }

  public static Log listAddLog(List list) {
    return Log.builder()
        .withType(LogType.LIST_ADD)
        .withToListId(list.getId())
        .withBoard(list.getBoard())
        .build();
  }

  public static Log listRenameLog(List list) {
    return Log.builder()
        .withType(LogType.LIST_RENAME)
        .withFromListId(list.getId())
        .withToListId(list.getId())
        .withBoard(list.getBoard())
        .build();
  }

  public static Log listArchiveLog(List list) {
    return Log.builder()
        .withType(LogType.LIST_ARCHIVE)
        .withFromListId(list.getId())
        .withBoard(list.getBoard())
        .build();
  }

  public static Log cardMoveLog(Long listId, Long destinationListId, Board board) {
    return Log.builder()
        .withType(LogType.CARD_MOVE)
        .withFromListId(listId)
        .withToListId(destinationListId)
        .withBoard(board)
        .build();
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
