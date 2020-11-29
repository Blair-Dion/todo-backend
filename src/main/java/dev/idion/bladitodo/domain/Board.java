package dev.idion.bladitodo.domain;

import java.util.ArrayList;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class Board {

  private Long id;
  private String name;
  private final java.util.List<List> lists = new ArrayList<>();
  private final java.util.List<Log> logs = new ArrayList<>();

  @Builder(setterPrefix = "with")
  private Board(Long id, String name) {
    this.id = id;
    this.name = name;
  }

  public void addList(List list) {
    this.lists.add(list);
  }

  public void addLog(Log log) {
    this.logs.add(log);
  }
}
