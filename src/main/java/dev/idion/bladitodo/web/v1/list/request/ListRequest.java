package dev.idion.bladitodo.web.v1.list.request;

import dev.idion.bladitodo.domain.list.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class ListRequest {

  private String name;

  public List toEntity() {
    return List.builder().withName(this.name).build();
  }
}
