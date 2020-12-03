package dev.idion.bladitodo.domain.log;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class LogRepositoryImpl implements LogRepositoryCustom {

  private final JPAQueryFactory queryFactory;
}
