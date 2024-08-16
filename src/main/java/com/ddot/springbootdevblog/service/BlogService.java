package com.ddot.springbootdevblog.service;

import com.ddot.springbootdevblog.domain.Article;
import com.ddot.springbootdevblog.dto.AddArticleRequest;
import com.ddot.springbootdevblog.repository.BlogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class BlogService {
    private final BlogRepository blogRepository;

    //블로그 글 추가 메서드
    public Article save(AddArticleRequest request) {
        return blogRepository.save(request.toEntity());
    }

    //블로그 글 목록 조회
    public List<Article> findAll() {
        return blogRepository.findAll();
    }

    //블로그 글 조회
    public Article findById(Long id) {
        return blogRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("not found: " + id));
    }

}
