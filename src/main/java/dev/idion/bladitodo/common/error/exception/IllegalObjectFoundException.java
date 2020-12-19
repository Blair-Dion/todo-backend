package dev.idion.bladitodo.common.error.exception;

import dev.idion.bladitodo.common.error.ErrorCode;

public class IllegalObjectFoundException extends BusinessException {

  public IllegalObjectFoundException() {
    super(ErrorCode.ILLEGAL_OBJECT_FOUND);
  }

  public IllegalObjectFoundException(String message) {
    super(message, ErrorCode.ILLEGAL_OBJECT_FOUND);
  }
}
