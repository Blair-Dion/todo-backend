package dev.idion.bladitodo.service.card;

import dev.idion.bladitodo.common.error.exception.UserNotFoundException;
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
import dev.idion.bladitodo.domain.user.User;
import dev.idion.bladitodo.domain.user.UserRepository;
import dev.idion.bladitodo.web.dto.CardDTO;
import dev.idion.bladitodo.web.dto.DTOContainer;
import dev.idion.bladitodo.web.dto.LogDTO;
import dev.idion.bladitodo.web.v1.card.request.CardMoveRequest;
import dev.idion.bladitodo.web.v1.card.request.CardRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional
public class CardService {

  private final CardRepository cardRepository;
  private final UserRepository userRepository;
  private final ListRepository listRepository;
  private final BoardRepository boardRepository;
  private final LogRepository logRepository;

  public DTOContainer createCardInto(Long boardId, Long listId, CardRequest request) {
    boardRepository.findByBoardId(boardId).orElseThrow(BoardNotFoundException::new);

    log.debug("카드요청 객체: {}", request);
    Card card = request.toEntity();
    // TODO: 멀티유저 대응
    User user = userRepository.findById(1L).orElseThrow(UserNotFoundException::new);
    List list = listRepository.findById(listId).orElseThrow(ListNotFoundException::new);
    card.setUser(user);
    card.setList(list);

    card = cardRepository.save(card);
    log.debug("저장된 card 정보: {}", card);

    Log cardAddLog = Log.cardAddLog(listId, request, list.getBoard());
    logRepository.save(cardAddLog);

    return new DTOContainer(CardDTO.from(card), LogDTO.from(cardAddLog));
  }

  public DTOContainer updateCard(Long boardId, Long listId, Long cardId, CardRequest request) {
    boardRepository.findByBoardId(boardId).orElseThrow(BoardNotFoundException::new);
    listRepository.findById(listId).orElseThrow(ListNotFoundException::new);

    log.debug("카드요청 객체: {}", request);
    Card card = cardRepository.findById(cardId).orElseThrow(CardNotFoundException::new);
    String beforeTitle = card.getTitle();
    String beforeContents = card.getContents();
    card.updateTitleAndContents(request);

    log.debug("변경된 card 정보: {}", card);

    Log cardUpdateLog = Log.cardUpdateLog(listId, beforeTitle, beforeContents, card);
    logRepository.save(cardUpdateLog);

    return new DTOContainer(CardDTO.from(card), LogDTO.from(cardUpdateLog));
  }

  public DTOContainer moveCard(Long boardId, Long listId, Long cardId, CardMoveRequest request) {
    Board board = boardRepository.findByBoardId(boardId).orElseThrow(BoardNotFoundException::new);
    listRepository.findById(listId).orElseThrow(ListNotFoundException::new);

    log.debug("카드 이동 요청 객체: {}", request);
    Card card = cardRepository.findById(cardId).orElseThrow(CardNotFoundException::new);
    List destinationList = listRepository.findById(request.getDestinationListId())
        .orElseThrow(() -> new ListNotFoundException("이동하려하는 리스트가 존재하지 않습니다."));
    card.moveCardTo(destinationList);

    Log cardMoveLog = Log.cardMoveLog(listId, destinationList.getId(), board);
    logRepository.save(cardMoveLog);

    return new DTOContainer(CardDTO.from(card), LogDTO.from(cardMoveLog));
  }

  public DTOContainer archiveCard(Long boardId, Long listId, Long cardId) {
    boardRepository.findByBoardId(boardId).orElseThrow(BoardNotFoundException::new);
    listRepository.findById(listId).orElseThrow(ListNotFoundException::new);

    Card card = cardRepository.findById(cardId).orElseThrow(CardNotFoundException::new);
    String beforeTitle = card.getTitle();
    String beforeContents = card.getContents();
    log.debug("보관할 카드 정보: {}", card);
    card.archiveCard();

    Log cardArchiveLog = Log.cardArchiveLog(listId, beforeTitle, beforeContents, card);
    logRepository.save(cardArchiveLog);

    return new DTOContainer(CardDTO.from(card), LogDTO.from(cardArchiveLog));
  }
}
