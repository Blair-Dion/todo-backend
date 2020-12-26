package dev.idion.bladitodo.service.card;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;

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
  final long existListId = 1L;
  final long existUserId = 1L;
  final String title = "카드 제목";
  final String contents = "카드 내용";

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

  CardDTO cardDTO;
  LogDTO cardAddLogDTO;
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
    cardAddLog = Log.cardAddLog(existListId, card, board);
    cardAddLogDTO = LogDTO.from(cardAddLog);
    dtoContainer = new DTOContainer(cardDTO, cardAddLogDTO);

    given(boardRepository.findByBoardId(eq(existBoardId))).willReturn(Optional.of(board));
    given(listRepository.findById(existListId)).willReturn(Optional.of(list));
    given(userRepository.findById(eq(existUserId))).willReturn(Optional.of(user));
    given(cardRepository.save(any(Card.class))).willReturn(card);

    DTOContainer container = cardService.createCardInto(existBoardId, existListId, request);
    CardDTO resultCardDTO = (CardDTO) container.getResult();

    assertThat(container).usingRecursiveComparison().isEqualTo(dtoContainer);
    assertThat(resultCardDTO.getTitle()).isEqualTo(title);
    assertThat(resultCardDTO.getContents()).isEqualTo(contents);
    assertThat(container.getLog().getType()).isEqualTo(LogType.CARD_ADD);
  }
}
