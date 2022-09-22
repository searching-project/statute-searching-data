package com.example.pracrawling.entity;

import com.example.pracrawling.LawDetailDto;
import lombok.SneakyThrows;

import javax.persistence.Column;
import javax.persistence.Id;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Article {
    //조문번호
    @Id
    private String articleNum;
    //조문키
    @Column(nullable = false)
    private String key;
    //조문 제목
    @Column(nullable = false)
    private String title;
    //조문 내용
    @Column(nullable = false)
    private String content;
    //시행일자
    @Column(nullable = false)
    private String effectiveDate;
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
    private String reference;

    public Article(LawDetailDto lawDetailDto){
        LawDetailDto.Article articleInfo = lawDetailDto.getArticle();
        this.articleNum = articleInfo.getDetails().;
        this.key = lawDetailDto.getKey();
        this.title = basicInfo.getKoreaName();
        this.content = basicInfo.getChineseName();
        this.effectiveDate = basicInfo.getAbbreviation();
        this.articleYN = basicInfo.getLanguage();
        this.beforeMove = basicInfo.isKorean();
        this.afterMove = basicInfo.getClassification().getContent();
        this.changeYN = basicInfo.getClassification().getCode();
        this.reference = basicInfo.isEffective();
        this.publishNumber = String.valueOf(basicInfo.getNumber());
        this.publishDate = StringToDate(String.valueOf(basicInfo.getDate()));
        this.effectiveDate= StringToDate(String.valueOf(basicInfo.getEffectiveDate()));
        this.changeYN = basicInfo.isChange();

    }

    public Date StringToDate(String str) throws ParseException {
        // 포맷터
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
        // 문자열 -> Date
        Date date = formatter.parse(str);
        return date;
    }
}
