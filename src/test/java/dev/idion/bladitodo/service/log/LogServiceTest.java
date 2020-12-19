package dev.idion.bladitodo.service.log;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;

import dev.idion.bladitodo.common.error.ErrorCode;
import dev.idion.bladitodo.common.error.exception.domain.BoardNotFoundException;
import dev.idion.bladitodo.domain.log.Log;
import dev.idion.bladitodo.domain.log.LogRepository;
import dev.idion.bladitodo.domain.log.LogType;
import dev.idion.bladitodo.web.dto.LogDTO;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class LogServiceTest {

  @Mock
  LogRepository logRepository;

  @InjectMocks
  LogService logService;

  List<Log> logs;
  List<LogDTO> logDTOs;

  long existBoardId = 1L;
  long notExistBoardId = 12345678987654L;

  @BeforeEach
  void setUp() {
    String title = "카드 제목";
    String contents = "카드 내용";
    long listId = 1L;

    logs = Collections.singletonList(
        Log.builder()
            .withType(LogType.CARD_ADD)
            .withAfterTitle(title)
            .withAfterContents(contents)
            .withFromListId(listId)
            .withToListId(listId)
            .build());

    logDTOs = logs.stream().map(LogDTO::from).collect(Collectors.toList());
  }

  @Test
  @DisplayName("boardId에 해당하는 Board가 존재해서 Log가 존재하는 경우")
  void findLogsTest() {
    given(logRepository.findLogsByBoardIdOrderByIdDesc(eq(existBoardId))).willReturn(logs);

    List<LogDTO> findLogsResult = logService.findLogs(existBoardId);

    assertThat(findLogsResult).isNotEmpty().hasSize(logs.size())
        .usingRecursiveFieldByFieldElementComparator().isEqualTo(logDTOs);
  }

  @Test
  @DisplayName("boardId에 해당하는 Board가 존재하지 않는 경우")
  void findLogsFailTest() {
    given(logRepository.findLogsByBoardIdOrderByIdDesc(eq(existBoardId)))
        .willReturn(Collections.emptyList());

    assertThatThrownBy(() -> logService.findLogs(existBoardId))
        .isInstanceOf(BoardNotFoundException.class)
        .hasMessage(ErrorCode.BOARD_NOT_FOUND.getMessage());
  }
}
