package com.ddot.springbootdevblog.repository;

import com.ddot.springbootdevblog.domain.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlogRepository extends JpaRepository<Article, Long> {

}
