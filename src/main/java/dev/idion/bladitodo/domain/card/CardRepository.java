package dev.idion.bladitodo.domain.card;

import java.util.Optional;
import javax.annotation.Nonnull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CardRepository extends JpaRepository<Card, Long> {

  @Override
  @Nonnull
  @Query("select c from Card c where c.isArchived = false and c.id = :cardId")
  Optional<Card> findById(@Nonnull @Param("cardId") Long cardId);
}
