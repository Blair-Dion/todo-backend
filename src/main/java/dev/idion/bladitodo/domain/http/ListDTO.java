package dev.idion.bladitodo.domain.http;

import dev.idion.bladitodo.domain.List;
import java.time.LocalDateTime;
import java.util.stream.Collectors;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ListDTO {

  private Long id;
  private String name;
  private boolean isArchived;
  private int order;
  private Long boardId;
  private LocalDateTime archivedDatetime;
  private java.util.List<CardDTO> cards;

  @Builder(setterPrefix = "with")
  private ListDTO(Long id, String name, boolean isArchived, int order, Long boardId,
      LocalDateTime archivedDatetime, java.util.List<CardDTO> cards) {
    this.id = id;
    this.name = name;
    this.isArchived = isArchived;
    this.order = order;
    this.boardId = boardId;
    this.archivedDatetime = archivedDatetime;
    this.cards = cards;
  }

  public static ListDTO from(List list) {
    return ListDTO.builder()
        .withId(list.getId())
        .withName(list.getName())
        .withIsArchived(list.isArchived())
        .withOrder(list.getOrder())
        .withBoardId(list.getBoard().getId())
        .withArchivedDatetime(list.getArchivedDatetime())
        .withCards(list.getCards()
            .stream()
            .map(CardDTO::from)
            .collect(Collectors.toList()))
        .build();
  }
}
