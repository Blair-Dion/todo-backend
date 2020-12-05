package dev.idion.bladitodo.web.v1.card;

import dev.idion.bladitodo.service.card.CardService;
import dev.idion.bladitodo.web.dto.CardDTO;
import dev.idion.bladitodo.web.v1.card.request.CardRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/v1/list/{listId}/card")
@RestController
public class CardController {

  private final CardService cardService;

  @ResponseStatus(HttpStatus.CREATED)
  @PostMapping
  public CardDTO createCard(@PathVariable Long listId, @RequestBody CardRequest request) {
    CardDTO createdCard = cardService.createCardInto(listId, request);
    log.debug("생성된 카드 정보: {}", createdCard);

    return createdCard;
  }

  @PutMapping("/{cardId}")
  public CardDTO updateCard(@PathVariable Long listId, @PathVariable Long cardId,
      @RequestBody CardRequest request) {
    CardDTO updatedCard = cardService.updateCard(listId, cardId, request);
    log.debug("변경된 카드 정보: {}", updatedCard);

    return updatedCard;
  }

  @ResponseStatus(HttpStatus.NO_CONTENT)
  @DeleteMapping("/{cardId}")
  public void updateCard(@PathVariable Long listId, @PathVariable Long cardId) {
    log.debug("보관 처리할 카드의 id: {}", cardId);
    cardService.archiveCard(listId, cardId);
  }
}
