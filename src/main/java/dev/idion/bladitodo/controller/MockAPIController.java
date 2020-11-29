package dev.idion.bladitodo.controller;

import dev.idion.bladitodo.domain.Board;
import dev.idion.bladitodo.domain.List;
import dev.idion.bladitodo.domain.http.BoardWithListIdResponse;
import java.util.Arrays;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/mock")
@RestController
public class MockAPIController {

  @GetMapping("/board/{boardId}")
  public BoardWithListIdResponse getBoardWithListIdOf(@PathVariable Long boardId) {
    Board board = Board.builder()
        .withId(boardId)
        .withName("테스트 보드")
        .withLists(Arrays.asList(
            List.builder()
                .withId(1L)
                .withName("TODO")
                .withOrder(0)
                .build(),
            List.builder()
                .withId(2L)
                .withName("DOING")
                .withOrder(0)
                .build(),
            List.builder()
                .withId(3L)
                .withName("DONE")
                .withOrder(0)
                .build()
        ))
        .withLogs(null)
        .build();
    return BoardWithListIdResponse.from(board);
  }
}
