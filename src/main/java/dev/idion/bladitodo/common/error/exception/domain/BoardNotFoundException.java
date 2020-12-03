package dev.idion.bladitodo.common.error.exception.domain;

import dev.idion.bladitodo.common.error.ErrorCode;
import dev.idion.bladitodo.common.error.exception.EntityNotFoundException;

public class BoardNotFoundException extends EntityNotFoundException {

  public BoardNotFoundException() {
    super(ErrorCode.BOARD_NOT_FOUND);
  }

  public BoardNotFoundException(String message) {
    super(message, ErrorCode.BOARD_NOT_FOUND);
  }
}
