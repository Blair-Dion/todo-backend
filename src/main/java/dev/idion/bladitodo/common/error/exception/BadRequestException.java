package dev.idion.bladitodo.common.error.exception;

import dev.idion.bladitodo.common.error.ErrorCode;

public class BadRequestException extends BusinessException {

  public BadRequestException() {
    super(ErrorCode.BAD_REQUEST);
  }

  public BadRequestException(String message) {
    super(message, ErrorCode.BAD_REQUEST);
  }
}
