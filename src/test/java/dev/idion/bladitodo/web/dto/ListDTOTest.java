package dev.idion.bladitodo.web.dto;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import dev.idion.bladitodo.common.error.exception.IllegalObjectFoundException;
import dev.idion.bladitodo.domain.board.Board;
import dev.idion.bladitodo.domain.list.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ListDTOTest {

  List list;
  List illegalList;
  ListDTO listDTO;

  @BeforeEach
  void setUp() {
    String name = "리스트 이름";
    list = List.builder()
        .withName(name)
        .withBoard(Board.builder().build())
        .build();
    illegalList = List.builder()
        .withName(name)
        .build();

    listDTO = ListDTO.builder()
        .withName(name)
        .build();
  }

  @Test
  @DisplayName("from 메서드 테스트")
  void fromTest() {
    ListDTO convertedList = ListDTO.from(list);

    assertThat(convertedList.getId()).isNull();
    assertThat(convertedList.getName()).isEqualTo(listDTO.getName());
    assertThat(convertedList.isArchived()).isFalse();
    assertThat(convertedList.getCards()).isEmpty();
  }

  @Test
  @DisplayName("from 메서드 수행중 예외 발생 테스트 - List는 Board가 있어야 존재할 수 있다.")
  void throwIllegalObjectExceptionTest() {
    assertThatThrownBy(() -> ListDTO.from(illegalList))
        .isInstanceOf(IllegalObjectFoundException.class)
        .hasMessage("요청을 수행하는 도중 Board에 속하지 않은 List가 발견되었습니다.");
  }
}
