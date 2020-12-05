package dev.idion.bladitodo.web.dto;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class DTOContainer {

  private final String resultType;
  private final ContainableDTO result;
  private final LogDTO log;

  public DTOContainer(ContainableDTO result, LogDTO log) {
    this.resultType = result.getType();
    this.result = result;
    this.log = log;
  }
}
