package dev.idion.bladitodo.web.v1.card;

import dev.idion.bladitodo.service.card.CardService;
import dev.idion.bladitodo.web.dto.DTOContainer;
import dev.idion.bladitodo.web.v1.card.request.CardMoveRequest;
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
@RequestMapping("/v1/board/{boardId}/list/{listId}/card")
@RestController
public class CardController {

  private final CardService cardService;

  @ResponseStatus(HttpStatus.CREATED)
  @PostMapping
  public DTOContainer createCard(@PathVariable Long boardId, @PathVariable Long listId,
      @RequestBody CardRequest request) {
    DTOContainer createdCardContainer = cardService.createCardInto(boardId, listId, request);
    log.debug("생성된 카드 정보: {}", createdCardContainer);

    return createdCardContainer;
  }

  @PutMapping("/{cardId}")
  public DTOContainer updateCard(@PathVariable Long boardId, @PathVariable Long listId,
      @PathVariable Long cardId, @RequestBody CardRequest request) {
    DTOContainer updatedCardContainer = cardService.updateCard(boardId, listId, cardId, request);
    log.debug("변경된 카드 정보: {}", updatedCardContainer);

    return updatedCardContainer;
  }

  @PutMapping("/{cardId}/move")
  public DTOContainer moveCard(@PathVariable Long boardId, @PathVariable Long listId,
      @PathVariable Long cardId, @RequestBody CardMoveRequest request) {
    DTOContainer moveCardContainer = cardService.moveCard(boardId, listId, cardId, request);
    log.debug("이동된 카드 정보: {}", moveCardContainer);

    return moveCardContainer;
  }

  @DeleteMapping("/{cardId}")
  public DTOContainer archiveCard(@PathVariable Long boardId, @PathVariable Long listId,
      @PathVariable Long cardId) {
    log.debug("보관 처리할 카드의 id: {}", cardId);
    DTOContainer archiveCardContainer = cardService.archiveCard(boardId, listId, cardId);

    return archiveCardContainer;
  }
}
