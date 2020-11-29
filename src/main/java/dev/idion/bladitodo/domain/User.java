package dev.idion.bladitodo.domain;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;

@Getter
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class User {

  private Long id;
  private String profileImageUrl;
  private String userId;
  private String userName;
  private String userNickname;
  private String githubToken;
}
