package com.example.pracrawling.entity;

import com.example.pracrawling.LawDetailDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.text.ParseException;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Ho {
    //호 ID
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long HoId;
    //항ID
    @Column(nullable = false)
    private Long paragraphId;

    //법령ID
    @Column(nullable = false)
    private String lawSN;

    //호 번호
    @Column
    private String hoNum;

    //호 내용
    @Lob
    @Column
    private String hoContent;

    public Ho(LawDetailDto.Article.ArticleDetail.ParagraphDetail.HoDetail hoDetail, String lawSN, Long paragraphId) throws ParseException {
        this.lawSN = lawSN;
        this.paragraphId = paragraphId;
        this.hoNum = hoDetail.getHoNum();
        this.hoContent = hoDetail.getHocontent();
    }
}
