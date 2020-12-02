package dev.idion.bladitodo.service.card;

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
  private final LogRepository logRepository;

  public CardDTO createCardInto(Long listId, CardRequest request) {
    log.debug("카드요청 객체: {}", request);
    Card card = request.toEntity();
    // TODO: 멀티유저 대응
    User user = userRepository.findById(1L).orElseThrow(RuntimeException::new);
    List list = listRepository.findById(listId).orElseThrow(RuntimeException::new);
    card.setUser(user);
    card.setList(list);

    card = cardRepository.save(card);
    log.debug("저장된 card 정보: {}", card);

    Log cardAddLog = Log.builder()
        .withType(LogType.CARD_ADD)
        .withFromListId(listId)
        .withToListId(listId)
        .withAfterTitle(request.getTitle())
        .withAfterContents(request.getContents())
        .withBoard(list.getBoard())
        .build();
    logRepository.save(cardAddLog);

    return CardDTO.from(card);
  }

  public CardDTO updateCard(Long listId, Long cardId, CardRequest request) {
    log.debug("카드요청 객체: {}", request);
    Card card = cardRepository.findById(cardId).orElseThrow(RuntimeException::new);
    String beforeTitle = card.getTitle();
    String beforeContents = card.getContents();
    card.updateTitleAndContents(request);

    cardRepository.save(card);
    log.debug("변경된 card 정보: {}", card);

    Log cardUpdateLog = Log.builder()
        .withType(LogType.CARD_TITLE_AND_CONTENT_UPDATE)
        .withFromListId(listId)
        .withToListId(listId)
        .withBeforeTitle(beforeTitle)
        .withAfterTitle(card.getTitle())
        .withBeforeContents(beforeContents)
        .withAfterTitle(card.getContents())
        .withBoard(card.getList().getBoard())
        .build();
    logRepository.save(cardUpdateLog);

    return CardDTO.from(card);
  }

  public void archiveCard(Long listId, Long cardId) {
    try {
      Card card = cardRepository.findById(cardId).orElseThrow(RuntimeException::new);
      String beforeTitle = card.getTitle();
      String beforeContents = card.getContents();
      log.debug("보관할 카드 정보: {}", card);
      card.archiveCard();
      cardRepository.save(card);

      Log cardArchiveLog = Log.builder()
          .withType(LogType.CARD_ARCHIVE)
          .withFromListId(listId)
          .withBeforeTitle(beforeTitle)
          .withBeforeContents(beforeContents)
          .withBoard(card.getList().getBoard())
          .build();
      logRepository.save(cardArchiveLog);
    } catch (RuntimeException ignored) {
    }
  }
}
