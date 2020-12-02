package dev.idion.bladitodo.domain.card;

import dev.idion.bladitodo.domain.base.BaseEntity;
import dev.idion.bladitodo.domain.list.List;
import dev.idion.bladitodo.domain.user.User;
import java.time.LocalDateTime;
import javax.persistence.Entity;
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
@ToString(callSuper = true, of = {"id", "title", "contents", "isArchived", "user", "list",
    "archivedDatetime"})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Card extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String title;
  private String contents;
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

  public void archiveCard() {
    this.isArchived = true;
    this.archivedDatetime = LocalDateTime.now();
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
}
