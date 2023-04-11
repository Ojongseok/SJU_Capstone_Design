# 📋 23-1 세종대학교 캡스톤디자인

## 1~2주차
https://develop-oj.tistory.com/55

## 3~4주차
### 제안서 작성 및 기획안 발표   
* [문서] 파일 첨부 - 과제 제안서, 발표자료
### 주요 기능 정의
#### 작물의 병해 검출 기능   
* - [x] 진단 전 접근 권한 확인, 카메라/갤러리에서 이미지 첨부(Crop 필요) 및 작물 종류 선택
* - [x] 진단 결과(%) PieChart 구성 및 한 줄 요약 제공
* - [ ] 진단 결과 저장 및 게시판 공유 기능
* - [ ] 진단 결과를 바탕으로 추가 Open API 활용

#### 사용자 커뮤니티 기능
* - [x] 게시판 탭 질문 게시판, 노하우 게시판 분류
* - [ ] 게시글 목록 확인, 게시글 작성, 수정, 삭제 기능
* - [ ] 게시글별 댓글 작성
* - [ ] 게시글 추천/비추천 기능

#### 국가농작물병해충관리시스템(NCPMS) API 활용 (https://ncpms.rda.go.kr/npms/OpenApiInfo.np)
* 상세 기능 미정

### ~4주차 주요 구현화면
<img src="https://user-images.githubusercontent.com/98886487/230277439-3366642f-f2b7-4bae-a2fe-5408d2e599be.png" width="700" height="350" /> 

## 5~6주차
### 유스케이스(Use Case) 작성
* [문서] 파일 첨부 - 유스케이스

### 주요 기능 구체화
* NCPMS에서 제공하는 월별 병해충 발생정보, 작물별 도감정보 데이터 크롤링
* 하단 메뉴바 BottomSheet 구성
* NCPMS의 병 검색 서비스 OpenAPI 연결 및 UI 구성
* View, ViewModel, Model 분리 및 구조 설계

### ~6주차 주요 구현화면
<img src="https://user-images.githubusercontent.com/98886487/230278323-508615ca-3cad-4899-a0af-ae687242b83a.png" width="700" height="350" /> 

