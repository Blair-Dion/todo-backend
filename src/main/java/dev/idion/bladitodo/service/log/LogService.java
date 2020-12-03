package dev.idion.bladitodo.service.log;

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
    return logRepository.findLogsByBoardIdOrderByIdDesc(boardId)
        .stream()
        .map(LogDTO::from)
        .collect(Collectors.toList());
  }
}
