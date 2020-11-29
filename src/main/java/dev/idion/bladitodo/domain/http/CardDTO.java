package dev.idion.bladitodo.domain.http;

import dev.idion.bladitodo.domain.Card;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CardDTO {

  private Long id;
  private String title;
  private String contents;
  private boolean isArchived;
  private Long userDbId;
  private String userId;
  private Long listId;
  private LocalDateTime archivedDatetime;

  @Builder(setterPrefix = "with")
  private CardDTO(Long id, String title, String contents, boolean isArchived, Long userDbId,
      String userId, Long listId, LocalDateTime archivedDatetime) {
    this.id = id;
    this.title = title;
    this.contents = contents;
    this.isArchived = isArchived;
    this.userDbId = userDbId;
    this.userId = userId;
    this.listId = listId;
    this.archivedDatetime = archivedDatetime;
  }

  public static CardDTO from(Card card) {
    return CardDTO.builder()
        .withId(card.getId())
        .withTitle(card.getTitle())
        .withContents(card.getContents())
        .withIsArchived(card.isArchived())
        .withUserDbId(card.getUser().getId())
        .withUserId(card.getUser().getUserId())
        .withListId(card.getList().getId())
        .withArchivedDatetime(card.getArchivedDatetime())
        .build();
  }
}
