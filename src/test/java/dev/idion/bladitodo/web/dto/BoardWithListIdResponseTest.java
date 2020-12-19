package dev.idion.bladitodo.web.dto;

import static org.assertj.core.api.Assertions.assertThat;

import dev.idion.bladitodo.domain.board.Board;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class BoardWithListIdResponseTest {

  Board board;
  BoardWithListIdResponse boardWithListIdResponse;

  @BeforeEach
  void setUp() {
    String name = "보드 이름";
    board = Board.builder()
        .withName(name)
        .build();

    boardWithListIdResponse = BoardWithListIdResponse.builder()
        .withName(name)
        .build();
  }

  @Test
  @DisplayName("from 메서드 테스트")
  void fromTest() {
    BoardWithListIdResponse convertedBoard = BoardWithListIdResponse.from(board);

    assertThat(convertedBoard.getBoardId()).isNull();
    assertThat(convertedBoard.getName()).isEqualTo(boardWithListIdResponse.getName());
    assertThat(convertedBoard.getListIds()).isEmpty();
  }
}
