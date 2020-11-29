package dev.idion.bladitodo.controller;

import dev.idion.bladitodo.domain.Board;
import dev.idion.bladitodo.domain.Card;
import dev.idion.bladitodo.domain.List;
import dev.idion.bladitodo.domain.User;
import dev.idion.bladitodo.domain.http.BoardDTO;
import dev.idion.bladitodo.domain.http.BoardWithListIdResponse;
import dev.idion.bladitodo.domain.http.UserDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/mock")
@RestController
public class MockAPIController {

  @GetMapping("/v1/board/{boardId}")
  public BoardWithListIdResponse getBoardWithListIdOf(@PathVariable Long boardId) {
    Board board = makeBoard(boardId);
    return BoardWithListIdResponse.from(board);
  }

  @GetMapping("/v2/board/{boardId}")
  public BoardDTO getBoardDTO(@PathVariable Long boardId) {
    Board board = makeBoard(boardId);
    return BoardDTO.from(board);
  }

  @GetMapping("/v1/user/{userId}")
  public UserDTO getUserDTO(@PathVariable Long userId) {
    User user = User.builder()
        .withId(userId)
        .withProfileImageUrl(
            "https://avatars0.githubusercontent.com/u/38597469?s=88&u=4dec19ec378bfb64c9b4a00c4d63e7805dac9c6c&v=4")
        .withGithubToken("token")
        .withUserId("ksundong")
        .withUserNickname("Dion")
        .build();

    return UserDTO.from(user);
  }

  private Board makeBoard(Long boardId) {
    User user = User.builder()
        .withId(1L)
        .withUserId("ksundong")
        .build();

    Board board = Board.builder()
        .withId(boardId)
        .withName("테스트 보드")
        .build();

    List list1 = List.builder().withId(1L).withName("TODO").withBoard(board).build();
    List list2 = List.builder().withId(2L).withName("DOING").withBoard(board).build();
    List list3 = List.builder().withId(3L).withName("DONE").withBoard(board).build();

    Card card1 = Card.builder()
        .withId(1L)
        .withTitle("API 문서 작성하기")
        .withContents("mock API라도 문서는 필요하잖아요!")
        .withList(list1)
        .withUser(user)
        .build();
    Card card2 = Card.builder()
        .withId(2L)
        .withTitle("mock 작성하기")
        .withContents("mock API 잘 만들기")
        .withList(list2)
        .withUser(user)
        .build();
    Card card3 = Card.builder()
        .withId(3L)
        .withTitle("환경설정 하기")
        .withContents("환경설정 잘 하기")
        .withList(list3)
        .withUser(user)
        .build();

    card1.setList(list1);
    card2.setList(list2);
    card3.setList(list3);

    list1.setBoard(board);
    list2.setBoard(board);
    list3.setBoard(board);

    return board;
  }
}
