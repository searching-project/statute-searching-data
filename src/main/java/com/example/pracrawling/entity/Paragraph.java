package com.example.pracrawling.entity;


import com.example.pracrawling.LawDetailDto;
import lombok.*;

import javax.persistence.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Paragraph {

    //항 ID
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long paragraphId;
    //조문ID
    @Column(nullable = false)
    private Long articleId;

    //법령ID
    @Column(nullable = false)
    private String lawSN;
    //항 번호
    @Column
    private String paragraphNum;
    //항 내용
    @Lob
    @Column
    private String paragraphContent;

    public Paragraph(LawDetailDto.Article.ArticleDetail.ParagraphDetail paragraphDetail, String lawSN, Long articleId) throws ParseException {
        this.lawSN = lawSN;
        this.articleId = articleId;
        this.paragraphNum = paragraphDetail.getParagraphNum();
        this.paragraphContent = paragraphDetail.getParagraphContent();
    }
}
