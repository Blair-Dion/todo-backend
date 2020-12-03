package dev.idion.bladitodo.common.error.exception;

import dev.idion.bladitodo.common.error.ErrorCode;

public class LoginRequiredException extends BusinessException {

  public LoginRequiredException() {
    super(ErrorCode.LOGIN_REQUIRED);
  }

  public LoginRequiredException(String message) {
    super(message, ErrorCode.LOGIN_REQUIRED);
  }
}
