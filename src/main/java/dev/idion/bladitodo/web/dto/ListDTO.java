package dev.idion.bladitodo.web.dto;

import dev.idion.bladitodo.common.error.exception.IllegalObjectFoundException;
import dev.idion.bladitodo.domain.list.List;
import java.time.LocalDateTime;
import java.util.stream.Collectors;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class ListDTO implements ContainableDTO {

  private Long id;
  private String name;
  private boolean isArchived;
  private Long boardId;
  private LocalDateTime archivedDatetime;
  private java.util.List<CardDTO> cards;

  @Builder(setterPrefix = "with")
  private ListDTO(Long id, String name, boolean isArchived, Long boardId,
      LocalDateTime archivedDatetime, java.util.List<CardDTO> cards) {
    this.id = id;
    this.name = name;
    this.isArchived = isArchived;
    this.boardId = boardId;
    this.archivedDatetime = archivedDatetime;
    this.cards = cards;
  }

  public static ListDTO from(List list) {
    if (list.getBoard() == null) {
      throw new IllegalObjectFoundException("요청을 수행하는 도중 Board에 속하지 않은 List가 발견되었습니다.");
    }

    return ListDTO.builder()
        .withId(list.getId())
        .withName(list.getName())
        .withIsArchived(list.isArchived())
        .withBoardId(list.getBoard().getId())
        .withArchivedDatetime(list.getArchivedDatetime())
        .withCards(list.getCards()
            .stream()
            .map(CardDTO::from)
            .collect(Collectors.toList()))
        .build();
  }

  @Override
  public String dtoType() {
    return "list";
  }
}
