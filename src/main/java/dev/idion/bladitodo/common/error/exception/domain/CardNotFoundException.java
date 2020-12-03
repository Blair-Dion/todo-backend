package dev.idion.bladitodo.common.error.exception.domain;

import dev.idion.bladitodo.common.error.ErrorCode;
import dev.idion.bladitodo.common.error.exception.EntityNotFoundException;

public class CardNotFoundException extends EntityNotFoundException {

  public CardNotFoundException() {
    super(ErrorCode.CARD_NOT_FOUND);
  }

  public CardNotFoundException(String message) {
    super(message, ErrorCode.CARD_NOT_FOUND);
  }
}
