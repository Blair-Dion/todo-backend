package dev.idion.bladitodo.common.error.exception;

import dev.idion.bladitodo.common.error.ErrorCode;

public class UserNotFoundException extends EntityNotFoundException {

  public UserNotFoundException() {
    super(ErrorCode.USER_NOT_FOUND);
  }

  public UserNotFoundException(String message) {
    super(message, ErrorCode.USER_NOT_FOUND);
  }
}
