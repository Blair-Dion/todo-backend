package dev.idion.bladitodo.domain.board;

import dev.idion.bladitodo.domain.entity.BaseEntity;
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
@ToString(of = {"id", "name"})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Board extends BaseEntity {

  @OneToMany(mappedBy = "board")
  private final java.util.List<List> lists = new ArrayList<>();
  @OneToMany(mappedBy = "board")
  private final java.util.List<Log> logs = new ArrayList<>();
  private String name;
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Builder(setterPrefix = "with")
  private Board(Long id, String name) {
    this.id = id;
    this.name = name;
  }
}
