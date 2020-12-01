package dev.idion.bladitodo.domain.user;

import dev.idion.bladitodo.domain.base.BaseTimeEntity;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString(of = {"id", "profileImageUrl", "userId", "userNickname", "githubToken"})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class User extends BaseTimeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
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
