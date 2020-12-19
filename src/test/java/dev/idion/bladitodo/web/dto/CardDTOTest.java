package dev.idion.bladitodo.web.dto;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import dev.idion.bladitodo.common.error.exception.IllegalObjectFoundException;
import dev.idion.bladitodo.domain.card.Card;
import dev.idion.bladitodo.domain.list.List;
import dev.idion.bladitodo.domain.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CardDTOTest {

  Card card;
  Card illegalCardWhenListIsNull;
  Card illegalCardWhenUserIsNull;
  CardDTO cardDTO;

  @BeforeEach
  void setUp() {
    String title = "카드 제목";
    String contents = "카드 내용";
    User user = User.builder().build();
    List list = List.builder().build();
    card = Card.builder()
        .withTitle(title)
        .withContents(contents)
        .withUser(user)
        .withList(list)
        .build();
    illegalCardWhenListIsNull = Card.builder()
        .withTitle(title)
        .withContents(contents)
        .withUser(user)
        .build();
    illegalCardWhenUserIsNull = Card.builder()
        .withTitle(title)
        .withContents(contents)
        .withList(list)
        .build();

    cardDTO = CardDTO.builder()
        .withTitle(title)
        .withContents(contents)
        .build();
  }

  @Test
  @DisplayName("from 메서드 테스트")
  void fromTest() {
    CardDTO convertedCard = CardDTO.from(card);

    assertThat(convertedCard.getId()).isNull();
    assertThat(convertedCard.getTitle()).isEqualTo(cardDTO.getTitle());
    assertThat(convertedCard.getContents()).isEqualTo(cardDTO.getContents());
    assertThat(convertedCard.isArchived()).isFalse();
  }

  @Test
  @DisplayName("from 메서드 수행중 예외 발생 테스트 - Card는 List가 있어야 존재할 수 있다.")
  void throwIllegalObjectExceptionTestWhenCardIsNUll() {
    assertThatThrownBy(() -> CardDTO.from(illegalCardWhenListIsNull))
        .isInstanceOf(IllegalObjectFoundException.class)
        .hasMessage("요청을 수행하는 도중 List에 속하지 않은 Card가 발견되었습니다.");
  }

  @Test
  @DisplayName("from 메서드 수행중 예외 발생 테스트 - Card는 User가 있어야 존재할 수 있다.")
  void throwIllegalObjectExceptionTestWhenUserIsNull() {
    assertThatThrownBy(() -> CardDTO.from(illegalCardWhenUserIsNull))
        .isInstanceOf(IllegalObjectFoundException.class)
        .hasMessage("요청을 수행하는 도중 User와 연결되지 않은 Card가 발견되었습니다.");
  }
}
