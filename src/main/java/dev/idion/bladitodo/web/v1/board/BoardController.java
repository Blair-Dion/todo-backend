package dev.idion.bladitodo.web.v1.board;

import dev.idion.bladitodo.service.board.BoardService;
import dev.idion.bladitodo.web.dto.BoardDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/v1/board")
@RestController
public class BoardController {

  private final BoardService boardService;

  @GetMapping("/{boardId}")
  public BoardDTO showBoard(@PathVariable Long boardId) {
    BoardDTO board = boardService.findBoard(boardId);
    log.debug("조회된 board: {}", board);

    return board;
  }
}
