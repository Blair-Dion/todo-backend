package dev.idion.bladitodo.web.dto;

import dev.idion.bladitodo.domain.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class UserDTO {

  private Long id;
  private String profileImageUrl;
  private String userId;
  private String userNickname;

  @Builder(setterPrefix = "with")
  private UserDTO(Long id, String profileImageUrl, String userId, String userNickname) {
    this.id = id;
    this.profileImageUrl = profileImageUrl;
    this.userId = userId;
    this.userNickname = userNickname;
  }

  public static UserDTO from(User user) {
    return UserDTO.builder()
        .withId(user.getId())
        .withUserId(user.getUserId())
        .withProfileImageUrl(user.getProfileImageUrl())
        .withUserNickname(user.getUserNickname())
        .build();
  }
}
