package com.example.pracrawling.repository;

import com.example.pracrawling.entity.Law;
import com.example.pracrawling.entity.Paragraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParagraphRepository extends JpaRepository<Paragraph,Long> {
}
