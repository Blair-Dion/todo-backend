package dev.idion.bladitodo.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class User {

  private Long id;
  private String profileImageUrl;
  private String userId;
  private String userNickname;
  private String githubToken;

  @Builder(setterPrefix = "with")
  private User(Long id, String profileImageUrl, String userId, String userNickname,
      String githubToken) {
    this.id = id;
    this.profileImageUrl = profileImageUrl;
    this.userId = userId;
    this.userNickname = userNickname;
    this.githubToken = githubToken;
  }
}
