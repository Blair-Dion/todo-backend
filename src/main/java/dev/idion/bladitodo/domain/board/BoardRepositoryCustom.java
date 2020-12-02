package dev.idion.bladitodo.domain.board;

import java.util.Optional;

public interface BoardRepositoryCustom {

  Optional<Board> findByBoardId(Long id);
}
