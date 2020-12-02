package dev.idion.bladitodo.domain.user;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
class UserRepositoryTest {

  @Autowired
  private UserRepository userRepository;

  User user;

  @BeforeEach
  void setUp() {
    this.user = User.builder()
        .withUserId("blair0404")
        .withUserNickname("Blair")
        .withProfileImageUrl("none")
        .withGithubToken("token")
        .build();
  }

  @AfterEach
  void cleanUp() {
    userRepository.deleteAll();
  }

  @Test
  void 주입된_빈_null_확인_테스트() {
    assertThat(userRepository).isNotNull();
  }

  @Test
  void user_추가_테스트() {
    // given
    final long beforeCount = userRepository.count();

    // when
    User user1 = userRepository.save(this.user);

    // then
    assertThat(user1.getId()).isNotNull();
    assertThat(userRepository.count()).isEqualTo(beforeCount + 1);
  }

  @Test
  void user_조회_테스트() {
    User user1 = userRepository.save(this.user);
    User user2 = userRepository.findById(user1.getId()).orElseThrow(RuntimeException::new);

    assertThat(user2).isEqualTo(user1);
  }

  @Test
  void user_삭제_테스트() {
    // given
    final long beforeCount = userRepository.count();

    // when
    User user1 = userRepository.save(this.user);
    userRepository.delete(user1);

    // then
    assertThat(userRepository.count()).isEqualTo(beforeCount);
  }
}
