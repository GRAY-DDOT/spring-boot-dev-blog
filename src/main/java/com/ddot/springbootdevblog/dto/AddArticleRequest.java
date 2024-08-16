package com.ddot.springbootdevblog.dto;

import com.ddot.springbootdevblog.domain.Article;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class AddArticleRequest {

    private String title;
    private String content;

    // 빌더 패턴으로 DTO를 엔티티로 만드는 메서드
    // 글 추가 시 저장할 엔티티로 변환하는 용도
    public Article toEntity() {
        return Article.builder()
                .title(title)
                .content(content)
                .build();
    }
}
