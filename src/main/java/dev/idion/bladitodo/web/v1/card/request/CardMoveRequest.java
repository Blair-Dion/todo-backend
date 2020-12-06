package dev.idion.bladitodo.web.v1.card.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class CardMoveRequest {

  private Long destinationListId;
}
