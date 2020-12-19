package dev.idion.bladitodo.web.dto;

import static org.assertj.core.api.Assertions.assertThat;

import dev.idion.bladitodo.domain.board.Board;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class BoardDTOTest {

  Board board;
  BoardDTO boardDTO;

  @BeforeEach
  void setUp() {
    String name = "보드 이름";
    board = Board.builder()
        .withName(name)
        .build();

    boardDTO = BoardDTO.builder()
        .withName(name)
        .build();
  }

  @Test
  @DisplayName("from 메서드 테스트")
  void fromTest() {
    BoardDTO convertedBoard = BoardDTO.from(board);

    assertThat(convertedBoard.getId()).isNull();
    assertThat(convertedBoard.getName()).isEqualTo(boardDTO.getName());
    assertThat(convertedBoard.getLists()).isEmpty();
  }
}
