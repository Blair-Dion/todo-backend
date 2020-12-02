package dev.idion.bladitodo.domain.card;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class CardTest {

  @Test
  void Card_생성_테스트() {
    //given
    String title = "제목";
    String contents = "내용";
    Card card = Card.builder()
        .withTitle(title)
        .withContents(contents)
        .build();

    //when

    //then
    assertThat(card.getTitle()).isEqualTo(title);
    assertThat(card.getContents()).isEqualTo(contents);
    assertThat(card.isArchived()).isFalse();
    assertThat(card.getArchivedDatetime()).isNull();
  }
}
