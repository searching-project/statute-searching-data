# Statute-Searching : 법령 검색 서비스
⚠ 프로젝트 기간 도중,  주제 변경 이슈로 중단한 법령 검색 서비스의 하위 Repository입니다.

- 초기 기획 및 중단된 이유 보러 가기 ⇒ [법령 검색 서비스 : Statute-Searching](https://github.com/searching-project/statute-searching)

<br>

### 1. Repository 설명
: Open API로 법령 데이터를 XML 양식으로 수집하고, 수집한 데이터를 Java 로직으로 가공하여 DB에 넣는 Spring Application

<br>

### 2. XML 데이터 파싱 방법

- 원본 데이터
     <div>
      <center><img src="https://user-images.githubusercontent.com/100582309/198091994-7c528a2c-b30e-47a9-912e-5e165ff9bdf4.png" height="170"></center>
      <center><img src="https://user-images.githubusercontent.com/100582309/198092145-67c1ef33-e6c4-42eb-a03b-397aee275f44.png" height="170"></center>     
      <center><img src="https://user-images.githubusercontent.com/100582309/198091648-03d1f02f-2984-4c0d-9135-e1581ac060ac.png" height="170"></center
             <div>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;▲ 파싱해야 했던 한 법령 XML 데이터의 구조(법령, 소관부처, 조, 항, 호, 부칙, 제개정문)</div>
    </div>

  <br>

- 파싱에 사용한 방법
    - 법령 : BufferedInputStream, InputStreamReader로 XML 데이터를 JSON Object로 얻어와 가공
    - 판례 : DocumentBuilderFactory로 XML 데이터를 파싱하여 NodeValue를 String으로 얻어와 가공
