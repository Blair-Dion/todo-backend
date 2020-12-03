package dev.idion.bladitodo.service.board;

import dev.idion.bladitodo.domain.board.Board;
import dev.idion.bladitodo.domain.board.BoardRepository;
import dev.idion.bladitodo.web.dto.BoardDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional
public class BoardService {

  private final BoardRepository boardRepository;

  public BoardDTO findBoard(Long boardId) {
    Board board = boardRepository.findById(boardId).orElseThrow(RuntimeException::new);
    log.debug("DB에서 조회된 board: {}", board);

    return BoardDTO.from(board);
  }
}
