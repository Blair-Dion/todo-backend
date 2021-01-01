package dev.idion.bladitodo.domain.list;

import dev.idion.bladitodo.common.error.exception.domain.CardNotFoundException;
import dev.idion.bladitodo.domain.base.BaseEntity;
import dev.idion.bladitodo.domain.board.Board;
import dev.idion.bladitodo.domain.card.Card;
import java.time.Clock;
import java.time.LocalDateTime;
import java.util.ArrayList;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.Where;

@Getter
@ToString(callSuper = true, of = {"id", "name", "isArchived", "board", "archivedDatetime"})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Where(clause = "is_archived <> true")
@Entity
public class List extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;
  private LocalDateTime archivedDatetime;
  private boolean isArchived;

  @OneToMany(mappedBy = "list")
  @OrderBy("pos desc")
  private final java.util.List<Card> cards = new ArrayList<>();

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "board_id")
  private Board board;

  @Builder(setterPrefix = "with")
  private List(String name, Board board) {
    this.name = name;
    this.board = board;
  }

  public void archiveList(Clock clock) {
    this.isArchived = true;
    this.archivedDatetime = LocalDateTime.now(clock);
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

  public void rename(String name) {
    this.name = name;
  }

  public void checkNotContainsCard(Card card) {
    if (!cards.contains(card)) {
      throw new CardNotFoundException();
    }
  }
}
