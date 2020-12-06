package dev.idion.bladitodo.web.v1.board;

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

import dev.idion.bladitodo.service.board.BoardService;
import dev.idion.bladitodo.web.dto.BoardDTO;
import dev.idion.bladitodo.web.dto.CardDTO;
import dev.idion.bladitodo.web.dto.ListDTO;
import dev.idion.bladitodo.web.dto.UserDTO;
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

@AutoConfigureRestDocs(uriHost = "54.180.198.188/api", uriPort = 80)
@WebMvcTest(controllers = {BoardController.class})
@DisplayName("v1 Board API 테스트")
class BoardControllerTest {

  private static final String PRE_URI = "/v1";

  @Autowired
  MockMvc mockMvc;

  @MockBean
  BoardService boardService;

  @Test
  @DisplayName("v1 API board 테스트")
  void getBoardDTOTest() throws Exception {
    //given
    long boardId = 1L;
    BoardDTO boardDTO = makeBoard(boardId);

    //when
    when(boardService.findBoard(boardId)).thenReturn(boardDTO);

    //then
    mockMvc
        .perform(
            get(PRE_URI + "/board/{board_id}", boardId).contentType(MediaType.APPLICATION_JSON))
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
                fieldWithPath("lists[].cards[].pos").description("카드의 리스트에서의 순서")
                    .type(JsonFieldType.NUMBER),
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

  private BoardDTO makeBoard(Long boardId) {
    UserDTO user = UserDTO.builder()
        .withId(1L)
        .withUserId("ksundong")
        .withProfileImageUrl(
            "https://avatars0.githubusercontent.com/u/38597469?s=88&u=4dec19ec378bfb64c9b4a00c4d63e7805dac9c6c&v=4")
        .build();

    BoardDTO manualBoard = BoardDTO.builder()
        .withId(boardId)
        .withName("테스트 보드")
        .withLists(new ArrayList<>())
        .build();

    ListDTO list1 = ListDTO.builder().withId(1L).withName("TODO").withBoardId(manualBoard.getId())
        .withCards(new ArrayList<>()).build();
    ListDTO list2 = ListDTO.builder().withId(2L).withName("DOING").withBoardId(manualBoard.getId())
        .withCards(new ArrayList<>()).build();
    ListDTO list3 = ListDTO.builder().withId(3L).withName("DONE").withBoardId(manualBoard.getId())
        .withCards(new ArrayList<>()).build();

    CardDTO card1 = CardDTO.builder()
        .withId(1L)
        .withTitle("API 문서 작성하기")
        .withContents("mock API라도 문서는 필요하잖아요!")
        .withListId(list1.getId())
        .withUserId(user.getUserId())
        .withProfileImageUrl(user.getProfileImageUrl())
        .withPos(0)
        .build();
    CardDTO card2 = CardDTO.builder()
        .withId(2L)
        .withTitle("mock 작성하기")
        .withContents("mock API 잘 만들기")
        .withListId(list2.getId())
        .withUserId(user.getUserId())
        .withProfileImageUrl(user.getProfileImageUrl())
        .withPos(0)
        .build();
    CardDTO card3 = CardDTO.builder()
        .withId(3L)
        .withTitle("환경설정 하기")
        .withContents("환경설정 잘 하기")
        .withListId(list3.getId())
        .withUserId(user.getUserId())
        .withProfileImageUrl(user.getProfileImageUrl())
        .withPos(0)
        .build();

    list1.getCards().add(card1);
    list2.getCards().add(card2);
    list3.getCards().add(card3);

    manualBoard.getLists().add(list1);
    manualBoard.getLists().add(list2);
    manualBoard.getLists().add(list3);

    return manualBoard;
  }
}
