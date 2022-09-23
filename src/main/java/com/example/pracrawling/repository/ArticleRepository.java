package com.example.pracrawling.repository;

import com.example.pracrawling.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article,String> {
}

