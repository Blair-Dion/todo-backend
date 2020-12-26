package dev.idion.bladitodo.service.card;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import dev.idion.bladitodo.domain.board.Board;
import dev.idion.bladitodo.domain.board.BoardRepository;
import dev.idion.bladitodo.domain.card.Card;
import dev.idion.bladitodo.domain.card.CardRepository;
import dev.idion.bladitodo.domain.list.List;
import dev.idion.bladitodo.domain.list.ListRepository;
import dev.idion.bladitodo.domain.log.LogRepository;
import dev.idion.bladitodo.domain.user.User;
import dev.idion.bladitodo.domain.user.UserRepository;
import dev.idion.bladitodo.web.dto.DTOContainer;
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

  @BeforeEach
  void setUp() {
  }

  @Test
  @DisplayName("card 생성 성공 테스트")
  void createCardTest() {
    Board board = Board.builder().build();
    User user = User.builder().build();
    List list = List.builder().build();
    list.setBoard(board);

    CardRequest request = new CardRequest();
    request.setTitle("카드 제목");
    request.setContents("카드 내용");

    Card card = request.toEntity();

    given(boardRepository.findByBoardId(1L)).willReturn(Optional.of(board));
    given(listRepository.findById(1L)).willReturn(Optional.of(list));
    given(userRepository.findById(1L)).willReturn(Optional.of(user));
    given(cardRepository.save(any(Card.class))).willReturn(card);

    card.setList(list);
    card.setUser(user);

    DTOContainer container = cardService.createCardInto(1L, 1L, request);

    assertThat(container).isNotNull();
  }
}
