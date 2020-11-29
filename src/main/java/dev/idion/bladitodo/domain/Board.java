package dev.idion.bladitodo.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class Board {

  private Long id;
  private String name;
  private java.util.List<List> lists;
  private java.util.List<Log> logs;

  @Builder(setterPrefix = "with")
  private Board(Long id, String name, java.util.List<List> lists,
      java.util.List<Log> logs) {
    this.id = id;
    this.name = name;
    this.lists = lists;
    this.logs = logs;
  }
}
