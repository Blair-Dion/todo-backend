package dev.idion.bladitodo.service.board;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;

import dev.idion.bladitodo.common.error.ErrorCode;
import dev.idion.bladitodo.common.error.exception.domain.BoardNotFoundException;
import dev.idion.bladitodo.domain.board.Board;
import dev.idion.bladitodo.domain.board.BoardRepository;
import dev.idion.bladitodo.web.dto.BoardDTO;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class BoardServiceTest {

  @Mock
  BoardRepository boardRepository;

  @InjectMocks
  BoardService boardService;

  Board board;
  BoardDTO boardDTO;

  long existBoardId = 1L;
  long notExistBoardId = 12345678987654L;

  @BeforeEach
  void setUp() {
    String name = "보드 이름";
    board = Board.builder()
        .withName(name)
        .build();
    boardDTO = BoardDTO.from(board);
  }

  @Test
  @DisplayName("boardId 에 해당하는 Board가 존재하는 경우")
  void findBoardTest() {
    given(boardRepository.findByBoardId(eq(existBoardId))).willReturn(Optional.of(board));

    BoardDTO findBoardResult = boardService.findBoard(existBoardId);

    assertThat(findBoardResult).usingRecursiveComparison().isEqualTo(boardDTO);
  }

  @Test
  @DisplayName("boardId 에 해당하는 Board가 존재하지 않는 경우")
  void findBoardFailTest() {
    given(boardRepository.findByBoardId(eq(notExistBoardId))).willReturn(Optional.empty());

    assertThatThrownBy(() -> {
      boardService.findBoard(notExistBoardId);
    }).isInstanceOf(BoardNotFoundException.class)
        .hasMessage(ErrorCode.BOARD_NOT_FOUND.getMessage());
  }
}
