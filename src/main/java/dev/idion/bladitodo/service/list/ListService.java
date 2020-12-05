package dev.idion.bladitodo.service.list;

import dev.idion.bladitodo.common.error.exception.domain.BoardNotFoundException;
import dev.idion.bladitodo.common.error.exception.domain.ListNotFoundException;
import dev.idion.bladitodo.domain.board.Board;
import dev.idion.bladitodo.domain.board.BoardRepository;
import dev.idion.bladitodo.domain.list.List;
import dev.idion.bladitodo.domain.list.ListRepository;
import dev.idion.bladitodo.domain.log.Log;
import dev.idion.bladitodo.domain.log.LogRepository;
import dev.idion.bladitodo.domain.log.LogType;
import dev.idion.bladitodo.web.dto.DTOContainer;
import dev.idion.bladitodo.web.dto.ListDTO;
import dev.idion.bladitodo.web.dto.LogDTO;
import dev.idion.bladitodo.web.v1.list.request.ListRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional
public class ListService {

  private final ListRepository listRepository;
  private final BoardRepository boardRepository;
  private final LogRepository logRepository;

  public DTOContainer createListInto(Long boardId, ListRequest request) {
    log.debug("리스트 요청 객체: {}", request);
    List list = request.toEntity();
    Board board = boardRepository.findByBoardId(boardId).orElseThrow(BoardNotFoundException::new);

    list.setBoard(board);
    list = listRepository.save(list);
    log.debug("저장된 List 정보: {}", list);

    Log listAddLog = Log.builder()
        .withType(LogType.LIST_ADD)
        .withToListId(list.getId())
        .withBoard(list.getBoard())
        .build();
    logRepository.save(listAddLog);

    return new DTOContainer(ListDTO.from(list), LogDTO.from(listAddLog));
  }

  public DTOContainer updateListNameOf(Long boardId, Long listId, ListRequest request) {
    boardRepository.findByBoardId(boardId).orElseThrow(BoardNotFoundException::new);

    log.debug("리스트 요청 객체: {}", request);
    List list = listRepository.findById(listId).orElseThrow(ListNotFoundException::new);
    list.rename(request);
    log.debug("변경된 list 정보: {}", list);

    Log listNameUpdateLog = Log.builder()
        .withType(LogType.LIST_RENAME)
        .withFromListId(list.getId())
        .withToListId(list.getId())
        .withBoard(list.getBoard())
        .build();
    logRepository.save(listNameUpdateLog);

    return new DTOContainer(ListDTO.from(list), LogDTO.from(listNameUpdateLog));
  }

  public void archiveList(Long boardId, Long listId) {
    boardRepository.findByBoardId(boardId).orElseThrow(BoardNotFoundException::new);

    List list = listRepository.findById(listId).orElseThrow(ListNotFoundException::new);
    log.debug("보관할 리스트 정보: {}", list);
    list.archiveList();

    Log listArchiveLog = Log.builder()
        .withType(LogType.LIST_ARCHIVE)
        .withFromListId(list.getId())
        .withBoard(list.getBoard())
        .build();
    logRepository.save(listArchiveLog);
  }
}
