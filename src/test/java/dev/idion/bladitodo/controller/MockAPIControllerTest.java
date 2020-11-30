package dev.idion.bladitodo.controller;

import static org.assertj.core.api.Assertions.fail;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;

@AutoConfigureRestDocs(uriHost = "52.78.241.12/api", uriPort = 80)
@WebMvcTest(controllers = {MockAPIController.class})
@DisplayName("Mock API 테스트")
class MockAPIControllerTest {

  private static final String PRE_URI = "/mock";

  @Autowired
  MockMvc mockMvc;

  @Test
  @DisplayName("Mock API board v1 테스트")
  void getBoardWithListIdOfTest() throws Exception {
    mockMvc.perform(get(PRE_URI + "/v1/board/1").contentType(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isOk())
        .andDo(document("{class-name}/{method-name}",
            preprocessRequest(prettyPrint()),
            preprocessResponse(prettyPrint()),
            responseFields(
                fieldWithPath("board_id").description("해당 보드의 DB id").type(JsonFieldType.NUMBER),
                fieldWithPath("name").description("해당 보드의 이름").type(JsonFieldType.STRING),
                fieldWithPath("list_ids").description("해당 보드의 리스트 id 목록").type(JsonFieldType.ARRAY)
            )));
  }

  @Test
  @DisplayName("Mock API board v2 테스트")
  void getBoardDTOTest() throws Exception {
    mockMvc.perform(get(PRE_URI + "/v2/board/1").contentType(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isOk())
        .andDo(document("{class-name}/{method-name}",
            preprocessRequest(prettyPrint()),
            preprocessResponse(prettyPrint()),
            responseFields(
                fieldWithPath("id").description("해당 보드의 DB id").type(JsonFieldType.NUMBER),
                fieldWithPath("name").description("해당 보드의 이름").type(JsonFieldType.STRING),
                fieldWithPath("lists").description("해당 보드의 리스트 목록").type(JsonFieldType.ARRAY),
                fieldWithPath("lists[].id").description("리스트의 DB id").type(JsonFieldType.NUMBER),
                fieldWithPath("lists[].name").description("리스트의 이름").type(JsonFieldType.STRING),
                fieldWithPath("lists[].board_id").description("리스트가 속한 보드의 DB id")
                    .type(JsonFieldType.NUMBER),
                fieldWithPath("lists[].archived").description("리스트가 아카이브 되었는지 여부")
                    .type(JsonFieldType.BOOLEAN),
                fieldWithPath("lists[].archived_datetime").description("리스트가 아카이빙 된 시각").optional()
                    .type(JsonFieldType.STRING),
                fieldWithPath("lists[].cards").description("리스트에 속한 카드 목록")
                    .type(JsonFieldType.ARRAY),
                fieldWithPath("lists[].cards[].id").description("카드의 DB id")
                    .type(JsonFieldType.NUMBER),
                fieldWithPath("lists[].cards[].title").description("카드의 제목")
                    .type(JsonFieldType.STRING),
                fieldWithPath("lists[].cards[].contents").description("카드의 내용")
                    .type(JsonFieldType.STRING),
                fieldWithPath("lists[].cards[].user_db_id").description("카드 소유자의 DB id")
                    .type(JsonFieldType.NUMBER),
                fieldWithPath("lists[].cards[].user_id").description("카드 소유자의 id")
                    .type(JsonFieldType.STRING),
                fieldWithPath("lists[].cards[].list_id").description("카드가 속한 리스트의 DB id")
                    .type(JsonFieldType.NUMBER),
                fieldWithPath("lists[].cards[].archived").description("카드가 아카이브 되었는지 여부")
                    .type(JsonFieldType.BOOLEAN),
                fieldWithPath("lists[].cards[].archived_datetime").description("카드가 아카이빙 된 시각")
                    .optional().type(JsonFieldType.STRING)
            )));
  }

  @Test
  void getUserDTOTest() {
    fail("Not Implemented");
  }

  @Test
  void getListDTOTest() {
    fail("Not Implemented");
  }
}
