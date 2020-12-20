package dev.idion.bladitodo.service.list;

import dev.idion.bladitodo.common.error.exception.BadRequestException;
import dev.idion.bladitodo.common.error.exception.domain.BoardNotFoundException;
import dev.idion.bladitodo.common.error.exception.domain.ListNotFoundException;
import dev.idion.bladitodo.domain.board.Board;
import dev.idion.bladitodo.domain.board.BoardRepository;
import dev.idion.bladitodo.domain.list.List;
import dev.idion.bladitodo.domain.list.ListRepository;
import dev.idion.bladitodo.domain.log.Log;
import dev.idion.bladitodo.domain.log.LogRepository;
import dev.idion.bladitodo.web.dto.DTOContainer;
import dev.idion.bladitodo.web.dto.ListDTO;
import dev.idion.bladitodo.web.dto.LogDTO;
import dev.idion.bladitodo.web.v1.list.request.ListRequest;
import java.time.Clock;
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
  private final Clock clock;

  public DTOContainer createListInto(Long boardId, ListRequest request) {
    if (request == null) {
      throw new BadRequestException("요청이 올바르지 않습니다 ListRequest는 반드시 포함되어야 합니다.");
    }

    Board board = boardRepository.findByBoardId(boardId).orElseThrow(BoardNotFoundException::new);

    log.debug("리스트 요청 객체: {}", request);
    List list = request.toEntity();

    list.setBoard(board);
    list = listRepository.save(list);
    log.debug("저장된 List 정보: {}", list);

    Log listAddLog = Log.listAddLog(list);
    logRepository.save(listAddLog);

    return new DTOContainer(ListDTO.from(list), LogDTO.from(listAddLog));
  }

  public DTOContainer updateListNameOf(Long boardId, Long listId, ListRequest request) {
    if (request == null) {
      throw new BadRequestException("요청이 올바르지 않습니다 ListRequest는 반드시 포함되어야 합니다.");
    }

    Board board = boardRepository.findByBoardId(boardId).orElseThrow(BoardNotFoundException::new);
    List list = listRepository.findById(listId).orElseThrow(ListNotFoundException::new);
    if (!board.getLists().contains(list)) {
      throw new ListNotFoundException();
    }

    log.debug("리스트 요청 객체: {}", request);
    list.rename(request.getName());
    log.debug("변경된 list 정보: {}", list);

    Log listNameUpdateLog = Log.listRenameLog(list);
    logRepository.save(listNameUpdateLog);

    return new DTOContainer(ListDTO.from(list), LogDTO.from(listNameUpdateLog));
  }

  public DTOContainer archiveList(Long boardId, Long listId) {
    Board board = boardRepository.findByBoardId(boardId).orElseThrow(BoardNotFoundException::new);
    List list = listRepository.findById(listId).orElseThrow(ListNotFoundException::new);
    if (!board.getLists().contains(list)) {
      throw new ListNotFoundException();
    }

    log.debug("보관할 리스트 정보: {}", list);
    list.archiveList(clock);

    Log listArchiveLog = Log.listArchiveLog(list);
    logRepository.save(listArchiveLog);

    return new DTOContainer(ListDTO.from(list), LogDTO.from(listArchiveLog));
  }
}
