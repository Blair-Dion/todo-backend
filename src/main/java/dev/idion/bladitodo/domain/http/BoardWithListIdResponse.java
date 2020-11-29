package dev.idion.bladitodo.domain.http;

import dev.idion.bladitodo.domain.Board;
import dev.idion.bladitodo.domain.List;
import java.util.stream.Collectors;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BoardWithListIdResponse {

  private String name;
  private java.util.List<Long> listIds;

  @Builder(setterPrefix = "with")
  private BoardWithListIdResponse(String name, java.util.List<Long> listIds) {
    this.name = name;
    this.listIds = listIds;
  }

  public static BoardWithListIdResponse from(Board board) {
    return BoardWithListIdResponse.builder()
        .withName(board.getName())
        .withListIds(board.getLists().stream()
            .map(List::getId)
            .collect(Collectors.toList())
        )
        .build();
  }
}
