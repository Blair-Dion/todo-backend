package dev.idion.bladitodo.domain.card;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CardRepositoryImpl implements CardRepositoryCustom {

  private final JPAQueryFactory queryFactory;
}
