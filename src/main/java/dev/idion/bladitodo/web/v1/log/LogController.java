package dev.idion.bladitodo.web.v1.log;

import dev.idion.bladitodo.service.log.LogService;
import dev.idion.bladitodo.web.dto.LogDTO;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/v1/board/{boardId}/log")
@RestController
public class LogController {

  private final LogService logService;

  @GetMapping
  public List<LogDTO> getLogs(@PathVariable Long boardId) {
    return logService.findLogs(boardId);
  }
}
