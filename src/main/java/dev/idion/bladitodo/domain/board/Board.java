package dev.idion.bladitodo.domain.board;

import dev.idion.bladitodo.domain.base.BaseEntity;
import dev.idion.bladitodo.domain.list.List;
import dev.idion.bladitodo.domain.log.Log;
import java.util.ArrayList;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString(callSuper = true, of = {"id", "name"})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Board extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;

  @OneToMany(mappedBy = "board")
  private final java.util.List<List> lists = new ArrayList<>();

  @OneToMany(mappedBy = "board")
  private final java.util.List<Log> logs = new ArrayList<>();

  @Builder(setterPrefix = "with")
  private Board(String name) {
    this.name = name;
  }
}
