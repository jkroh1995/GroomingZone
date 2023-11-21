package tdd.groomingzone.board.freeboard;

import com.google.gson.Gson;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import tdd.groomingzone.board.freeboard.service.FreeBoardServiceImpl;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(FreeBoardController.class)
class FreeBoardControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private Gson gson;

    @MockBean
    private FreeBoardServiceImpl freeBoardService;

    @Test
    @DisplayName("정상적인 게시글 등록 요청 테스트")
    void postFreeBoardTest() throws Exception {
        // given
        String testTitle = "title";
        String testContent = "content";

        FreeBoardDto.Post testPost = new FreeBoardDto.Post();
        testPost.setTitle(testTitle);
        testPost.setContent(testContent);

        FreeBoardDto.Response testResponse = FreeBoardDto.Response
                .builder()
                .title(testTitle)
                .content(testContent)
                .build();

        String content = gson.toJson(testPost);

        given(freeBoardService.postFreeBoard(any())).willReturn(testResponse);

        // when // then
        mockMvc.perform(
                        post("/free-board")
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(content)
                ).andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value(testPost.getTitle()))
                .andExpect(jsonPath("$.content").value(testPost.getContent()));
    }
}