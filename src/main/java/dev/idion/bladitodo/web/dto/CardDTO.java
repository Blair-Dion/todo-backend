package dev.idion.bladitodo.web.dto;

import dev.idion.bladitodo.common.error.exception.IllegalObjectFoundException;
import dev.idion.bladitodo.domain.card.Card;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@ToString
public class CardDTO implements ContainableDTO {

  private Long id;
  private String title;
  private String contents;
  private Integer pos;
  private boolean isArchived;
  private String userId;
  private String profileImageUrl;
  private Long listId;
  private LocalDateTime archivedDatetime;

  @Builder(setterPrefix = "with")
  private CardDTO(Long id, String title, String contents, Integer pos, boolean isArchived,
      String userId, String profileImageUrl, Long listId, LocalDateTime archivedDatetime) {
    this.id = id;
    this.title = title;
    this.contents = contents;
    this.pos = pos;
    this.isArchived = isArchived;
    this.userId = userId;
    this.profileImageUrl = profileImageUrl;
    this.listId = listId;
    this.archivedDatetime = archivedDatetime;
  }

  public static CardDTO from(Card card) {
    if (card.getList() == null) {
      log.error("List에 속하지 않은 유효하지 않은 Card 객체: {}", card);
      throw new IllegalObjectFoundException("요청을 수행하는 도중 List에 속하지 않은 Card가 발견되었습니다.");
    }
    if (card.getUser() == null) {
      log.error("User와 연결되지 않은 유효하지 않은 Card 객체: {}", card);
      throw new IllegalObjectFoundException("요청을 수행하는 도중 User와 연결되지 않은 Card가 발견되었습니다.");
    }

    return CardDTO.builder()
        .withId(card.getId())
        .withTitle(card.getTitle())
        .withContents(card.getContents())
        .withPos(card.getPos())
        .withIsArchived(card.isArchived())
        .withUserId(card.getUser().getUserId())
        .withProfileImageUrl(card.getUser().getProfileImageUrl())
        .withListId(card.getList().getId())
        .withArchivedDatetime(card.getArchivedDatetime())
        .build();
  }

  @Override
  public String dtoType() {
    return "card";
  }
}
