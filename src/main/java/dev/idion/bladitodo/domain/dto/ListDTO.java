package dev.idion.bladitodo.domain.dto;

import dev.idion.bladitodo.domain.entity.List;
import java.time.LocalDateTime;
import java.util.ArrayList;
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
  private Long boardId;
  private LocalDateTime archivedDatetime;
  private java.util.List<CardDTO> cards = new ArrayList<>();

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
}
