package dev.idion.bladitodo.domain.user;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class UserTest {

  @Test
  void User_생성_테스트() {
    //given
    String userId = "userid";
    String userNickname = "nickname";
    String profileImageUrl = "imageurl";
    String githubToken = "token";
    User user = User.builder()
        .withUserId(userId)
        .withUserNickname(userNickname)
        .withProfileImageUrl(profileImageUrl)
        .withGithubToken(githubToken)
        .build();
    //when

    //then
    assertThat(user.getUserId()).isEqualTo(userId);
    assertThat(user.getUserNickname()).isEqualTo(userNickname);
    assertThat(user.getProfileImageUrl()).isEqualTo(profileImageUrl);
    assertThat(user.getGithubToken()).isEqualTo(githubToken);
  }
}
