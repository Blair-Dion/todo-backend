package dev.idion.bladitodo.web.v1.card.request;

import dev.idion.bladitodo.domain.card.Card;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class CardCreateRequest {

  private String title;
  private String contents;

  public Card toEntity() {
    return Card.builder().withTitle(title).withContents(contents).build();
  }
}
