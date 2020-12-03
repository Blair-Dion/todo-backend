package dev.idion.bladitodo.common.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
  ENTITY_NOT_FOUND(404, "COM001", " Entity Not Found"),
  INVALID_TYPE_VALUE(400, "COM002", "Wrong Type"),
  INVALID_INPUT_VALUE(400, "COM003", "Wrong InputValue"),
  METHOD_NOT_ALLOWED(405, "COM004", "Change Http Method"),
  INTERNAL_SERVER_ERROR(500, "COM005", "백엔드 개발자라 죄송합니다..."),

  // user
  USER_NOT_FOUND(404, "U001", " 해당 사용자가 존재하지 않습니다."),
  LOGIN_REQUIRED(401, "U002", " 로그인을 해주세요."),

  // domain
  BOARD_NOT_FOUND(404, "D001", "해당 보드를 찾을 수 없습니다."),
  LIST_NOT_FOUND(404, "D002", "해당 리스트를 찾을 수 없습니다."),
  CARD_NOT_FOUND(404, "D003", "해당 카드를 찾을 수 없습니다."),
  ;

  private final int status;
  private final String code;
  private final String message;
}
