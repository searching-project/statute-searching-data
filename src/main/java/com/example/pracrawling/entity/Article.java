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
public class Article {

    //조문키
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long articleId;

    @Column(nullable = false)
    private String articleKey;

    //조문번호
    @Column(nullable = false)
    private int articleNum;

    // 법령ID
    @Column(nullable = false)
    private String lawSN;

    //조문 제목
    @Column
    private String title;
    //조문 내용

    @Column(nullable = false)
    private String content;

    //시행일자
    @Column
    private Date effectiveDate;
    //한시일자
//    @Column(nullable = false)
//    private String field10;
    //조문여부
    @Column
    private boolean articleYN;
    //조문이동이전
    @Column
    private String beforeMove;
    //조문이동이후
    @Column
    private String afterMove;
    //조문변경여부
    @Column
    private boolean changeYN;
    //조문참고자료
    @Column
    private String articleReference;

    public Article(LawDetailDto.Article.ArticleDetail articleDetail, String lawSN) throws ParseException {
        this.lawSN = lawSN;
        this.articleNum = articleDetail.getId();
        this.articleKey = articleDetail.getKey();
        this.title = articleDetail.getTitle();
        ArrayList<String> contents = articleDetail.getContent();
        StringBuilder temp = new StringBuilder();
        for (int i = 0; i < contents.size(); i++){
            temp.append(contents.get(i));
        }
        this.content = temp.toString();
//        this.effectiveDate = StringToDate(String.valueOf(articleDetail.getEffectiveDate()));
        this.articleYN = articleDetail.isArticleYN();
        this.beforeMove = articleDetail.getBeforeMove();
        this.afterMove = articleDetail.getAfterMove();
        this.changeYN = articleDetail.isChangeYN();
        this.articleReference = articleDetail.getReference();
    }

    public Date StringToDate(String str) throws ParseException {
        // 포맷터
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
        // 문자열 -> Date
        Date date = formatter.parse(str);
        return date;
    }
}
