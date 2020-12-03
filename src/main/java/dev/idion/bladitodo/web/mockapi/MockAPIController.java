package dev.idion.bladitodo.web.mockapi;

import dev.idion.bladitodo.web.dto.BoardDTO;
import dev.idion.bladitodo.web.dto.BoardWithListIdResponse;
import dev.idion.bladitodo.web.dto.CardDTO;
import dev.idion.bladitodo.web.dto.ListDTO;
import dev.idion.bladitodo.web.dto.UserDTO;
import java.util.ArrayList;
import java.util.Arrays;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/mock")
@RestController
public class MockAPIController {

  private BoardDTO board;

  @GetMapping("/v1/board/{boardId}")
  public BoardWithListIdResponse getBoardWithListIdOf(@PathVariable Long boardId) {
    this.board = makeBoard(boardId);

    return BoardWithListIdResponse.builder()
        .withBoardId(boardId)
        .withName("테스트 보드")
        .withListIds(Arrays.asList(1L, 2L, 3L))
        .build();
  }

  @GetMapping("/v2/board/{boardId}")
  public BoardDTO getBoardDTO(@PathVariable Long boardId) {
    this.board = makeBoard(boardId);

    return this.board;
  }

  @GetMapping("/v1/user/{userId}")
  public UserDTO getUserDTO(@PathVariable Long userId) {
    return UserDTO.builder()
        .withId(userId)
        .withProfileImageUrl(
            "https://avatars0.githubusercontent.com/u/38597469?s=88&u=4dec19ec378bfb64c9b4a00c4d63e7805dac9c6c&v=4")
        .withUserId("ksundong")
        .withUserNickname("Dion")
        .build();
  }

  @GetMapping("/v1/list/{listId}")
  public ListDTO getListDTO(@PathVariable Long listId) {
    this.board = makeBoard(1L);
    java.util.List<ListDTO> lists = this.board.getLists();

    if (listId > lists.size()) {
      throw new IndexOutOfBoundsException("숫자가 너무 커요!");
    }

    return lists.get(listId.intValue() - 1);
  }

  private BoardDTO makeBoard(Long boardId) {
    UserDTO user = UserDTO.builder()
        .withId(1L)
        .withUserId("ksundong")
        .withProfileImageUrl(
            "https://avatars0.githubusercontent.com/u/38597469?s=88&u=4dec19ec378bfb64c9b4a00c4d63e7805dac9c6c&v=4")
        .build();

    BoardDTO manualBoard = BoardDTO.builder()
        .withId(boardId)
        .withName("테스트 보드")
        .withLists(new ArrayList<>())
        .build();

    ListDTO list1 = ListDTO.builder().withId(1L).withName("TODO").withBoardId(manualBoard.getId())
        .withCards(new ArrayList<>()).build();
    ListDTO list2 = ListDTO.builder().withId(2L).withName("DOING").withBoardId(manualBoard.getId())
        .withCards(new ArrayList<>()).build();
    ListDTO list3 = ListDTO.builder().withId(3L).withName("DONE").withBoardId(manualBoard.getId())
        .withCards(new ArrayList<>()).build();

    CardDTO card1 = CardDTO.builder()
        .withId(1L)
        .withTitle("API 문서 작성하기")
        .withContents("mock API라도 문서는 필요하잖아요!")
        .withListId(list1.getId())
        .withUserId(user.getUserId())
        .withProfileImageUrl(user.getProfileImageUrl())
        .build();
    CardDTO card2 = CardDTO.builder()
        .withId(2L)
        .withTitle("mock 작성하기")
        .withContents("mock API 잘 만들기")
        .withListId(list2.getId())
        .withUserId(user.getUserId())
        .withProfileImageUrl(user.getProfileImageUrl())
        .build();
    CardDTO card3 = CardDTO.builder()
        .withId(3L)
        .withTitle("환경설정 하기")
        .withContents("환경설정 잘 하기")
        .withListId(list3.getId())
        .withUserId(user.getUserId())
        .withProfileImageUrl(user.getProfileImageUrl())
        .build();

    list1.getCards().add(card1);
    list2.getCards().add(card2);
    list3.getCards().add(card3);

    manualBoard.getLists().add(list1);
    manualBoard.getLists().add(list2);
    manualBoard.getLists().add(list3);

    return manualBoard;
  }
}
