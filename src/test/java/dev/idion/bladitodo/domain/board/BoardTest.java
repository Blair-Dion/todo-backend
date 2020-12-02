package dev.idion.bladitodo.domain.board;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class BoardTest {

  @Test
  void Board_생성_테스트() {
    //given
    String boardName = "Test Board";
    Board board = Board.builder()
        .withName(boardName)
        .build();

    //when

    //then
    assertThat(board.getName()).isEqualTo(boardName);
  }
}
