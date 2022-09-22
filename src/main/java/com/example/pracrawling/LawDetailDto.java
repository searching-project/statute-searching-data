package com.example.pracrawling;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Set;

import static com.example.pracrawling.PublicMethod.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class LawDetailDto{
    //법령

    BasicInfo basicInfo;
    Article article;
    Addendum addendum;
    Amendment amendment;
    ReasonOfRevision reasonOfRevision;


    //법령키
    String key;


    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Builder
    static public class BasicInfo implements LawObject{

        @Override
        public BasicInfo update(JSONObject law){
            JSONObject contantJson;
            ArrayList<LawDetailDto.BasicInfo.Contact> contacts = new ArrayList<>();

            Object object = getOptional(ObjectsToJSonObject(law.get("연락부서")).keySet(),"부서단위",ObjectsToJSonObject(law.get("연락부서")));
            JSONArray objects = ObjectsToJSonArray(object);

            for(int i=0;i<objects.length();i++){
                contantJson = (JSONObject) objects.get(i);
                Contact contact = new Contact();
                contacts.add( contact.update(contantJson));
            }
            return LawDetailDto.BasicInfo.builder()
                    .hepaticJoint(law.getInt("편장절관"))
                    .isChange(law.getString("제명변경여부").equals("Y"))
                    .language(law.getString("언어"))
                    .isKorean(law.getString("한글법령여부").equals("Y"))
                    .revision(law.getString("제개정구분"))
                    .koreaName(law.getString("법령명_한글"))
                    .phoneNumber(ObjectToString(getOptional(law.keySet(),"전화번호",law)))
                    .contact(contacts)
                    .effectiveDate(law.getInt("시행일자"))
                    .isEffective(law.getString("공포법령여부").equals("Y"))
                    .competentMinistries(LawDetailDto.BasicInfo.Ministries.builder()
                            .code(getOptional(law.keySet(),"소관부처",law)==null?-1:law.getJSONObject("소관부처").getInt("소관부처코드"))
                            .content(getOptional(law.keySet(),"소관부처",law)==null?null:law.getJSONObject("소관부처").getString("content"))
                            .build())
                    .id(law.getString("법령ID"))
                    .number(law.getInt("공포번호"))
                    .chineseName((String) getOptional(law.keySet(),"법령명_한자",law))
                    .classification(LawDetailDto.BasicInfo.Classification.builder()
                            .code(law.getJSONObject("법종구분").getString("법종구분코드"))
                            .content(law.getJSONObject("법종구분").getString("content"))
                            .build())
                    .date(law.getInt("공포일자"))
                    .abbreviation(law.getString("법령명약칭"))
                    .isEdit(law.getString("별표편집여부").equals("Y"))
                    .build();
        }
        /**기본정보
            편장절관
            제명변경여부
            언어
            한글법령여부
            제개정구분
            법령명_한글
            전화번호
            연락부서
                부서단위
                소관부처코드
                부서연락처
                부서명
                부서키
                소관부처명
            시행일자
            공포법령여부
            소관부처
                소관부처코드
                content
            법령ID
            공포번호
            법령명_한자
            법종구분
                법종구분코드
                content
            공포일자
            법령명약칭
            별표편집여부
         **/
        private int hepaticJoint;
        private boolean isChange;
        private String language;
        private boolean isKorean;
        private String revision;
        private String koreaName;
        private String phoneNumber;
        private ArrayList<Contact> contact;

        @AllArgsConstructor
        @NoArgsConstructor
        @Getter
        @Builder
        static class Contact implements LawObject{
            private Unit departmentUnit;

            @AllArgsConstructor
            @NoArgsConstructor
            @Getter
            @Builder
            static class Unit {
                int code;
                String phoneNumber;
                String departmentName;
                int key;
                String name;
            }

            @Override
            public Contact update(JSONObject jsonObject) {
                return LawDetailDto.BasicInfo.Contact.builder()
                        .departmentUnit(LawDetailDto.BasicInfo.Contact.Unit.builder()
                                .departmentName(jsonObject.getString("소관부처명"))
                                .code(jsonObject.getInt("소관부처코드"))
                                .key(jsonObject.getInt("부서키"))
                                .name(jsonObject.getString("부서명"))
                                .phoneNumber(ObjectToString(jsonObject.get("부서연락처")))
                                .build())
                        .build();
            }
        }

        private int effectiveDate;
        private boolean isEffective;

        private Ministries competentMinistries;

        @AllArgsConstructor
        @NoArgsConstructor
        @Getter
        @Builder
        static class Ministries implements LawObject{
            private int code;
            private String content;

            @Override
            public Ministries update(JSONObject laws) {
                return null;
            }
        }

        private String id;
        private int number;
        private String chineseName;
        private Classification classification;

        @AllArgsConstructor
        @NoArgsConstructor
        @Getter
        @Builder
        static public class Classification implements LawObject{
            String code;
            String content;

            @Override
            public Classification update(JSONObject laws) {
                return null;
            }
        }

        private int date;
        private String abbreviation;

        private boolean isEdit;
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Builder
    static public class Article implements LawObject{
        @Override
        public Article update(JSONObject laws){
            Object object = laws.get("조문단위");
            // 배열로 변환
            JSONArray jsonArray = ObjectsToJSonArray(object);
            // 조문단위 리스트
            ArrayList<LawDetailDto.Article.ArticleDetail> detailArrayList = new ArrayList<>();
            for(int i=0;i<jsonArray.length();i++){
                JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                LawDetailDto.Article.ArticleDetail articleDetail = new ArticleDetail();
                detailArrayList.add(articleDetail.update(jsonObject));
            }
            return LawDetailDto.Article.builder()
                    .details(detailArrayList)
                    .build();
        }

        /**조문
            조문단위 [리스트]
                    조문시행일자
                    조문변경여부
                    조문제목
                    조문여부
                    조문키
                    조문번호
                    조문이동이전
                    조문이동이후
                    조문내용
         **/
        ArrayList<ArticleDetail> details = new ArrayList<>();

        @AllArgsConstructor
        @NoArgsConstructor
        @Getter
        @Builder
        static public class ArticleDetail implements LawObject{
            String key;
            String title;
            ArrayList<String> content;
            int effectiveDate;

            boolean isArticle;
            //조문키

            int id;
            String beforeMove;
            String afterMove;
            boolean changeYN;
            String reference;


            @Override
            public ArticleDetail update(JSONObject jsonObject) {
                Set<String> keys = jsonObject.keySet();
                ArrayList<String> contents = new ArrayList<>();
                Object contentObject = jsonObject.get("조문내용");
                JSONArray contentArray = ObjectsToJSonArray(contentObject);
                for (int j = 0; j < contentArray.length(); j++) {
                    contents.add(contentArray.get(j).toString());
                }
                this.effectiveDate = jsonObject.getInt("조문시행일자");
                this.changeYN = jsonObject.getString("조문변경여부").equals("Y");
                this.title = (String) getOptional(keys,"조문제목", jsonObject);
                this.isArticle = jsonObject.getString("조문여부").equals("Y");
                this.key = ObjectToString(jsonObject.get("조문키"));
                this.id = jsonObject.getInt("조문번호");
                this.beforeMove = jsonObject.getString("조문이동이전");
                this.afterMove = jsonObject.getString("조문이동이후");
                this.content = contents;
                return this;
            }

        }

    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Builder
    static class Addendum implements LawObject{
        /**부칙
            부칙단위 [리스트]
                부칙공포일자
                부칙키
                부칙공포번호
                부칙내용
         **/
        ArrayList<AddendumDetail> details = new ArrayList<>();

        @Override
        public Addendum update(JSONObject laws) {
            ArrayList<LawDetailDto.Addendum.AddendumDetail> detailArrayList = new ArrayList<>();
            Object object = laws.get("부칙단위");
            JSONArray jsonArray = ObjectsToJSonArray(object);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                LawDetailDto.Addendum.AddendumDetail addendumDetail = new LawDetailDto.Addendum.AddendumDetail();
                detailArrayList.add(addendumDetail.update(jsonObject));
            }
            return LawDetailDto.Addendum.builder()
                    .details(detailArrayList)
                    .build();
        }

        @AllArgsConstructor
        @NoArgsConstructor
        @Getter
        @Builder
        static class AddendumDetail implements LawObject{
            int date;
            Long key;
            String number;
            ArrayList<String> content;

            @Override
            public AddendumDetail update(JSONObject jsonObject) {
                ArrayList<String> contents = new ArrayList<>();
                JSONArray contentArray = ObjectsToJSonArray(jsonObject.get("부칙내용"));
                for(int j=0; j<contentArray.length();j++){
                    contents.add(contentArray.get(j).toString());
                }
                return LawDetailDto.Addendum.AddendumDetail.builder()
                        .date(jsonObject.getInt("부칙공포일자"))
                        .key(jsonObject.getLong("부칙키"))
                        .number(ObjectToString(jsonObject.get("부칙공포번호")))
                        .content(contents)
                        .build();
            }
        }
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Builder
    static class Amendment implements LawObject{
        //개정문
        //개정문내용
        ArrayList<String> content;

        @Override
        public Amendment update(JSONObject laws) {
            ArrayList<String> contents = new ArrayList<>();
            Object object = laws.get("개정문내용");
            JSONArray contentArray = ObjectsToJSonArray(object);

            for (int j = 0; j < contentArray.length(); j++) {
                contents.add(contentArray.get(j).toString());
            }
            return LawDetailDto.Amendment.builder()
                    .content(contents)
                    .build();
        }
    }
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Builder
    static class ReasonOfRevision implements LawObject{
        //재개정이유
        //재개정이유내용
        ArrayList<String> content;

        @Override
        public ReasonOfRevision update(JSONObject laws) {
            ArrayList<String> contents = new ArrayList<>();
            Object object = laws.get("제개정이유내용");
            JSONArray contentArray = ObjectsToJSonArray(object);
            for (int j = 0; j < contentArray.length(); j++) {
                contents.add(contentArray.get(j).toString());
            }

            return LawDetailDto.ReasonOfRevision.builder()
                    .content(contents)
                    .build();
        }
    }


}
