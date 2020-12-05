package dev.idion.bladitodo.web.v1.list;

import dev.idion.bladitodo.service.list.ListService;
import dev.idion.bladitodo.web.dto.DTOContainer;
import dev.idion.bladitodo.web.v1.list.request.ListRequest;
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
@RestController
@RequestMapping("/v1/board/{boardId}/list")
public class ListController {

  private final ListService listService;

  @ResponseStatus(HttpStatus.CREATED)
  @PostMapping
  public DTOContainer createList(@PathVariable Long boardId, @RequestBody ListRequest listRequest) {
    DTOContainer listCreateContainer = listService.createListInto(boardId, listRequest);
    log.debug("생성된 list 정보: {}", listCreateContainer);

    return listCreateContainer;
  }

  @PutMapping("/{listId}")
  public DTOContainer updateListName(@PathVariable Long boardId, @PathVariable Long listId,
      @RequestBody ListRequest listRequest) {
    DTOContainer listNameUpdateContainer = listService
        .updateListNameOf(boardId, listId, listRequest);
    log.debug("변경된 list 정보: {}", listNameUpdateContainer);

    return listNameUpdateContainer;
  }

  @ResponseStatus(HttpStatus.NO_CONTENT)
  @DeleteMapping("/{listId}")
  public void archiveListName(@PathVariable Long boardId, @PathVariable Long listId) {
    listService.archiveList(boardId, listId);
  }
}
