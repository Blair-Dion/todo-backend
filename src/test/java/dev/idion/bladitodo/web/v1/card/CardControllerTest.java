package dev.idion.bladitodo.web.v1.card;

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
import dev.idion.bladitodo.service.card.CardService;
import dev.idion.bladitodo.web.dto.CardDTO;
import dev.idion.bladitodo.web.dto.DTOContainer;
import dev.idion.bladitodo.web.dto.LogDTO;
import dev.idion.bladitodo.web.v1.card.request.CardMoveRequest;
import dev.idion.bladitodo.web.v1.card.request.CardRequest;
import java.util.StringJoiner;
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
@WebMvcTest(controllers = {CardController.class})
@DisplayName("v1 Card API 테스트")
class CardControllerTest {

  private static final String PRE_URI = "/v1";

  @Autowired
  MockMvc mockMvc;

  @MockBean
  CardService cardService;

  @Test
  void Card_생성_API_테스트() throws Exception {
    //given
    long boardId = 1L;
    long listId = 1L;
    long cardId = 4L;
    String title = "제목";
    String contents = "내용";
    String userId = "ksundong";
    String profileImageUrl = "https://avatars0.githubusercontent.com/u/38597469?s=88&u=4dec19ec378bfb64c9b4a00c4d63e7805dac9c6c&v=4";
    // CardRequest
    CardRequest cardRequest = new CardRequest();
    cardRequest.setTitle(title);
    cardRequest.setContents(contents);
    // CardDTO
    CardDTO cardDTO = CardDTO.builder()
        .withId(cardId)
        .withTitle(title)
        .withContents(contents)
        .withUserId(userId)
        .withProfileImageUrl(profileImageUrl)
        .withListId(listId)
        .build();
    // LogDTO
    LogDTO logDTO = LogDTO.builder()
        .withId(1L)
        .withType(LogType.CARD_ADD)
        .withFromListId(listId)
        .withToListId(listId)
        .withAfterTitle(title)
        .withAfterContents(contents)
        .build();
    // DTOContainer
    DTOContainer container = new DTOContainer(cardDTO, logDTO);

    //when
    when(cardService.createCardInto(eq(boardId), eq(listId), any(CardRequest.class)))
        .thenReturn(container);

    //then
    MockHttpServletRequestBuilder requestBuilder = post(
        PRE_URI + "/board/{board_id}/list/{list_id}/card", boardId, listId)
        .contentType(MediaType.APPLICATION_JSON)
        .content(asJsonString(cardRequest));
    mockMvc.perform(requestBuilder)
        .andDo(print())
        .andExpect(status().isCreated())
        .andDo(document("{class-name}/{method-name}",
            preprocessRequest(prettyPrint()),
            preprocessResponse(prettyPrint()),
            pathParameters(
                parameterWithName("board_id").description("카드가 속한 보드의 DB id"),
                parameterWithName("list_id").description("카드를 추가할 list의 DB id")
            ),
            requestFields(
                fieldWithPath("title").description("카드의 제목").type(JsonFieldType.STRING),
                fieldWithPath("contents").description("카드의 내용").type(JsonFieldType.STRING)
            ),
            responseFields(
                fieldWithPath("result_type").description("result의 DTO 타입")
                    .type(JsonFieldType.STRING),
                fieldWithPath("result.id").description("카드의 DB id").type(JsonFieldType.NUMBER),
                fieldWithPath("result.title").description("카드의 제목").type(JsonFieldType.STRING),
                fieldWithPath("result.contents").description("카드의 내용").type(JsonFieldType.STRING),
                fieldWithPath("result.user_id").description("카드를 생성한 user id")
                    .type(JsonFieldType.STRING),
                fieldWithPath("result.profile_image_url").description("카드 소유자의 프로필 이미지")
                    .type(JsonFieldType.STRING),
                fieldWithPath("result.list_id").description("카드가 속한 List의 DB id")
                    .type(JsonFieldType.NUMBER),
                fieldWithPath("result.archived").description("카드가 보관처리 되었는지 여부")
                    .type(JsonFieldType.BOOLEAN),
                fieldWithPath("result.archived_datetime").description("카드 보관처리 일자").optional()
                    .type(JsonFieldType.STRING),
                fieldWithPath("log.id").description("로그의 DB id").type(JsonFieldType.NUMBER),
                typeField("log.type"),
                fieldWithPath("log.before_title").description("이전 제목").type(JsonFieldType.STRING)
                    .optional(),
                fieldWithPath("log.after_title").description("이후 제목").type(JsonFieldType.STRING)
                    .optional(),
                fieldWithPath("log.before_contents").description("이전 내용").type(JsonFieldType.STRING)
                    .optional(),
                fieldWithPath("log.after_contents").description("이후 내용").type(JsonFieldType.STRING)
                    .optional(),
                fieldWithPath("log.from_list_id").description("이전 리스트의 DB id")
                    .type(JsonFieldType.NUMBER).optional(),
                fieldWithPath("log.to_list_id").description("이후 리스트의 DB id")
                    .type(JsonFieldType.NUMBER).optional()
            )
        ));
  }

  @Test
  void Card_수정_API_테스트() throws Exception {
    //given
    long boardId = 1L;
    long listId = 1L;
    long cardId = 4L;
    String title = "수정한 제목";
    String contents = "수정한 내용";
    String userId = "ksundong";
    String profileImageUrl = "https://avatars0.githubusercontent.com/u/38597469?s=88&u=4dec19ec378bfb64c9b4a00c4d63e7805dac9c6c&v=4";
    CardRequest cardRequest = new CardRequest();
    cardRequest.setTitle(title);
    cardRequest.setContents(contents);
    // CardDTO
    CardDTO cardDTO = CardDTO.builder()
        .withId(cardId)
        .withTitle(title)
        .withContents(contents)
        .withUserId(userId)
        .withProfileImageUrl(profileImageUrl)
        .withListId(listId)
        .build();
    // LogDTO
    LogDTO logDTO = LogDTO.builder()
        .withId(1L)
        .withType(LogType.CARD_TITLE_AND_CONTENT_UPDATE)
        .withFromListId(listId)
        .withToListId(listId)
        .withBeforeContents("제목")
        .withAfterTitle(title)
        .withBeforeContents("내용")
        .withAfterContents(contents)
        .build();
    // DTOContainer
    DTOContainer container = new DTOContainer(cardDTO, logDTO);

    //when
    when(cardService.updateCard(eq(boardId), eq(listId), eq(cardId), any(CardRequest.class)))
        .thenReturn(container);

    //then
    MockHttpServletRequestBuilder requestBuilder =
        put(PRE_URI + "/board/{board_id}/list/{list_id}/card/{card_id}", boardId, listId, cardId)
            .contentType(MediaType.APPLICATION_JSON)
            .content(asJsonString(cardRequest));
    mockMvc.perform(requestBuilder)
        .andDo(print())
        .andExpect(status().isOk())
        .andDo(document("{class-name}/{method-name}",
            preprocessRequest(prettyPrint()),
            preprocessResponse(prettyPrint()),
            pathParameters(
                parameterWithName("board_id").description("카드가 속한 보드의 DB id"),
                parameterWithName("list_id").description("수정할 카드의 list DB id"),
                parameterWithName("card_id").description("수정할 카드의 DB id")
            ),
            requestFields(
                fieldWithPath("title").description("수정할 제목").type(JsonFieldType.STRING),
                fieldWithPath("contents").description("수정할 내용").type(JsonFieldType.STRING)
            ),
            responseFields(
                fieldWithPath("result_type").description("result의 DTO 타입")
                    .type(JsonFieldType.STRING),
                fieldWithPath("result.id").description("카드의 DB id").type(JsonFieldType.NUMBER),
                fieldWithPath("result.title").description("카드의 수정된 제목").type(JsonFieldType.STRING),
                fieldWithPath("result.contents").description("카드의 수정된 내용")
                    .type(JsonFieldType.STRING),
                fieldWithPath("result.user_id").description("카드를 생성한 user id")
                    .type(JsonFieldType.STRING),
                fieldWithPath("result.profile_image_url").description("카드 소유자의 프로필 이미지")
                    .type(JsonFieldType.STRING),
                fieldWithPath("result.list_id").description("카드가 속한 List의 DB id")
                    .type(JsonFieldType.NUMBER),
                fieldWithPath("result.archived").description("카드가 보관처리 되었는지 여부")
                    .type(JsonFieldType.BOOLEAN),
                fieldWithPath("result.archived_datetime").description("카드 보관처리 일자").optional()
                    .type(JsonFieldType.STRING),
                fieldWithPath("log.id").description("로그의 DB id").type(JsonFieldType.NUMBER),
                typeField("log.type"),
                fieldWithPath("log.before_title").description("이전 제목").type(JsonFieldType.STRING)
                    .optional(),
                fieldWithPath("log.after_title").description("이후 제목").type(JsonFieldType.STRING)
                    .optional(),
                fieldWithPath("log.before_contents").description("이전 내용").type(JsonFieldType.STRING)
                    .optional(),
                fieldWithPath("log.after_contents").description("이후 내용").type(JsonFieldType.STRING)
                    .optional(),
                fieldWithPath("log.from_list_id").description("이전 리스트의 DB id")
                    .type(JsonFieldType.NUMBER).optional(),
                fieldWithPath("log.to_list_id").description("이후 리스트의 DB id")
                    .type(JsonFieldType.NUMBER).optional()
            )
        ));
  }

  @Test
  void Card_이동_API_테스트() throws Exception {
    //given
    long boardId = 1L;
    long listId = 1L;
    long destinationListId = 3L;
    long cardId = 3L;
    String title = "제목";
    String contents = "내용";
    String userId = "ksundong";
    String profileImageUrl = "https://avatars0.githubusercontent.com/u/38597469?s=88&u=4dec19ec378bfb64c9b4a00c4d63e7805dac9c6c&v=4";
    // CardMoveRequest
    CardMoveRequest cardMoveRequest = new CardMoveRequest();
    cardMoveRequest.setDestinationListId(destinationListId);
    // CardDTO
    CardDTO cardDTO = CardDTO.builder()
        .withId(cardId)
        .withTitle(title)
        .withContents(contents)
        .withUserId(userId)
        .withProfileImageUrl(profileImageUrl)
        .withListId(destinationListId)
        .build();
    // LogDTO
    LogDTO logDTO = LogDTO.builder()
        .withId(1L)
        .withType(LogType.CARD_MOVE)
        .withFromListId(listId)
        .withToListId(destinationListId)
        .build();
    // DTOContainer
    DTOContainer container = new DTOContainer(cardDTO, logDTO);

    //when
    when(cardService.moveCard(eq(boardId), eq(listId), eq(cardId), any(CardMoveRequest.class)))
        .thenReturn(container);

    //then
    MockHttpServletRequestBuilder requestBuilder =
        put(PRE_URI + "/board/{board_id}/list/{list_id}/card/{card_id}/move", boardId, listId,
            cardId)
            .contentType(MediaType.APPLICATION_JSON)
            .content(asJsonString(cardMoveRequest));
    mockMvc.perform(requestBuilder)
        .andDo(print())
        .andExpect(status().isOk())
        .andDo(document("{class-name}/{method-name}",
            preprocessRequest(prettyPrint()),
            preprocessResponse(prettyPrint()),
            pathParameters(
                parameterWithName("board_id").description("카드가 속한 보드의 DB id"),
                parameterWithName("list_id").description("카드가 속한 리스트 DB id"),
                parameterWithName("card_id").description("이동할 카드의 DB id")
            ),
            requestFields(
                fieldWithPath("destination_list_id").description("이동할 리스트의 DB id")
                    .type(JsonFieldType.NUMBER)
            ),
            responseFields(
                fieldWithPath("result_type").description("result의 DTO 타입")
                    .type(JsonFieldType.STRING),
                fieldWithPath("result.id").description("카드의 DB id").type(JsonFieldType.NUMBER),
                fieldWithPath("result.title").description("카드의 제목").type(JsonFieldType.STRING),
                fieldWithPath("result.contents").description("카드의 내용")
                    .type(JsonFieldType.STRING),
                fieldWithPath("result.user_id").description("카드를 생성한 user id")
                    .type(JsonFieldType.STRING),
                fieldWithPath("result.profile_image_url").description("카드 소유자의 프로필 이미지")
                    .type(JsonFieldType.STRING),
                fieldWithPath("result.list_id").description("카드가 속한 List의 DB id")
                    .type(JsonFieldType.NUMBER),
                fieldWithPath("result.archived").description("카드가 보관처리 되었는지 여부")
                    .type(JsonFieldType.BOOLEAN),
                fieldWithPath("result.archived_datetime").description("카드 보관처리 일자").optional()
                    .type(JsonFieldType.STRING),
                fieldWithPath("log.id").description("로그의 DB id").type(JsonFieldType.NUMBER),
                typeField("log.type"),
                fieldWithPath("log.before_title").description("이전 제목").type(JsonFieldType.STRING)
                    .optional(),
                fieldWithPath("log.after_title").description("이후 제목").type(JsonFieldType.STRING)
                    .optional(),
                fieldWithPath("log.before_contents").description("이전 내용").type(JsonFieldType.STRING)
                    .optional(),
                fieldWithPath("log.after_contents").description("이후 내용").type(JsonFieldType.STRING)
                    .optional(),
                fieldWithPath("log.from_list_id").description("이전 리스트의 DB id")
                    .type(JsonFieldType.NUMBER).optional(),
                fieldWithPath("log.to_list_id").description("이후 리스트의 DB id")
                    .type(JsonFieldType.NUMBER).optional()
            )
        ));
  }

  @Test
  void Card_삭제_API_테스트() throws Exception {
    //given
    long boardId = 1L;
    long listId = 1L;
    long cardId = 4L;

    //when

    //then
    mockMvc.perform(
        delete(PRE_URI + "/board/{board_id}/list/{list_id}/card/{card_id}", boardId, listId,
            cardId))
        .andDo(print())
        .andExpect(status().isNoContent())
        .andDo(document("{class-name}/{method-name}",
            preprocessRequest(prettyPrint()),
            preprocessResponse(prettyPrint()),
            pathParameters(
                parameterWithName("board_id").description("카드가 속한 보드의 DB id"),
                parameterWithName("list_id").description("보관할 카드의 list DB id"),
                parameterWithName("card_id").description("보관할 카드의 DB id")
            )));
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
