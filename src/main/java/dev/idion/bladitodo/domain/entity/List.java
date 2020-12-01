package dev.idion.bladitodo.domain.entity;

import dev.idion.bladitodo.domain.board.Board;
import java.time.LocalDateTime;
import java.util.ArrayList;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString(of = {"id", "name", "isArchived", "board", "archivedDatetime"})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class List extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;
  private LocalDateTime archivedDatetime;
  private boolean isArchived;

  @OneToMany(mappedBy = "list")
  private final java.util.List<Card> cards = new ArrayList<>();

  @ManyToOne
  @JoinColumn(name = "board_id")
  private Board board;

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
