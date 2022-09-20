package com.example.pracrawling;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class LawDetailDto {
    //법령

    BasicInfo basicInfo;
    Article article;
    Addendum addendum;
    Amendment amendment;
    ReasonOfRevision reasonOfRevision;


        //법령키
    String key;
    @AllArgsConstructor
    @Getter
    @Builder
    static class BasicInfo{
        //기본정보
            //편장절관
            //제명변경여부
            //언어
            //한글법령여부
            //제개정구분
            //법령명_한글
            //전화번호
            //연락부서
                //부서단위
                    //소관부처코드
                    //부서연락처
                    //부서명
                    //부서키
                    //소관부처명
            //시행일자
            //공포법령여부
            //소관부처
                //소관부처코드
                //content
            //법령ID
            //공포번호
            //법령명_한자
            //법종구분
                //법종구분코드
                //c
            //공포일자
            //법령명약칭
            //별표편집여부
        private int hepaticJoint;
        private boolean isChange;
        private String languahe;
        private boolean isKorean;
        private String revision;
        private String koreaName;
        private String phoneNumber;
        private ArrayList<Contact> contact;
        @AllArgsConstructor
        @Getter
        @Builder
       static class Contact {
            private Unit departmentUnit;
            @AllArgsConstructor
            @Getter
            @Builder
         static class Unit{
                int code;
                String phoneNumber;
                String departmentName;
                int key;
                String name;
            }
        }
        private int effectiveDate;
        private boolean isEffective;

        private Ministries competentMinistries;
        @AllArgsConstructor
        @Getter
        @Builder
      static   class Ministries{
            private int code;
            private String content;
        }
        private String id;
        private int number;
        private String chineseName;
        private Classification classification;
        @AllArgsConstructor
        @Getter
        @Builder
       static class Classification{
            String code;
            String content;
        }
        private int date;
        private String abbreviation;

        private boolean isEdit;
    }
    @AllArgsConstructor
    @Getter
    @Builder
   static class Article{
        //조문
            //조문단위
                //리스트
                    //조문시행일자
                    //조문변경여부
                    //조문제목
                    //조문여부
                    //조문키
                    //조문번호
                    //조문이동이전
                    //조문이동이후
                    //조문내용
        ArrayList<ArticleDetail> details = new ArrayList<>();
        @AllArgsConstructor
        @Getter
        @Builder
       static class ArticleDetail{
            int date;
            boolean isChanged;
            String title;
            boolean isArticle;
            String key;
            int number;
            String moveBefore;
            String mobeAfter;
            String content;

        }

    }
    @AllArgsConstructor
    @Getter
    @Builder
    static class Addendum{
        //부칙
            //부칙단위
                //리스트
                    //부칙공포일자
                    //부칙키
                    //부칙공포번호
                    //부칙내용
        ArrayList<AddendumDetail> details = new ArrayList<>();
        @AllArgsConstructor
        @Getter
        @Builder
        static class AddendumDetail{
            int date;
            Long key;
            int number;
            ArrayList<String> content;
        }
    }

    @AllArgsConstructor
    @Getter
    @Builder
    static class Amendment{
        //개정문
            //개정문내용
        ArrayList<String> content;
    }

    @AllArgsConstructor
    @Getter
    @Builder
    static class ReasonOfRevision{
        //재개정이유
            //재개정이유내용
        ArrayList<String> content;
    }


}
