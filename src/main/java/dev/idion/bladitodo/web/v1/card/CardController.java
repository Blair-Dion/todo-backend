package dev.idion.bladitodo.web.v1.card;

import dev.idion.bladitodo.service.card.CardService;
import dev.idion.bladitodo.web.dto.CardDTO;
import dev.idion.bladitodo.web.v1.card.request.CardCreateRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/v1/list/{listId}/card")
@RestController
public class CardController {

  private final CardService cardService;

  @PostMapping
  public CardDTO createCard(@PathVariable Long listId, @RequestBody CardCreateRequest request) {
    CardDTO createdCard = cardService.createCardInto(listId, request);
    log.debug("생성된 카드 정보: {}", createdCard);

    return createdCard;
  }
}
