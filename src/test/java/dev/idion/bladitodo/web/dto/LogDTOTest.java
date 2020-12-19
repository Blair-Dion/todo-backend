package dev.idion.bladitodo.web.dto;

import static org.assertj.core.api.Assertions.assertThat;

import dev.idion.bladitodo.domain.log.Log;
import dev.idion.bladitodo.domain.log.LogType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class LogDTOTest {

  Log log;
  LogDTO logDTO;

  @BeforeEach
  void setUp() {
    long listId = 1L;
    String title = "카드 제목";
    String contents = "카드 내용";

    log = Log.builder()
        .withType(LogType.CARD_ADD)
        .withAfterTitle(title)
        .withAfterContents(contents)
        .withFromListId(listId)
        .withToListId(listId)
        .build();

    logDTO = LogDTO.builder()
        .withType(LogType.CARD_ADD)
        .withAfterTitle(title)
        .withAfterContents(contents)
        .withFromListId(listId)
        .withToListId(listId)
        .build();
  }

  @Test
  @DisplayName("from 메서드 테스트")
  void fromTest() {
    LogDTO convertedLog = LogDTO.from(log);

    assertThat(convertedLog.getId()).isNull();
    assertThat(convertedLog.getType()).isEqualTo(logDTO.getType());
    assertThat(convertedLog.getAfterTitle()).isEqualTo(logDTO.getAfterTitle());
    assertThat(convertedLog.getAfterContents()).isEqualTo(logDTO.getAfterContents());
    assertThat(convertedLog.getFromListId()).isEqualTo(logDTO.getFromListId());
    assertThat(convertedLog.getToListId()).isEqualTo(logDTO.getToListId());
  }
}
