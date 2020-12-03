package dev.idion.bladitodo.domain.board;

import com.querydsl.jpa.impl.JPAQueryFactory;
import dev.idion.bladitodo.domain.list.QList;
import java.util.Optional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class BoardRepositoryImpl implements BoardRepositoryCustom {

  private final JPAQueryFactory queryFactory;

  @Override
  public Optional<Board> findByBoardId(Long id) {
    return queryFactory.selectFrom(QBoard.board)
        .distinct()
        .leftJoin(QBoard.board.lists, QList.list)
        .fetchJoin()
        .where(QBoard.board.id.eq(id))
        .fetch()
        .stream()
        .findFirst();
  }
}
