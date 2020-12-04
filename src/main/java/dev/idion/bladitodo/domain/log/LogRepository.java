package dev.idion.bladitodo.domain.log;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LogRepository extends JpaRepository<Log, Long>, LogRepositoryCustom {

  List<Log> findLogsByBoardIdOrderByIdDesc(Long boardId);
}
