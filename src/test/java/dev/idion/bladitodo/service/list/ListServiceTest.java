package dev.idion.bladitodo.service.list;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;

import dev.idion.bladitodo.common.error.ErrorCode;
import dev.idion.bladitodo.common.error.exception.domain.BoardNotFoundException;
import dev.idion.bladitodo.domain.board.Board;
import dev.idion.bladitodo.domain.board.BoardRepository;
import dev.idion.bladitodo.domain.list.List;
import dev.idion.bladitodo.domain.list.ListRepository;
import dev.idion.bladitodo.domain.log.Log;
import dev.idion.bladitodo.domain.log.LogRepository;
import dev.idion.bladitodo.web.dto.DTOContainer;
import dev.idion.bladitodo.web.dto.ListDTO;
import dev.idion.bladitodo.web.dto.LogDTO;
import dev.idion.bladitodo.web.v1.list.request.ListRequest;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ListServiceTest {

  @InjectMocks
  ListService listService;

  @Mock
  ListRepository listRepository;

  @Mock
  BoardRepository boardRepository;

  @Mock
  LogRepository logRepository;

  long existBoardId = 1L;
  long notExistBoardId = 123456789876L;
  long existListId = 1L;
  String boardName = "보드 이름";
  String listName = "리스트 이름";

  Board board;
  ListRequest listRequest;
  List list;
  Log listAddLog;
  ListDTO listDTO;
  LogDTO logDTO;
  DTOContainer dtoContainer;

  @BeforeEach
  void setUp() {
    board = Board.builder()
        .withName(boardName)
        .build();

    listRequest = new ListRequest();
    listRequest.setName(listName);

    list = listRequest.toEntity();
    list.setBoard(board);

    listAddLog = Log.listAddLog(list);

    listDTO = ListDTO.from(list);
    logDTO = LogDTO.from(listAddLog);
    dtoContainer = new DTOContainer(listDTO, logDTO);
  }

  @Test
  @DisplayName("list 생성 성공 테스트")
  void listCreateTest() {
    given(boardRepository.findByBoardId(eq(existBoardId))).willReturn(Optional.of(board));
    given(listRepository.save(any(List.class))).willReturn(list);
    given(logRepository.save(any(Log.class))).willReturn(listAddLog);

    DTOContainer container = listService.createListInto(existBoardId, listRequest);

    assertThat(container).isNotNull();
    assertThat(container).usingRecursiveComparison().isEqualTo(dtoContainer);
  }

  @Test
  @DisplayName("list 생성 실패 - Board가 존재하지 않음 테스트")
  void listCreateBoardNotFoundTest() {
    given(boardRepository.findByBoardId(eq(notExistBoardId))).willReturn(Optional.empty());

    assertThatThrownBy(() -> {
      listService.createListInto(notExistBoardId, listRequest);
    }).isInstanceOf(BoardNotFoundException.class)
        .hasMessage(ErrorCode.BOARD_NOT_FOUND.getMessage());
  }

  @Test
  @DisplayName("list 생성 실패 - Request가 Null인경우 테스트")
  void listCreateRequestNullTest() {
    fail("Not Implemented");
  }

  @Test
  @DisplayName("list 이름 변경 성공 테스트")
  void updateListNameOfTest() {
    fail("Not Implemented");
  }

  @Test
  @DisplayName("list 이름 변경 - Board가 존재하지 않음 테스트")
  void updateListNameBoardNotFoundTest() {
    fail("Not Implemented");
  }

  @Test
  @DisplayName("list 이름 변경 - List가 존재하지 않음 테스트")
  void updateListNameListNotFoundTest() {
    fail("Not Implemented");
  }

  @Test
  @DisplayName("list 이름 변경 - Request가 Null인 경우 테스트")
  void updateListNameRequestNullTest() {
    fail("Not Implemented");
  }

  @Test
  @DisplayName("list 보관 성공 테스트")
  void archiveListTest() {
    fail("Not Implemented");
  }

  @Test
  @DisplayName("list 보관 실패 - Board가 존재하지 않음 테스트")
  void archiveListBoardNotFoundTest() {
    fail("Not Implemented");
  }

  @Test
  @DisplayName("list 보관 실패 - List가 존재하지 않음 테스트")
  void archiveListListNotFoundTest() {
    fail("Not Implemented");
  }
}
