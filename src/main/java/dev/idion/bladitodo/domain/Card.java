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
  private int order;
  private List list;
  private LocalDateTime archivedDatetime;

  @Builder(setterPrefix = "with")
  private Card(Long id, String title, String contents, boolean isArchived,
      User user, int order, List list, LocalDateTime archivedDatetime) {
    this.id = id;
    this.title = title;
    this.contents = contents;
    this.isArchived = isArchived;
    this.user = user;
    this.order = order;
    this.list = list;
    this.archivedDatetime = archivedDatetime;
  }
}
