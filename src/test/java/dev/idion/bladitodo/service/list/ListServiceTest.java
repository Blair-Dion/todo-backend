package dev.idion.bladitodo.service.list;

import static dev.idion.bladitodo.common.TimeConstants.ASIA_SEOUL;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;

import dev.idion.bladitodo.common.error.ErrorCode;
import dev.idion.bladitodo.common.error.exception.BadRequestException;
import dev.idion.bladitodo.common.error.exception.domain.BoardNotFoundException;
import dev.idion.bladitodo.common.error.exception.domain.ListNotFoundException;
import dev.idion.bladitodo.domain.board.Board;
import dev.idion.bladitodo.domain.board.BoardRepository;
import dev.idion.bladitodo.domain.list.List;
import dev.idion.bladitodo.domain.list.ListRepository;
import dev.idion.bladitodo.domain.log.Log;
import dev.idion.bladitodo.domain.log.LogRepository;
import dev.idion.bladitodo.domain.log.LogType;
import dev.idion.bladitodo.web.dto.DTOContainer;
import dev.idion.bladitodo.web.dto.ListDTO;
import dev.idion.bladitodo.web.dto.LogDTO;
import dev.idion.bladitodo.web.v1.list.request.ListRequest;
import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalDateTime;
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

  final static LocalDate LOCAL_DATE = LocalDate.of(2020, 12, 20);

  @InjectMocks
  ListService listService;

  @Mock
  ListRepository listRepository;

  @Mock
  BoardRepository boardRepository;

  @Mock
  LogRepository logRepository;

  @Mock
  Clock clock;

  Clock fixedClock = Clock.fixed(LOCAL_DATE.atStartOfDay(ASIA_SEOUL).toInstant(), ASIA_SEOUL);

  long existBoardId = 1L;
  long notExistBoardId = 123456789876L;
  long existListId = 1L;
  long notExistListId = 123456789876L;
  String boardName = "보드 이름";
  String listName = "리스트 이름";
  String renamedListName = "변경된 리스트 이름";

  Board board;
  ListRequest listRequest;
  ListRequest renameRequest;
  List list;

  @BeforeEach
  void setUp() {
    board = Board.builder()
        .withName(boardName)
        .build();

    listRequest = new ListRequest();
    listRequest.setName(listName);

    renameRequest = new ListRequest();
    renameRequest.setName(renamedListName);

    list = listRequest.toEntity();
    list.setBoard(board);
  }

  @Test
  @DisplayName("list 생성 성공 테스트")
  void listCreateTest() {
    ListDTO listDTO = ListDTO.from(list);

    Log listAddLog = Log.listAddLog(list);

    LogDTO logDTO = LogDTO.from(listAddLog);
    DTOContainer dtoContainer = new DTOContainer(listDTO, logDTO);

    given(boardRepository.findByBoardId(eq(existBoardId))).willReturn(Optional.of(board));
    given(listRepository.save(any(List.class))).willReturn(list);
    given(logRepository.save(any(Log.class))).willReturn(listAddLog);

    DTOContainer container = listService.createListInto(existBoardId, listRequest);

    assertThat(container).isNotNull().usingRecursiveComparison().isEqualTo(dtoContainer);

    ListDTO result = (ListDTO) container.getResult();
    assertThat(result.getName()).isEqualTo(listRequest.getName());
    assertThat(result.isArchived()).isFalse();
    assertThat(container.getLog().getType()).isEqualTo(LogType.LIST_ADD);
  }

  @Test
  @DisplayName("list 생성 실패 - Board가 존재하지 않음 테스트")
  void listCreateBoardNotFoundTest() {
    given(boardRepository.findByBoardId(eq(notExistBoardId))).willReturn(Optional.empty());

    assertThatThrownBy(() -> listService.createListInto(notExistBoardId, listRequest))
        .isInstanceOf(BoardNotFoundException.class)
        .hasMessage(ErrorCode.BOARD_NOT_FOUND.getMessage());
  }

  @Test
  @DisplayName("list 생성 실패 - Request가 Null인경우 테스트")
  void listCreateRequestNullTest() {
    assertThatThrownBy(() -> listService.createListInto(existBoardId, null))
        .isInstanceOf(BadRequestException.class)
        .hasMessage("요청이 올바르지 않습니다 ListRequest는 반드시 포함되어야 합니다.");
  }

  @Test
  @DisplayName("list 이름 변경 성공 테스트")
  void updateListNameOfTest() {
    List renamedList = renameRequest.toEntity();
    renamedList.setBoard(board);
    ListDTO renamedListDTO = ListDTO.from(renamedList);

    Log listRenameLog = Log.listRenameLog(renamedList);
    LogDTO listRenameLogDTO = LogDTO.from(listRenameLog);

    DTOContainer dtoContainer = new DTOContainer(renamedListDTO, listRenameLogDTO);

    given(boardRepository.findByBoardId(eq(existBoardId))).willReturn(Optional.of(board));
    given(listRepository.findById(eq(existListId))).willReturn(Optional.of(list));

    DTOContainer container = listService
        .updateListNameOf(existBoardId, existListId, renameRequest);

    assertThat(container).isNotNull().usingRecursiveComparison().isEqualTo(dtoContainer);

    ListDTO result = (ListDTO) container.getResult();
    assertThat(result.getName()).isEqualTo(renameRequest.getName());
    assertThat(result.isArchived()).isFalse();
    assertThat(container.getLog().getType()).isEqualTo(LogType.LIST_RENAME);
  }

  @Test
  @DisplayName("list 이름 변경 - Board가 존재하지 않음 테스트")
  void updateListNameBoardNotFoundTest() {
    given(boardRepository.findByBoardId(eq(notExistBoardId))).willReturn(Optional.empty());

    assertThatThrownBy(
        () -> listService.updateListNameOf(notExistBoardId, existListId, listRequest))
        .isInstanceOf(BoardNotFoundException.class)
        .hasMessage(ErrorCode.BOARD_NOT_FOUND.getMessage());
  }

  @Test
  @DisplayName("list 이름 변경 - List가 존재하지 않음 테스트")
  void updateListNameListNotFoundTest() {
    given(boardRepository.findByBoardId(eq(existBoardId))).willReturn(Optional.of(board));
    given(listRepository.findById(eq(notExistListId))).willReturn(Optional.empty());

    assertThatThrownBy(
        () -> listService.updateListNameOf(existBoardId, notExistListId, listRequest))
        .isInstanceOf(ListNotFoundException.class)
        .hasMessage(ErrorCode.LIST_NOT_FOUND.getMessage());
  }

  @Test
  @DisplayName("list 이름 변경 - Board에 해당 List가 존재하지 않음 테스트")
  void updateListNameBoardNotContainsListTest() {
    list.setBoard(null);

    given(boardRepository.findByBoardId(eq(existBoardId))).willReturn(Optional.of(board));
    given(listRepository.findById(eq(existListId))).willReturn(Optional.of(list));

    assertThatThrownBy(
        () -> listService.updateListNameOf(existBoardId, existListId, listRequest),
        "Board안에 List가 존재하지 않는 경우 ListNotFoundException을 반환해야한다.")
        .isInstanceOf(ListNotFoundException.class)
        .hasMessage(ErrorCode.LIST_NOT_FOUND.getMessage());
  }

  @Test
  @DisplayName("list 이름 변경 - Request가 Null인 경우 테스트")
  void updateListNameRequestNullTest() {
    assertThatThrownBy(
        () -> listService.updateListNameOf(existBoardId, existListId, null))
        .isInstanceOf(BadRequestException.class)
        .hasMessage("요청이 올바르지 않습니다 ListRequest는 반드시 포함되어야 합니다.");
  }

  @Test
  @DisplayName("list 보관 성공 테스트")
  void archiveListTest() {
    given(clock.instant()).willReturn(fixedClock.instant());
    given(clock.getZone()).willReturn(fixedClock.getZone());

    List archivedList = listRequest.toEntity();
    archivedList.setBoard(board);
    archivedList.archiveList(clock);

    Log listArchiveLog = Log.listArchiveLog(archivedList);

    DTOContainer dtoContainer = new DTOContainer(ListDTO.from(archivedList),
        LogDTO.from(listArchiveLog));

    given(boardRepository.findByBoardId(eq(existBoardId))).willReturn(Optional.of(board));
    given(listRepository.findById(eq(existListId))).willReturn(Optional.of(list));

    DTOContainer container = listService.archiveList(existBoardId, existListId);

    assertThat(container).isNotNull().usingRecursiveComparison().isEqualTo(dtoContainer);

    ListDTO result = (ListDTO) container.getResult();
    assertThat(result.getName()).isEqualTo(listRequest.getName());
    assertThat(result.isArchived()).isTrue();
    assertThat(result.getArchivedDatetime()).isEqualTo(LocalDateTime.now(fixedClock));
    assertThat(container.getLog().getType()).isEqualTo(LogType.LIST_ARCHIVE);
  }

  @Test
  @DisplayName("list 보관 실패 - Board가 존재하지 않음 테스트")
  void archiveListBoardNotFoundTest() {
    given(boardRepository.findByBoardId(eq(notExistBoardId))).willReturn(Optional.empty());

    assertThatThrownBy(() -> listService.archiveList(notExistBoardId, existListId))
        .isInstanceOf(BoardNotFoundException.class)
        .hasMessage(ErrorCode.BOARD_NOT_FOUND.getMessage());
  }

  @Test
  @DisplayName("list 보관 실패 - List가 존재하지 않음 테스트")
  void archiveListListNotFoundTest() {
    given(boardRepository.findByBoardId(eq(existBoardId))).willReturn(Optional.of(board));
    given(listRepository.findById(eq(notExistListId))).willReturn(Optional.empty());

    assertThatThrownBy(() -> listService.archiveList(existBoardId, notExistListId))
        .isInstanceOf(ListNotFoundException.class)
        .hasMessage(ErrorCode.LIST_NOT_FOUND.getMessage());
  }

  @Test
  @DisplayName("list 보관 실패 - Board에 해당 List가 존재하지 않음 테스트")
  void archiveListBoardNotContainsListTest() {
    list.setBoard(null);

    given(boardRepository.findByBoardId(eq(existBoardId))).willReturn(Optional.of(board));
    given(listRepository.findById(eq(existListId))).willReturn(Optional.of(list));

    assertThatThrownBy(
        () -> listService.archiveList(existBoardId, existListId),
        "Board안에 List가 존재하지 않는 경우 ListNotFoundException을 반환해야한다.")
        .isInstanceOf(ListNotFoundException.class)
        .hasMessage(ErrorCode.LIST_NOT_FOUND.getMessage());
  }
}
