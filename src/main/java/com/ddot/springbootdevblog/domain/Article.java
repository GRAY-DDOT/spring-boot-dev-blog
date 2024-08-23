package com.ddot.springbootdevblog.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)

public class Article {
    // id 필드를 기본키(PK)로 지정
    @Id
    // 기본키 자동 1씩 증가
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false )
    private Long id;

    // 'title' not null 컬럼과 맵핑
    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "content", nullable = false)
    private String content;

    // 엔티티에 생성, 수정 시간 추가
    @CreatedDate    // 엔티티 생성 시 해당 시간
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @LastModifiedDate   // 엔티티 수정 시 해당 시간
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;


    @Builder // 빌더 패턴으로 객체 생성
    public Article(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }





/*  Getter 직접 생성
    protected Article() { // 기본 생성자
    }

    //Getter
    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }*/

}
