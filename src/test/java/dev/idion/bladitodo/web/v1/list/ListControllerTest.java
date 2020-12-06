package dev.idion.bladitodo.web.v1.list;

import static dev.idion.bladitodo.util.TestUtil.asJsonString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.delete;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.put;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import dev.idion.bladitodo.domain.log.LogType;
import dev.idion.bladitodo.service.list.ListService;
import dev.idion.bladitodo.web.dto.CardDTO;
import dev.idion.bladitodo.web.dto.DTOContainer;
import dev.idion.bladitodo.web.dto.ListDTO;
import dev.idion.bladitodo.web.dto.LogDTO;
import dev.idion.bladitodo.web.v1.list.request.ListRequest;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.StringJoiner;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.FieldDescriptor;
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
    long listId = 1L;
    long boardId = 1L;
    String name = "리스트 이름";

    ListRequest listRequest = makeListRequest(name);
    ListDTO listDTO = makeListDTO(boardId, listId, name);
    LogDTO listAddLog = LogDTO.builder()
        .withId(1L)
        .withType(LogType.LIST_ADD)
        .withToListId(listId)
        .withLogTime(LocalDateTime.now())
        .build();
    DTOContainer container = new DTOContainer(listDTO, listAddLog);

    //when
    when(listService.createListInto(eq(boardId), any(ListRequest.class))).thenReturn(container);

    //then
    MockHttpServletRequestBuilder requestBuilder = post(PRE_URI + "/board/{board_id}/list", boardId)
        .contentType(MediaType.APPLICATION_JSON)
        .content(asJsonString(listRequest));
    mockMvc.perform(requestBuilder)
        .andDo(print())
        .andExpect(status().isCreated())
        .andDo(document("{class-name}/{method-name}",
            preprocessRequest(prettyPrint()),
            preprocessResponse(prettyPrint()),
            pathParameters(
                parameterWithName("board_id").description("리스트를 추가할 board의 DB id")
            ),
            requestFields(
                getListRequestFieldDescriptors("리스트의 name")
            ),
            responseFields(fieldWithPath("result_type").description("result의 DTO 타입")
                .type(JsonFieldType.STRING))
                .and(getListDtoSnippetIgnoreCardDTO("result."))
                .and(
                    fieldWithPath("log.id").description("로그의 DB id").type(JsonFieldType.NUMBER),
                    typeField("log.type"),
                    fieldWithPath("log.before_title").description("이전 제목").optional()
                        .type(JsonFieldType.STRING),
                    fieldWithPath("log.after_title").description("이후 제목").optional()
                        .type(JsonFieldType.STRING),
                    fieldWithPath("log.before_contents").description("이전 내용").optional()
                        .type(JsonFieldType.STRING),
                    fieldWithPath("log.after_contents").description("이후 내용").optional()
                        .type(JsonFieldType.STRING),
                    fieldWithPath("log.from_list_id").description("이전 리스트의 DB id").optional()
                        .type(JsonFieldType.NUMBER),
                    fieldWithPath("log.to_list_id").description("이후 리스트의 DB id").optional()
                        .type(JsonFieldType.NUMBER),
                    fieldWithPath("log.log_time").description("로그 생성 시각").type(JsonFieldType.STRING)
                )
        ));
  }

  @Test
  void List_이름_수정_API_테스트() throws Exception {
    //given
    long listId = 1L;
    long boardId = 1L;
    String name = "리스트 이름 수정";

    ListRequest listRequest = makeListRequest(name);
    ListDTO listDTO = makeListDTOContainsCardDTO(boardId, listId, name);
    LogDTO listNameUpdateLog = LogDTO.builder()
        .withId(2L)
        .withType(LogType.LIST_RENAME)
        .withFromListId(listId)
        .withToListId(listId)
        .withLogTime(LocalDateTime.now())
        .build();
    DTOContainer container = new DTOContainer(listDTO, listNameUpdateLog);

    //when
    when(listService.updateListNameOf(eq(boardId), eq(listId), any(ListRequest.class)))
        .thenReturn(container);

    //then
    MockHttpServletRequestBuilder requestBuilder =
        put(PRE_URI + "/board/{board_id}/list/{list_id}", boardId, listId)
            .contentType(MediaType.APPLICATION_JSON)
            .content(asJsonString(listRequest));
    mockMvc.perform(requestBuilder)
        .andDo(print())
        .andExpect(status().isOk())
        .andDo(document("{class-name}/{method-name}",
            preprocessRequest(prettyPrint()),
            preprocessResponse(prettyPrint()),
            pathParameters(
                parameterWithName("board_id").description("리스트가 속한 board의 DB id"),
                parameterWithName("list_id").description("수정할 list의 DB id")
            ),
            requestFields(
                getListRequestFieldDescriptors("수정할 리스트의 name")
            ),
            responseFields(fieldWithPath("result_type").description("result의 DTO 타입")
                .type(JsonFieldType.STRING))
                .and(getListDtoSnippet("result."))
                .and(
                    fieldWithPath("log.id").description("로그의 DB id").type(JsonFieldType.NUMBER),
                    typeField("log.type"),
                    fieldWithPath("log.before_title").description("이전 제목").optional()
                        .type(JsonFieldType.STRING),
                    fieldWithPath("log.after_title").description("이후 제목").optional()
                        .type(JsonFieldType.STRING),
                    fieldWithPath("log.before_contents").description("이전 내용").optional()
                        .type(JsonFieldType.STRING),
                    fieldWithPath("log.after_contents").description("이후 내용").optional()
                        .type(JsonFieldType.STRING),
                    fieldWithPath("log.from_list_id").description("이전 리스트의 DB id").optional()
                        .type(JsonFieldType.NUMBER),
                    fieldWithPath("log.to_list_id").description("이후 리스트의 DB id").optional()
                        .type(JsonFieldType.NUMBER),
                    fieldWithPath("log.log_time").description("로그 생성 시각").type(JsonFieldType.STRING)
                )
        ));
  }

  @Test
  void List_보관_API_테스트() throws Exception {
    //given
    long listId = 1L;
    long boardId = 1L;
    String name = "리스트 이름";

    ListDTO listDTO = makeListDTOContainsCardDTO(boardId, listId, name);
    LogDTO listNameUpdateLog = LogDTO.builder()
        .withId(3L)
        .withType(LogType.LIST_ARCHIVE)
        .withFromListId(listId)
        .withLogTime(LocalDateTime.now())
        .build();
    DTOContainer container = new DTOContainer(listDTO, listNameUpdateLog);

    //when
    when(listService.archiveList(eq(boardId), eq(listId))).thenReturn(container);

    //then
    mockMvc.perform(delete(PRE_URI + "/board/{board_id}/list/{list_id}", boardId, listId))
        .andDo(print())
        .andExpect(status().isOk())
        .andDo(document("{class-name}/{method-name}",
            preprocessRequest(prettyPrint()),
            preprocessResponse(prettyPrint()),
            pathParameters(
                parameterWithName("board_id").description("리스트가 속한 board의 DB id"),
                parameterWithName("list_id").description("수정할 list의 DB id")
            ),
            responseFields(fieldWithPath("result_type").description("result의 DTO 타입")
                .type(JsonFieldType.STRING))
                .and(getListDtoSnippet("result."))
                .and(
                    fieldWithPath("log.id").description("로그의 DB id").type(JsonFieldType.NUMBER),
                    typeField("log.type"),
                    fieldWithPath("log.before_title").description("이전 제목").optional()
                        .type(JsonFieldType.STRING),
                    fieldWithPath("log.after_title").description("이후 제목").optional()
                        .type(JsonFieldType.STRING),
                    fieldWithPath("log.before_contents").description("이전 내용").optional()
                        .type(JsonFieldType.STRING),
                    fieldWithPath("log.after_contents").description("이후 내용").optional()
                        .type(JsonFieldType.STRING),
                    fieldWithPath("log.from_list_id").description("이전 리스트의 DB id").optional()
                        .type(JsonFieldType.NUMBER),
                    fieldWithPath("log.to_list_id").description("이후 리스트의 DB id").optional()
                        .type(JsonFieldType.NUMBER),
                    fieldWithPath("log.log_time").description("로그 생성 시각").type(JsonFieldType.STRING)
                )
        ));
  }

  private ListRequest makeListRequest(String name) {
    ListRequest listRequest = new ListRequest();
    listRequest.setName(name);
    return listRequest;
  }

  private ListDTO makeListDTO(Long boardId, Long listId, String name) {
    return ListDTO.builder()
        .withId(listId)
        .withName(name)
        .withBoardId(boardId)
        .withCards(new ArrayList<>())
        .build();
  }

  private ListDTO makeListDTOContainsCardDTO(Long boardId, Long listId, String name) {
    return ListDTO.builder()
        .withId(listId)
        .withName(name)
        .withBoardId(boardId)
        .withCards(Lists.list(
            CardDTO.builder()
                .withId(1L)
                .withTitle("API 문서 작성하기")
                .withContents("mock API라도 문서는 필요하잖아요!")
                .withListId(listId)
                .withUserId("userid")
                .withProfileImageUrl("imageUrl")
                .withPos(0)
                .build()
        ))
        .build();
  }

  private FieldDescriptor[] getListRequestFieldDescriptors(String name) {
    return new FieldDescriptor[]{
        fieldWithPath("name").description(name).type(JsonFieldType.STRING)
    };
  }

  private FieldDescriptor[] getListDtoSnippet(String prefix) {
    return new FieldDescriptor[]{
        fieldWithPath(prefix + "id").description("리스트의 DB id").type(JsonFieldType.NUMBER),
        fieldWithPath(prefix + "name").description("리스트의 name").type(JsonFieldType.STRING),
        fieldWithPath(prefix + "board_id").description("리스트가 속한 보드의 DB id")
            .type(JsonFieldType.NUMBER),
        fieldWithPath(prefix + "archived").description("리스트가 보관처리 되었는지 여부")
            .type(JsonFieldType.BOOLEAN),
        fieldWithPath(prefix + "archived_datetime").description("리스트 보관처리 일자").optional()
            .type(JsonFieldType.STRING),
        fieldWithPath(prefix + "cards").description("리스트의 카드목록").type(JsonFieldType.ARRAY),
        fieldWithPath(prefix + "cards[].id").description("카드의 DB id").type(JsonFieldType.NUMBER),
        fieldWithPath(prefix + "cards[].title").description("카드의 제목").type(JsonFieldType.STRING),
        fieldWithPath(prefix + "cards[].contents").description("카드의 내용").type(JsonFieldType.STRING),
        fieldWithPath(prefix + "cards[].pos").description("카드의 리스트에서의 순서").type(
            JsonFieldType.NUMBER),
        fieldWithPath(prefix + "cards[].user_id").description("카드를 생성한 user id")
            .type(JsonFieldType.STRING),
        fieldWithPath(prefix + "cards[].profile_image_url").description("카드 소유자의 프로필 이미지")
            .type(JsonFieldType.STRING),
        fieldWithPath(prefix + "cards[].list_id").description("카드가 속한 List의 DB id")
            .type(JsonFieldType.NUMBER),
        fieldWithPath(prefix + "cards[].archived").description("카드가 보관처리 되었는지 여부")
            .type(JsonFieldType.BOOLEAN),
        fieldWithPath(prefix + "cards[].archived_datetime").description("카드 보관처리 일자").optional()
            .type(JsonFieldType.STRING),
    };
  }

  private FieldDescriptor[] getListDtoSnippetIgnoreCardDTO(String prefix) {
    return new FieldDescriptor[]{
        fieldWithPath(prefix + "id").description("리스트의 DB id").type(JsonFieldType.NUMBER),
        fieldWithPath(prefix + "name").description("리스트의 name").type(JsonFieldType.STRING),
        fieldWithPath(prefix + "board_id").description("리스트가 속한 보드의 DB id")
            .type(JsonFieldType.NUMBER),
        fieldWithPath(prefix + "archived").description("리스트가 보관처리 되었는지 여부")
            .type(JsonFieldType.BOOLEAN),
        fieldWithPath(prefix + "archived_datetime").description("리스트 보관처리 일자").optional()
            .type(JsonFieldType.STRING),
        fieldWithPath(prefix + "cards").description("리스트의 카드목록").type(JsonFieldType.ARRAY)
    };
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
