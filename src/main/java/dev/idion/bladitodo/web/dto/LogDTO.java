package dev.idion.bladitodo.web.dto;

import dev.idion.bladitodo.domain.log.Log;
import dev.idion.bladitodo.domain.log.LogType;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
public class LogDTO {

  private Long id;
  private LogType type;
  private String beforeTitle;
  private String afterTitle;
  private String beforeContents;
  private String afterContents;
  private Long fromListId;
  private Long toListId;

  @Builder(setterPrefix = "with")
  private LogDTO(Long id, LogType type, String beforeTitle, String afterTitle,
      String beforeContents, String afterContents, Long fromListId, Long toListId) {
    this.id = id;
    this.type = type;
    this.beforeTitle = beforeTitle;
    this.afterTitle = afterTitle;
    this.beforeContents = beforeContents;
    this.afterContents = afterContents;
    this.fromListId = fromListId;
    this.toListId = toListId;
  }

  public static LogDTO from(Log log) {
    return LogDTO.builder()
        .withId(log.getId())
        .withType(log.getType())
        .withBeforeTitle(log.getBeforeTitle())
        .withAfterTitle(log.getAfterTitle())
        .withBeforeContents(log.getBeforeContents())
        .withAfterContents(log.getAfterContents())
        .withFromListId(log.getFromListId())
        .withToListId(log.getToListId())
        .build();
  }
}
