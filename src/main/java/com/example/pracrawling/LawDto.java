package com.example.pracrawling;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class LawDto {
    //법령일련번호
    String serialNumber;
    //현행연혁코드
    String historyCode;
    //법령명한글
    String name;
    //법령약칭명
    String abbreviation;
    //법령ID
    Long id;
    //공포일자
    String date;
    //공포번호
    String number;
    //제개정구분명

    //소관부처코드
    //소관부처명
    //법령구분명
    //시행일자
    //자법타법여부
    //법령상세링크
    String link;
}
