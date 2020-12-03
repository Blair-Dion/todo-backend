package dev.idion.bladitodo.domain.board;

import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.Optional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class BoardRepositoryImpl implements BoardRepositoryCustom {

  private final JPAQueryFactory queryFactory;

  @Override
  public Optional<Board> findByBoardId(Long id) {
    return Optional.empty();
//    return queryFactory.selectFrom(QBoard.board)
//        .leftJoin(QBoard.board.lists, QList.list)
//        .on(QBoard.board.id.eq(QList.list.board.id))
//        .leftJoin(QList.list.cards, QCard.card)
//        .on(QList.list.id.eq(QCard.card.list.id).and(QCard.card.isArchived.eq(false)))
//        .where(QBoard.board.id.eq(id))
//        .fetch()
//        .stream()
//        .findFirst();
  }
}
