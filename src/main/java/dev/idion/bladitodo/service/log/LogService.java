package dev.idion.bladitodo.service.log;

import dev.idion.bladitodo.common.error.exception.domain.BoardNotFoundException;
import dev.idion.bladitodo.domain.log.LogRepository;
import dev.idion.bladitodo.web.dto.LogDTO;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional
public class LogService {

  private final LogRepository logRepository;

  public List<LogDTO> findLogs(Long boardId) {
    List<LogDTO> logs = logRepository.findLogsByBoardIdOrderByIdDesc(boardId)
        .stream()
        .map(LogDTO::from)
        .collect(Collectors.toList());

    if (logs.isEmpty()) {
      throw new BoardNotFoundException();
    }
    return logs;
  }
}
