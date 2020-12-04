package dev.idion.bladitodo.web.v1.list;

import dev.idion.bladitodo.service.list.ListService;
import dev.idion.bladitodo.web.dto.ListDTO;
import dev.idion.bladitodo.web.v1.list.request.ListRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/board/{boardId}/list")
public class ListController {

  private final ListService listService;

  @PostMapping
  public ListDTO createList(@PathVariable Long boardId, @RequestBody ListRequest listRequest) {
    ListDTO list = listService.createListInto(boardId, listRequest);
    log.debug("생성된 list 정보: {}", list);

    return list;
  }
}