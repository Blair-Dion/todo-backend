package dev.idion.bladitodo.service.card;

import dev.idion.bladitodo.domain.card.Card;
import dev.idion.bladitodo.domain.card.CardRepository;
import dev.idion.bladitodo.domain.list.ListRepository;
import dev.idion.bladitodo.domain.user.UserRepository;
import dev.idion.bladitodo.web.dto.CardDTO;
import dev.idion.bladitodo.web.v1.card.request.CardCreateRequest;
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

  public CardDTO createCardInto(Long listId, CardCreateRequest request) {
    log.debug("카드요청 객체: {}", request);
    Card card = request.toEntity();
    // TODO: 멀티유저 대응
    card.setUser(userRepository.findById(1L).orElseThrow(RuntimeException::new));
    card.setList(listRepository.findById(listId).orElseThrow(RuntimeException::new));
    card = cardRepository.save(card);
    log.debug("저장된 card 정보: {}", card);
    return CardDTO.from(card);
  }
}
