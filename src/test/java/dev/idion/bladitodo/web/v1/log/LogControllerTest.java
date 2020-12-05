package dev.idion.bladitodo.web.v1.log;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import dev.idion.bladitodo.domain.log.LogType;
import dev.idion.bladitodo.service.log.LogService;
import dev.idion.bladitodo.web.dto.LogDTO;
import java.util.List;
import java.util.StringJoiner;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;

@AutoConfigureRestDocs(uriHost = "54.180.198.188/api", uriPort = 80)
@WebMvcTest(controllers = {LogController.class})
@DisplayName("v1 Log API 테스트")
class LogControllerTest {

  private static final String PRE_URI = "/v1";

  @Autowired
  MockMvc mockMvc;

  @MockBean
  LogService logService;

  @Test
  void Board로그_조회_테스트() throws Exception {
    //given
    long boardId = 1L;
    List<LogDTO> logs = Lists.list(
        LogDTO.builder()
            .withId(2L)
            .withType(LogType.CARD_ADD)
            .withAfterTitle("제목")
            .withAfterContents("내용")
            .withFromListId(1L)
            .withToListId(1L)
            .build(),
        LogDTO.builder()
            .withId(1L)
            .withType(LogType.LIST_ADD)
            .withToListId(1L)
            .build()
    );

    //when
    when(logService.findLogs(eq(boardId))).thenReturn(logs);

    //then
    mockMvc.perform(get(PRE_URI + "/board/{board_id}/log", boardId))
        .andDo(print())
        .andExpect(status().isOk())
        .andDo(document("{class-name}/{method-name}",
            preprocessRequest(prettyPrint()),
            preprocessResponse(prettyPrint()),
            pathParameters(
                parameterWithName("board_id").description("로그를 조회할 board의 DB id")
            ),
            responseFields(
                fieldWithPath("[].id").description("로그의 DB id").type(JsonFieldType.NUMBER),
                typeField("[].type"),
                fieldWithPath("[].before_title").description("이전 제목").type(JsonFieldType.STRING)
                    .optional(),
                fieldWithPath("[].after_title").description("이후 제목").type(JsonFieldType.STRING)
                    .optional(),
                fieldWithPath("[].before_contents").description("이전 내용").type(JsonFieldType.STRING)
                    .optional(),
                fieldWithPath("[].after_contents").description("이후 내용").type(JsonFieldType.STRING)
                    .optional(),
                fieldWithPath("[].from_list_id").description("이전 리스트의 DB id")
                    .type(JsonFieldType.NUMBER).optional(),
                fieldWithPath("[].to_list_id").description("이후 리스트의 DB id")
                    .type(JsonFieldType.NUMBER).optional()
            )
        ));
  }

  private FieldDescriptor typeField(String path) {
    StringJoiner sj = new StringJoiner(", ");
    for (LogType value : LogType.values()) {
      sj.add("`" + value + "`");
    }
    String description = "로그의 종류(" + sj.toString() + ")";

    return fieldWithPath(path).description(description).type(JsonFieldType.STRING);
  }

}
