package dev.idion.bladitodo.web.v1.list;

import static dev.idion.bladitodo.util.TestUtil.asJsonString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import dev.idion.bladitodo.service.list.ListService;
import dev.idion.bladitodo.web.dto.ListDTO;
import dev.idion.bladitodo.web.v1.list.request.ListRequest;
import java.util.ArrayList;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

@AutoConfigureRestDocs(uriHost = "54.180.198.188/api", uriPort = 80)
@WebMvcTest(controllers = {ListController.class})
@DisplayName("v1 List API 테스트")
class ListControllerTest {

  private static final String PRE_URI = "/v1";

  @Autowired
  MockMvc mockMvc;

  @MockBean
  ListService listService;

  @Test
  void List_생성_API_테스트() throws Exception {
    //given
    long boardId = 1L;
    String name = "리스트 이름";
    // ListRequest
    ListRequest listRequest = new ListRequest();
    listRequest.setName(name);
    // ListDTO
    ListDTO listDTO = ListDTO.builder()
        .withId(1L)
        .withName(name)
        .withBoardId(boardId)
        .withCards(new ArrayList<>())
        .build();

    //when
    when(listService.createListInto(eq(boardId), any(ListRequest.class))).thenReturn(listDTO);

    //then
    MockHttpServletRequestBuilder requestBuilder = post(PRE_URI + "/board/{board_id}/list", boardId)
        .contentType(MediaType.APPLICATION_JSON)
        .content(asJsonString(listRequest));
    mockMvc.perform(requestBuilder)
        .andDo(print())
        .andDo(document("{class-name}/{method-name}",
            preprocessRequest(prettyPrint()),
            preprocessResponse(prettyPrint()),
            pathParameters(
                parameterWithName("board_id").description("리스트를 추가할 board의 DB id")
            ),
            requestFields(
                fieldWithPath("name").description("리스트의 name").type(JsonFieldType.STRING)
            ),
            responseFields(
                fieldWithPath("id").description("리스트의 DB id").type(JsonFieldType.NUMBER),
                fieldWithPath("name").description("리스트의 name").type(JsonFieldType.STRING),
                fieldWithPath("board_id").description("리스트가 속한 보드의 DB id")
                    .type(JsonFieldType.NUMBER),
                fieldWithPath("archived").description("리스트가 보관처리 되었는지 여부")
                    .type(JsonFieldType.BOOLEAN),
                fieldWithPath("archived_datetime").description("리스트 보관처리 일자").optional()
                    .type(JsonFieldType.STRING),
                fieldWithPath("cards").description("리스트의 카드목록(비어있음)").type(JsonFieldType.ARRAY)
            )
        ));
  }
}
