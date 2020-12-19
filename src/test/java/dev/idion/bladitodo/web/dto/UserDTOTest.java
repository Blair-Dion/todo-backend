package dev.idion.bladitodo.web.dto;

import static org.assertj.core.api.Assertions.assertThat;

import dev.idion.bladitodo.domain.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class UserDTOTest {

  User user;
  UserDTO userDTO;

  @BeforeEach
  void setUp() {
    String userId = "userid1234";
    String userNickname = "nick";
    String githubToken = "token";
    String profileImageUrl = "url";

    user = User.builder()
        .withUserId(userId)
        .withUserNickname(userNickname)
        .withGithubToken(githubToken)
        .withProfileImageUrl(profileImageUrl)
        .build();

    userDTO = UserDTO.builder()
        .withUserId(userId)
        .withUserNickname(userNickname)
        .withProfileImageUrl(profileImageUrl)
        .build();
  }

  @Test
  @DisplayName("from 메서드 테스트")
  void fromTest() {
    UserDTO convertedUser = UserDTO.from(user);

    assertThat(convertedUser.getId()).isNull();
    assertThat(convertedUser.getUserId()).isEqualTo(userDTO.getUserId());
    assertThat(convertedUser.getUserNickname()).isEqualTo(userDTO.getUserNickname());
    assertThat(convertedUser.getProfileImageUrl()).isEqualTo(userDTO.getProfileImageUrl());
  }
}
