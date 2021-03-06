package dev.idion.bladitodo.domain.card;

import dev.idion.bladitodo.domain.base.BaseEntity;
import dev.idion.bladitodo.domain.list.List;
import dev.idion.bladitodo.domain.user.User;
import java.time.Clock;
import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.Where;

@Getter
@ToString(callSuper = true, of = {"id", "title", "pos", "contents", "isArchived", "user", "list",
    "archivedDatetime"})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Where(clause = "is_archived <> true")
@Entity
public class Card extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String title;
  private String contents;
  private Integer pos;
  private boolean isArchived;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id")
  private User user;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "list_id")
  private List list;

  private LocalDateTime archivedDatetime;

  @Builder(setterPrefix = "with")
  private Card(String title, String contents, User user, List list) {
    this.title = title;
    this.contents = contents;
    this.user = user;
    this.list = list;
  }

  public void archiveCard(Clock clock) {
    this.pos = null;
    this.isArchived = true;
    this.archivedDatetime = LocalDateTime.now(clock);
  }

  public void setList(List list) {
    if (this.list != null) {
      this.list.getCards().remove(this);
    }
    this.list = list;

    if (this.list != null && !this.list.getCards().contains(this)) {
      this.list.getCards().add(this);
    }
  }

  public void setList(List list, int pos) {
    if (this.list != null) {
      this.list.getCards().remove(this);
    }
    this.list = list;

    if (this.list != null) {
      this.list.getCards().set(pos, this);
    }
  }

  public void setUser(User user) {
    this.user = user;
  }

  public void updateTitleAndContents(String title, String contents) {
    this.title = title;
    this.contents = contents;
  }

  public void moveCardTo(List destinationList) {
    this.setList(destinationList);
  }

  @PrePersist
  private void prepareIndex() {
    if (this.list != null) {
      this.pos = this.list.getCards().indexOf(this);
    }
  }
}
