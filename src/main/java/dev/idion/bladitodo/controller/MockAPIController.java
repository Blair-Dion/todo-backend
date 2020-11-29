package dev.idion.bladitodo.controller;

import dev.idion.bladitodo.domain.Board;
import dev.idion.bladitodo.domain.Card;
import dev.idion.bladitodo.domain.List;
import dev.idion.bladitodo.domain.User;
import dev.idion.bladitodo.domain.http.BoardDTO;
import dev.idion.bladitodo.domain.http.BoardWithListIdResponse;
import dev.idion.bladitodo.domain.http.ListDTO;
import dev.idion.bladitodo.domain.http.UserDTO;
import javax.annotation.PostConstruct;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/mock")
@RestController
public class MockAPIController {

  private Board board;

  @PostConstruct
  private void makeDefaultBoard() {
    this.board = makeBoard(1L);
  }

  @GetMapping("/v1/board/{boardId}")
  public BoardWithListIdResponse getBoardWithListIdOf(@PathVariable Long boardId) {
    this.board = makeBoard(boardId);

    return BoardWithListIdResponse.from(this.board);
  }

  @GetMapping("/v2/board/{boardId}")
  public BoardDTO getBoardDTO(@PathVariable Long boardId) {
    this.board = makeBoard(boardId);

    return BoardDTO.from(this.board);
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

  @GetMapping("/v1/list/{listId}")
  public ListDTO getListDTO(@PathVariable Long listId) {
    java.util.List<List> lists = this.board.getLists();

    if (listId > lists.size()) {
      throw new IndexOutOfBoundsException("숫자가 너무 커요!");
    }

    List list = lists.get(listId.intValue() - 1);
    return ListDTO.from(list);
  }

  private Board makeBoard(Long boardId) {
    User user = User.builder()
        .withId(1L)
        .withUserId("ksundong")
        .build();

    Board manualBoard = Board.builder()
        .withId(boardId)
        .withName("테스트 보드")
        .build();

    List list1 = List.builder().withId(1L).withName("TODO").withBoard(manualBoard).build();
    List list2 = List.builder().withId(2L).withName("DOING").withBoard(manualBoard).build();
    List list3 = List.builder().withId(3L).withName("DONE").withBoard(manualBoard).build();

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

    list1.setBoard(manualBoard);
    list2.setBoard(manualBoard);
    list3.setBoard(manualBoard);

    return manualBoard;
  }
}
