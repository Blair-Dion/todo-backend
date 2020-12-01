package dev.idion.bladitodo.domain.entity;

import dev.idion.bladitodo.domain.list.List;
import java.time.LocalDateTime;
import javax.persistence.Entity;
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
@ToString(of = {"id", "title", "contents", "isArchived", "user", "list", "archivedDatetime"})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Card extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String title;
  private String contents;
  private boolean isArchived;

  @ManyToOne
  @JoinColumn(name = "user_id")
  private User user;

  @ManyToOne
  @JoinColumn(name = "list_id")
  private List list;

  private LocalDateTime archivedDatetime;

  @Builder(setterPrefix = "with")
  private Card(Long id, String title, String contents, User user, List list) {
    this.id = id;
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
