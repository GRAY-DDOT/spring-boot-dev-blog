package com.ddot.springbootdevblog.controller;

import com.ddot.springbootdevblog.domain.Article;
import com.ddot.springbootdevblog.dto.AddArticleRequest;
import com.ddot.springbootdevblog.repository.BlogRepository;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


@SpringBootTest //테스트용 애플리케이션 컨택스트
@AutoConfigureMockMvc
class BlogApiControllerTest {

    @Autowired
    protected MockMvc mockMvc;

    // 직렬화, 역 직렬화
    // Jackson lib
    @Autowired
    protected ObjectMapper objectMapper;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    BlogRepository blogRepository;


    @BeforeEach // 테스트 실행 전 실행
    public void mockMVCSetUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context)
                .build();
        blogRepository.deleteAll();
    }

    @DisplayName("addArticle: 블로그 글 추가 성공")
    @Test
    public void addArticle() throws Exception {
        // given
        // 블로그 글 추가에 필요한 요청 객체 생성
        final String url = "/api/articles";
        final String title = "title";
        final String content = "content";
        final AddArticleRequest userRequest = new AddArticleRequest(title, content);

        // 직렬화 : 객체 => JSON
        // writeValueAsString : 직렬화(serialization)
        final String requestBody = objectMapper.writeValueAsString(userRequest);

        // when
        // 설정한 내용을 바탕으로 블로그 글추가 API에 요청 전송
        // 요청 타입 : JSON ; given 절에서 생성한 객체를 요청의 본문으로 첨부
        // mockMvc  : HTTP 메서드, URL, 요청 본문, 요청 타입 등 설정, 설정 바탕으로 테스트 요청
        // contentType  : 요청 시 JSON, XML 등의 타입 중 골라 요청
        ResultActions result = mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestBody));

        // then
        // 응답 코드가 201 Created인지 확인
        // 블로그 전체를 조회해 크기가 1인지 확인
        // 실제 저장된 데이터와 요청 값 비교
        result.andExpect(status().isCreated());

        List<Article> articles = blogRepository.findAll();

        // 크기가 1
        assertThat(articles.size()).isEqualTo(1);
        // 배열의 0번 객체의 title 값이 title 인가?
        assertThat(articles.get(0).getTitle()).isEqualTo(title);
        // 배열의 0번 객체의 content 값이 content 인가?
        assertThat(articles.get(0).getContent()).isEqualTo(content);
    }

    @DisplayName("findAllArticles: 블로그 글 목록 조회 성공")
    @Test
    public void findAllArticles() throws Exception {
        final String url = "/api/articles";
        final String title = "title";
        final String content = "content";

        blogRepository.save(Article.builder()
                .title(title)
                .content(content)
                .build());

        //when
        final ResultActions resultActions = mockMvc.perform(get(url)
                .accept(MediaType.APPLICATION_JSON));

        //then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].content").value(content))
                .andExpect(jsonPath("$[0].title").value(title));
    }

    @DisplayName("findArticle: 블로그 글 조회 성공")
    @Test
    public void findArticle() throws Exception {
        // given
        // 블로그 글 저장
        final String url = "/api/articles/{id}";
        final String title = "title";
        final String content = "content";

        Article savedArticle = blogRepository.save(Article.builder()
                .title(title)
                .content(content)
                .build());

        // when
        // 목록 조회 API 호출
        final ResultActions resultActions =
                mockMvc.perform(get(url, savedArticle.getId()));

        // then
        // 응답 코드 200 OK, 반환 객체의 0번째 객체의 content, title이 저장된 값과 같은지 확인
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").value(content))
                .andExpect(jsonPath("$.title").value(title));

    }

    @DisplayName("deleteArticle: 블로그 글 삭제 성공")
    @Test
    public void deleteArticle() throws Exception {
        // given
        final String url = "/api/articles/{id}";
        final String title = "title";
        final String content = "content";

        Article savedArticle = blogRepository.save(Article.builder()
                .title(title)
                .content(content)
                .build());

        // when
        mockMvc.perform(delete(url, savedArticle.getId()))
                .andExpect(status().isOk());

        // then
        List<Article> articles = blogRepository.findAll();

        assertThat(articles).isEmpty();
    }
}