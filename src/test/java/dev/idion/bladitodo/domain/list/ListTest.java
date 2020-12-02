package dev.idion.bladitodo.domain.list;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class ListTest {

  @Test
  void List_생성_테스트() {
    //given
    String listName = "리스트";
    List list = List.builder()
        .withName(listName)
        .build();
    //when

    //then
    assertThat(list.getName()).isEqualTo(listName);
    assertThat(list.isArchived()).isFalse();
    assertThat(list.getArchivedDatetime()).isNull();
  }
}
