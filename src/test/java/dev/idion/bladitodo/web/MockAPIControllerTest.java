package dev.idion.bladitodo.web;

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

import dev.idion.bladitodo.web.mockapi.MockAPIController;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;

@AutoConfigureRestDocs(uriHost = "54.180.198.188/api", uriPort = 80)
@WebMvcTest(controllers = {MockAPIController.class})
@DisplayName("Mock API 테스트")
class MockAPIControllerTest {

  private static final String PRE_URI = "/mock";

  @Autowired
  MockMvc mockMvc;

  @Test
  @DisplayName("Mock API board v1 테스트")
  void getBoardWithListIdOfTest() throws Exception {
    mockMvc
        .perform(get(PRE_URI + "/v1/board/{board_id}", 1).contentType(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isOk())
        .andDo(document("{class-name}/{method-name}",
            preprocessRequest(prettyPrint()),
            preprocessResponse(prettyPrint()),
            pathParameters(
                parameterWithName("board_id").description("보드의 DB id, 숫자값")
            ),
            responseFields(
                fieldWithPath("board_id").description("보드의 DB id").type(JsonFieldType.NUMBER),
                fieldWithPath("name").description("보드의 이름").type(JsonFieldType.STRING),
                fieldWithPath("list_ids").description("보드의 리스트 id 목록").type(JsonFieldType.ARRAY)
            )));
  }

  @Test
  @DisplayName("Mock API board v2 테스트")
  void getBoardDTOTest() throws Exception {
    mockMvc
        .perform(get(PRE_URI + "/v2/board/{board_id}", 1).contentType(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isOk())
        .andDo(document("{class-name}/{method-name}",
            preprocessRequest(prettyPrint()),
            preprocessResponse(prettyPrint()),
            pathParameters(
                parameterWithName("board_id").description("보드의 DB id, 숫자값")
            ),
            responseFields(
                fieldWithPath("id").description("보드의 DB id").type(JsonFieldType.NUMBER),
                fieldWithPath("name").description("보드의 이름").type(JsonFieldType.STRING),
                fieldWithPath("lists").description("보드의 리스트 목록").type(JsonFieldType.ARRAY),
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
                fieldWithPath("lists[].cards[].user_id").description("카드 소유자의 id")
                    .type(JsonFieldType.STRING),
                fieldWithPath("lists[].cards[].profile_image_url").description("카드 소유자의 프로필 이미지")
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
  @DisplayName("Mock API user v1 테스트")
  void getUserDTOTest() throws Exception {
    mockMvc.perform(get(PRE_URI + "/v1/user/{user_id}", 1).contentType(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isOk())
        .andDo(document("{class-name}/{method-name}",
            preprocessRequest(prettyPrint()),
            preprocessResponse(prettyPrint()),
            pathParameters(
                parameterWithName("user_id").description("유저의 DB id, 숫자값")
            ),
            responseFields(
                fieldWithPath("id").description("유저의 DB id").type(JsonFieldType.NUMBER),
                fieldWithPath("profile_image_url").description("유저의 프로필 이미지")
                    .type(JsonFieldType.STRING),
                fieldWithPath("user_id").description("유저의 아이디").type(JsonFieldType.STRING),
                fieldWithPath("user_nickname").description("유저의 닉네임").type(JsonFieldType.STRING)
            )));
  }

  @Test
  @DisplayName("Mock API list v1 테스트")
  void getListDTOTest() throws Exception {
    mockMvc.perform(get(PRE_URI + "/v1/list/{list_id}", 1).contentType(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isOk())
        .andDo(document("{class-name}/{method-name}",
            preprocessRequest(prettyPrint()),
            preprocessResponse(prettyPrint()),
            pathParameters(
                parameterWithName("list_id").description("리스트의 DB id, 숫자값")
            ),
            responseFields(
                fieldWithPath("id").description("리스트의 DB id").type(JsonFieldType.NUMBER),
                fieldWithPath("name").description("리스트의 이름").type(JsonFieldType.STRING),
                fieldWithPath("board_id").description("리스트가 속한 보드의 DB id")
                    .type(JsonFieldType.NUMBER),
                fieldWithPath("archived").description("리스트가 아카이브 되었는지 여부")
                    .type(JsonFieldType.BOOLEAN),
                fieldWithPath("archived_datetime").description("리스트가 아카이빙 된 시각").optional()
                    .type(JsonFieldType.STRING),
                fieldWithPath("cards").description("리스트에 속한 카드 목록").type(JsonFieldType.ARRAY),
                fieldWithPath("cards[].id").description("카드의 DB id").type(JsonFieldType.NUMBER),
                fieldWithPath("cards[].title").description("카드의 제목").type(JsonFieldType.STRING),
                fieldWithPath("cards[].contents").description("카드의 내용").type(JsonFieldType.STRING),
                fieldWithPath("cards[].user_id").description("카드 소유자의 id")
                    .type(JsonFieldType.STRING),
                fieldWithPath("cards[].profile_image_url").description("카드 소유자의 프로필 이미지")
                    .type(JsonFieldType.STRING),
                fieldWithPath("cards[].list_id").description("카드가 속한 리스트의 DB id")
                    .type(JsonFieldType.NUMBER),
                fieldWithPath("cards[].archived").description("카드가 아카이브 되었는지 여부")
                    .type(JsonFieldType.BOOLEAN),
                fieldWithPath("cards[].archived_datetime").description("카드가 아카이빙 된 시각").optional()
                    .type(JsonFieldType.STRING))));
  }
}
