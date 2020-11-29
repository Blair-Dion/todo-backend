package dev.idion.bladitodo.domain;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class Card {

  private Long id;
  private String title;
  private String contents;
  private boolean isArchived = false;
  private User user;
  private List list;
  private LocalDateTime archivedDatetime = null;

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
}
