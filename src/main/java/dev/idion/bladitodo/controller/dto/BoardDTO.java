package dev.idion.bladitodo.controller.dto;

import dev.idion.bladitodo.domain.entity.Board;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BoardDTO {

  private Long id;
  private String name;
  private java.util.List<ListDTO> lists = new ArrayList<>();

  @Builder(setterPrefix = "with")
  private BoardDTO(Long id, String name, List<ListDTO> lists) {
    this.id = id;
    this.name = name;
    this.lists = lists;
  }

  public static BoardDTO from(Board board) {
    return BoardDTO.builder()
        .withId(board.getId())
        .withName(board.getName())
        .withLists(board.getLists()
            .stream()
            .map(ListDTO::from)
            .collect(Collectors.toList()))
        .build();
  }
}
