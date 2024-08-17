package com.ddot.springbootdevblog.service;

import com.ddot.springbootdevblog.domain.Article;
import com.ddot.springbootdevblog.dto.AddArticleRequest;
import com.ddot.springbootdevblog.dto.UpdateArticleRequest;
import com.ddot.springbootdevblog.repository.BlogRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class BlogService {
    private final BlogRepository blogRepository;

    // 블로그 글 추가 메서드
    public Article save(AddArticleRequest request) {
        return blogRepository.save(request.toEntity());
    }

    // 블로그 글 목록 조회
    public List<Article> findAll() {
        return blogRepository.findAll();
    }

    // 블로그 글 조회
    public Article findById(Long id) {
        return blogRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("not found: " + id));
    }

    // 블로그 글 삭제
    public void delete(Long id) {
        blogRepository.deleteById(id);
    }

    // 블로그 글 수정
    @Transactional
    // 트랜젝션 => 메칭된 메서드를 하나의 트랜잭션으로 묶음
    // DB 데이터 변경을 위해 묶는 작업의 단위
    public Article update(Long id, UpdateArticleRequest request) {
        Article article = blogRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found: " + id));

        article.update(request.getTitle(), request.getContent());

        return article;
    }

}
