package dev.idion.bladitodo.service.card;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;

import dev.idion.bladitodo.common.error.ErrorCode;
import dev.idion.bladitodo.common.error.exception.domain.BoardNotFoundException;
import dev.idion.bladitodo.common.error.exception.domain.CardNotFoundException;
import dev.idion.bladitodo.common.error.exception.domain.ListNotFoundException;
import dev.idion.bladitodo.domain.board.Board;
import dev.idion.bladitodo.domain.board.BoardRepository;
import dev.idion.bladitodo.domain.card.Card;
import dev.idion.bladitodo.domain.card.CardRepository;
import dev.idion.bladitodo.domain.list.List;
import dev.idion.bladitodo.domain.list.ListRepository;
import dev.idion.bladitodo.domain.log.Log;
import dev.idion.bladitodo.domain.log.LogRepository;
import dev.idion.bladitodo.domain.log.LogType;
import dev.idion.bladitodo.domain.user.User;
import dev.idion.bladitodo.domain.user.UserRepository;
import dev.idion.bladitodo.web.dto.CardDTO;
import dev.idion.bladitodo.web.dto.DTOContainer;
import dev.idion.bladitodo.web.dto.LogDTO;
import dev.idion.bladitodo.web.v1.card.request.CardRequest;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CardServiceTest {

  final long existBoardId = 1L;
  final long notExistBoardId = 1234567898765432L;
  final long existListId = 1L;
  final long notExistListId = 1234567898765432L;
  final long existCardId = 1L;
  final long notExistCardId = 1234567898765432L;
  final long existUserId = 1L;
  final String title = "카드 제목";
  final String contents = "카드 내용";
  final String editTitle = "수정한 제목";
  final String editContents = "수정한 내용";

  @InjectMocks
  CardService cardService;

  @Mock
  CardRepository cardRepository;

  @Mock
  UserRepository userRepository;

  @Mock
  ListRepository listRepository;

  @Mock
  BoardRepository boardRepository;

  @Mock
  LogRepository logRepository;

  Board board;
  User user;
  List list;
  CardRequest request;
  Card card;
  Log cardAddLog;
  Log cardUpdateLog;

  CardDTO cardDTO;
  LogDTO cardAddLogDTO;
  LogDTO cardUpdateLogDTO;
  DTOContainer dtoContainer;

  @BeforeEach
  void setUp() {
    board = Board.builder().build();
    user = User.builder().build();
    list = List.builder().build();
    list.setBoard(board);

    request = new CardRequest();
    request.setTitle(title);
    request.setContents(contents);

    card = request.toEntity();
    card.setList(list);
    card.setUser(user);

    cardDTO = CardDTO.from(card);
  }

  @Test
  @DisplayName("card 생성 성공 테스트")
  void createCardTest() {
    //given
    cardAddLog = Log.cardAddLog(existListId, card, board);
    cardAddLogDTO = LogDTO.from(cardAddLog);
    dtoContainer = new DTOContainer(cardDTO, cardAddLogDTO);

    given(boardRepository.findByBoardId(eq(existBoardId))).willReturn(Optional.of(board));
    given(listRepository.findById(eq(existListId))).willReturn(Optional.of(list));
    given(userRepository.findById(eq(existUserId))).willReturn(Optional.of(user));
    given(cardRepository.save(any(Card.class))).willReturn(card);

    // when
    DTOContainer container = cardService.createCardInto(existBoardId, existListId, request);
    CardDTO resultCardDTO = (CardDTO) container.getResult();

    // then
    assertThat(container).usingRecursiveComparison().isEqualTo(dtoContainer);
    assertThat(resultCardDTO.getTitle()).isEqualTo(title);
    assertThat(resultCardDTO.getContents()).isEqualTo(contents);
    assertThat(container.getLog().getType()).isEqualTo(LogType.CARD_ADD);
  }

  @Test
  @DisplayName("card 생성 실패 - Board가 존재하지 않음 테스트")
  void createCardBoardNotFoundTest() {
    //given
    given(boardRepository.findByBoardId(eq(notExistBoardId))).willReturn(Optional.empty());

    //when
    //then
    assertThatThrownBy(() -> cardService.createCardInto(notExistBoardId, existListId, request))
        .isInstanceOf(BoardNotFoundException.class)
        .hasMessage(ErrorCode.BOARD_NOT_FOUND.getMessage());
  }

  @Test
  @DisplayName("card 생성 실패 - List가 존재하지 않음 테스트")
  void createCardListNotFoundTest() {
    //given
    given(boardRepository.findByBoardId(eq(existBoardId))).willReturn(Optional.of(board));
    given(listRepository.findById(eq(notExistListId))).willReturn(Optional.empty());

    //when
    //then
    assertThatThrownBy(() -> cardService.createCardInto(existBoardId, notExistListId, request))
        .isInstanceOf(ListNotFoundException.class)
        .hasMessage(ErrorCode.LIST_NOT_FOUND.getMessage());
  }

  @Test
  @DisplayName("card 생성 실패 - Board에 해당 List가 존재하지 않음 테스트")
  void createCardNotContainedListTest() {
    //given
    list.setBoard(null);
    given(boardRepository.findByBoardId(eq(existBoardId))).willReturn(Optional.of(board));
    given(listRepository.findById(eq(existListId))).willReturn(Optional.of(list));

    //when
    //then
    assertThatThrownBy(() -> cardService.createCardInto(existBoardId, existListId, request))
        .isInstanceOf(ListNotFoundException.class)
        .hasMessage(ErrorCode.LIST_NOT_FOUND.getMessage());
  }

  @Test
  @DisplayName("card 내용 수정 성공 테스트")
  void updateCardTest() {
    //given
    Card beforeCard = card;
    arrangeCardUpdate(beforeCard);

    given(boardRepository.findByBoardId(eq(existBoardId))).willReturn(Optional.of(board));
    given(listRepository.findById(eq(existListId))).willReturn(Optional.of(list));
    given(cardRepository.findById(eq(existCardId))).willReturn(Optional.of(beforeCard));

    //when
    DTOContainer container = cardService
        .updateCard(existBoardId, existListId, existCardId, request);
    CardDTO resultCardDTO = (CardDTO) container.getResult();

    //then
    assertThat(container).usingRecursiveComparison().isEqualTo(dtoContainer);
    assertThat(resultCardDTO.getTitle()).isEqualTo(editTitle);
    assertThat(resultCardDTO.getContents()).isEqualTo(editContents);
    assertThat(container.getLog().getType()).isEqualTo(LogType.CARD_TITLE_AND_CONTENT_UPDATE);
  }

  @Test
  @DisplayName("card 수정 실패 - Board가 존재하지 않음 테스트")
  void updateCardBoardNotFoundTest() {
    //given
    arrangeCardUpdate(card);
    given(boardRepository.findByBoardId(eq(notExistBoardId))).willReturn(Optional.empty());

    //when
    //then
    assertThatThrownBy(
        () -> cardService.updateCard(notExistBoardId, existListId, existCardId, request)
    ).isInstanceOf(BoardNotFoundException.class)
        .hasMessage(ErrorCode.BOARD_NOT_FOUND.getMessage());
  }

  @Test
  @DisplayName("card 수정 실패 - List가 존재하지 않음 테스트")
  void updateCardListNotFoundTest() {
    //given
    arrangeCardUpdate(card);
    given(boardRepository.findByBoardId(eq(existBoardId))).willReturn(Optional.of(board));
    given(listRepository.findById(eq(notExistListId))).willReturn(Optional.empty());

    //when
    //then
    assertThatThrownBy(
        () -> cardService.updateCard(existBoardId, notExistListId, existCardId, request)
    ).isInstanceOf(ListNotFoundException.class)
        .hasMessage(ErrorCode.LIST_NOT_FOUND.getMessage());
  }

  @Test
  @DisplayName("card 수정 실패 - Card가 존재하지 않음 테스트")
  void updateCardCardNotFoundTest() {
    //given
    arrangeCardUpdate(card);
    given(boardRepository.findByBoardId(eq(existBoardId))).willReturn(Optional.of(board));
    given(listRepository.findById(eq(existListId))).willReturn(Optional.of(list));
    given(cardRepository.findById(eq(notExistCardId))).willReturn(Optional.empty());

    //when
    //then
    assertThatThrownBy(
        () -> cardService.updateCard(existBoardId, existListId, notExistCardId, request)
    ).isInstanceOf(CardNotFoundException.class)
        .hasMessage(ErrorCode.CARD_NOT_FOUND.getMessage());
  }

  @Test
  @DisplayName("card 수정 실패 - Board에 해당 List가 존재하지 않음 테스트")
  void updateCardBoardNotContainsListTest() {
    //given
    arrangeCardUpdate(card);
    list.setBoard(null);
    given(boardRepository.findByBoardId(eq(existBoardId))).willReturn(Optional.of(board));
    given(listRepository.findById(eq(existListId))).willReturn(Optional.of(list));

    //when
    //then
    assertThatThrownBy(
        () -> cardService.updateCard(existBoardId, existListId, existCardId, request)
    ).isInstanceOf(ListNotFoundException.class)
        .hasMessage(ErrorCode.LIST_NOT_FOUND.getMessage());
  }

  @Test
  @DisplayName("card 수정 실패 - List에 해당 Card가 존재하지 않음 테스트")
  void updateCardListNotContainsCardTest() {
    //given
    arrangeCardUpdate(card);
    card.setList(null);
    given(boardRepository.findByBoardId(eq(existBoardId))).willReturn(Optional.of(board));
    given(listRepository.findById(eq(existListId))).willReturn(Optional.of(list));
    given(cardRepository.findById(eq(existCardId))).willReturn(Optional.of(card));

    //when
    //then
    assertThatThrownBy(
        () -> cardService.updateCard(existBoardId, existListId, existCardId, request)
    ).isInstanceOf(CardNotFoundException.class)
        .hasMessage(ErrorCode.CARD_NOT_FOUND.getMessage());
  }

  private void arrangeCardUpdate(Card beforeCard) {
    request.setTitle(editTitle);
    request.setContents(editContents);

    card = request.toEntity();
    card.setList(list);
    card.setUser(user);

    cardDTO = CardDTO.from(card);

    cardUpdateLog = Log
        .cardUpdateLog(existListId, beforeCard.getTitle(), beforeCard.getContents(), card);
    cardUpdateLogDTO = LogDTO.from(cardUpdateLog);
    dtoContainer = new DTOContainer(cardDTO, cardUpdateLogDTO);
  }
}
