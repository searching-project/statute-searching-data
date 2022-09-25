package com.example.pracrawling.entity;

import com.example.pracrawling.LawDetailDto;
import lombok.*;

import javax.persistence.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Law {
    //법령ID
    @Id
    private String lawSN;
    //법령키
    @Column(length = 19)
    private String lawKey;
    //법령명
    @Column(nullable = false)
    private String koranName;
    @Column
    private String chineseName;
    //법령명약칭
    @Column
    private String shorterName;
    //언어
    @Column(nullable = false)
    private String language;
    //한글법령여부
    @Column
    private boolean koreanYN;
    //법종구분
    @Column(nullable = false,length = 16)
    private String type;
    //법종구분코드
    @Column(nullable = false,length = 18)
    private String typeCode;
    //공보법령여부
    @Column
    private boolean effectiveYN;
    //공포번호
    @Column(nullable = false, length = 32)
    private String publishNumber;
    //공포일자
    @Column(nullable = false)
    private Date publishDate;
    //시행일자
    @Column
    private Date effectiveDate;
    //제명 변경 여부
    @Column
    private boolean changeYN;

    @OneToMany(mappedBy = "law")
    private List<LawMinistry> lawMinistries = new ArrayList<>();

    public Law(LawDetailDto lawDetailDto) throws ParseException {
        LawDetailDto.BasicInfo basicInfo =lawDetailDto.getBasicInfo();
        this.lawSN = basicInfo.getId();
        this.lawKey = lawDetailDto.getKey();
        this.koranName = basicInfo.getKoreaName();
        this.chineseName = basicInfo.getChineseName();
        this.shorterName = basicInfo.getAbbreviation();
        this.language = basicInfo.getLanguage();
        this.koreanYN = basicInfo.isKorean();
        this.type = basicInfo.getClassification().getContent();
        this.typeCode = basicInfo.getClassification().getCode();
        this.effectiveYN = basicInfo.isEffective();
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
