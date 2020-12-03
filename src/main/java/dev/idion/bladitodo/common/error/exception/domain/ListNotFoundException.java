package dev.idion.bladitodo.common.error.exception.domain;

import dev.idion.bladitodo.common.error.ErrorCode;
import dev.idion.bladitodo.common.error.exception.EntityNotFoundException;

public class ListNotFoundException extends EntityNotFoundException {

  public ListNotFoundException() {
    super(ErrorCode.LIST_NOT_FOUND);
  }

  public ListNotFoundException(String message) {
    super(message, ErrorCode.LIST_NOT_FOUND);
  }
}
