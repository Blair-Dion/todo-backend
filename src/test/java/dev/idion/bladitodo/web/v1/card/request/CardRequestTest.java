package dev.idion.bladitodo.web.v1.card.request;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import dev.idion.bladitodo.domain.card.Card;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CardRequestTest {

  String title = "카드 제목";
  String contents = "카드 내용";
  CardRequest cardRequest;
  Card card;

  @BeforeEach
  void setUp() {
    cardRequest = new CardRequest();
    cardRequest.setTitle(title);
    cardRequest.setContents(contents);

    card = Card.builder().withTitle(title).withContents(contents).build();
  }

  @Test
  @DisplayName("toEntity 메서드 테스트")
  void toEntityTest() {
    //when
    Card convertedCard = cardRequest.toEntity();

    //then
    assertAll(
        () -> assertThat(convertedCard.getTitle()).isEqualTo(card.getTitle()),
        () -> assertThat(convertedCard.getContents()).isEqualTo(card.getContents())
    );
  }
}
