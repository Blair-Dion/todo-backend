package dev.idion.bladitodo.domain.log;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class LogTest {

  @Test
  void Log_생성_테스트() {
    //given
    String beforeTitle = "이전 제목";
    String afterTitle = "이후 제목";
    String beforeContents = "이전 내용";
    String afterContents = "이후 내용";
    long fromListId = 1L;
    long toListId = 2L;
    LogType type = LogType.CARD_ADD;
    Log log = Log.builder()
        .withBeforeTitle(beforeTitle)
        .withAfterTitle(afterTitle)
        .withBeforeContents(beforeContents)
        .withAfterContents(afterContents)
        .withFromListId(fromListId)
        .withToListId(toListId)
        .withType(type)
        .build();
    //when

    //then
    assertThat(log.getBeforeTitle()).isEqualTo(beforeTitle);
    assertThat(log.getAfterTitle()).isEqualTo(afterTitle);
    assertThat(log.getBeforeContents()).isEqualTo(beforeContents);
    assertThat(log.getAfterContents()).isEqualTo(afterContents);
    assertThat(log.getFromListId()).isEqualTo(fromListId);
    assertThat(log.getToListId()).isEqualTo(toListId);
    assertThat(log.getType()).isEqualByComparingTo(type);
  }
}
