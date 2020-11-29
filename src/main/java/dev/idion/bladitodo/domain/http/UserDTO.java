package dev.idion.bladitodo.domain.http;

import dev.idion.bladitodo.domain.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserDTO {

  private Long id;
  private String profileImageUrl;
  private String userId;
  private String userNickname;
  private String githubToken;

  @Builder(setterPrefix = "with")
  private UserDTO(Long id, String profileImageUrl, String userId, String userNickname,
      String githubToken) {
    this.id = id;
    this.profileImageUrl = profileImageUrl;
    this.userId = userId;
    this.userNickname = userNickname;
    this.githubToken = githubToken;
  }

  public static UserDTO from(User user) {
    return UserDTO.builder()
        .withId(user.getId())
        .withUserId(user.getUserId())
        .withProfileImageUrl(user.getProfileImageUrl())
        .withGithubToken(user.getGithubToken())
        .withUserNickname(user.getUserNickname())
        .build();
  }
}
