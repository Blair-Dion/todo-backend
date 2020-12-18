package dev.idion.bladitodo.web.v1.list.request;

import static org.assertj.core.api.Assertions.assertThat;

import dev.idion.bladitodo.domain.list.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ListRequestTest {

  String name = "리스트 이름";
  ListRequest listRequest;
  List list;

  @BeforeEach
  void setUp() {
    listRequest = new ListRequest();
    listRequest.setName(name);

    list = List.builder().withName(name).build();
  }

  @Test
  @DisplayName("toEntity 메서드 테스트")
  void toEntityTest() {
    //when
    List convertedList = listRequest.toEntity();

    //then
    assertThat(convertedList.getName()).isEqualTo(list.getName());
  }
}
