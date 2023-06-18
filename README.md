# 🌿 [그로잇-Grow It!] 시설 작물 병해 검출 및 분류 플랫폼

![image 148](https://user-images.githubusercontent.com/98886487/236539788-8be66d56-a828-40f4-96cf-366ffbdaeb47.png)
프로젝트명 : [그로잇-Grow It!] 시설 작물 병해 검출 및 분류 플랫폼   
개발 기간 : 2023.03 ~ 2023.06 (4개월)  
팀원 및 역할 : Android 개발 1명, Server 개발 1명, 인공지능 모델 개발 및 학습 2명   

## 📝 Summary
* **2023-1 세종대학교 소프트웨어융합대학 캡스톤디자인 프로젝트**
* **2023-1 세종대학교 창의설계경진대회 [대상] 🎉**
* **주제 상세 설명**   
  * 해마다 전 세계 **식량작물의 약 40%가 병해충으로 인한 피해**를 입고 있어 실량부족 및 농업인에게 막대한 피해를 초래하고 있습니다. 또한,  젊은 세대에서도 **반려식물, 도시농업, 스마트팜**에 대한 인기가 높아지고 있어 **농업에 대한 지식이 깊지 않은 청년들**에게 편리하게 농작물 정보를 제공하기 위해 앱을 제작하였습니다.
* **요구사항 및 목표**   
  * **인공지능 모델을 활용**한 전문가의 개입 없이 농작물의 병해충을 판별할 수 있는 서비스 개발   
  * 농작물의 병해 진단에서 나아가 **플랫폼**을 목표로 추가적인 기능 구현   
  * 농작물 병해충, 농약 정보와 관련된 **Open API** 적극 활용   
  * 병해 진단뿐만 아니라 병해와 직접적으로 **연관된 병해 상세 정보를 제공**하고, 효율적인 방제를 위해 병해에 따른 **농약 사용가이드 제공**   
   
## 🛠️ Tech Stack
```Kotlin```, ```MVVM```, ```AAC```, ```SAA```, ```Repository```, ```ViewModel```, ```Coroutine```, ```JWT Token```, ```Retrofit2```, ```Multipart```, ```OkHttp```, ```Gson```, ```Glide```, ```Image-Cropper```, ```Navigation```, ```LiveData```, ```DataBinding```, ```DataStore```, ```Jsoup```, ```Tikxml```, ```Hilt```

## ✅ Main Function
### 1️⃣ 인공지능 모델을 활용한 작물 병해 검출 기능
<img src="https://user-images.githubusercontent.com/98886487/236542383-c0e765f9-810c-423d-94a3-6503b6addb6c.gif" width="200" height="400" /> <br>
* 진단 가능한 작물은 샘플 중 데이터셋이 가장 많은 **딸기, 상추, 고추, 토마토**를 선택했습니다.   
* 진단결과를 **PieChart와 한 줄 요약**으로 제공하며 해당 진단결과는 사용자가 **스크린샷으로 기기에 저장**하거나 커뮤니티에서 **다른 사용자들과 공유**가 가능합니다. 
* 이미지 촬영/선택 시 **Crop(자르기)** 기능을 지원하며 구현에는 `Image-Cropper` 라이브러리를 이용했습니다.   
* 서버와 이미지를 전달하는 방식으로 Retrofit2 - `Multipart` 를 통해 File을 전달합니다.   

### 2️⃣ 자유로운 소통을 위한 농업인 커뮤니티
<img src="https://github.com/Ojongseok/Grow-It/assets/98886487/aea6d7c3-f118-4355-8767-450014b2609f" width="200" height="400" /> <br>
* 농업인들이 본인만의 **경험이나 노하우를 공유**하고, 자유롭게 **질의응답**이 이루어질 수 있도록 **자체적인 커뮤니티**를 구성했습니다.   
* ‘AI 병해충 진단하기’ 기능에서 해결되지 않은 궁금증, 그 외 추가적인 내용은 **로그인** 후 게시판을 통해 해결할 수 있습니다.   
* **ViewModel**과 **LiveData, Observer** 패턴을 통해 **사용자 경험(UX)** 에 친숙할 수 있도록 집중했습니다.    
→ 댓글/답글 작성 및 게시글 추천 시 데이터 변경에 따른 화면 갱신, 직관적인 UI   
* **일반적인 커뮤니티의 요소, 기능 구현** → 게시판 항목 구분, 게시글 및 댓글, 답글(작성/수정/삭제), 답글, 추천   

### 3️⃣ OpenAPI를 활용한 병해 상세정보 및 농약 정보 제공
<img src="https://user-images.githubusercontent.com/98886487/236543272-42b211c7-3b7f-4445-bcca-15855f29dd1a.gif" width="200" height="400" /> <br>
* 국가농작물병해충관리시스템(NCPMS)의 **병해충 관련 OpenAPI**와 농약안전정보시스템의 **농약 등록정보 OpenAPI**를 이용해 병해/방제 정보를 제공합니다.   
  * 국가농작물병해충관리시스템(NCPMS) : https://ncpms.rda.go.kr/npms/Main.np   
  * 농약안전정보시스템 : https://psis.rda.go.kr/psis/index.ps   
* API의 데이터 반환 타입이 **XML**이기 때문에 `Tikxml` 라이브러리를 이용해 데이터를 파싱했습니다.   
 

## 🤔 Learned
* **이미지 선택 후 Crop(자르기)할 경우 화질저하 문제**   
  * 기존에 사용자가 선택한 이미지 파일을 **비트맵(Bitmap)** 으로 Crop(자르기)하여 불러오는 방식을 이용했습니다.
  * 이때, 이미지를 ImageView에 불러오는 과정에서 **화질이 크게 저하되는 것을 발견**했습니다.   
    → 이미지를 다시 **리사이즈**하는 과정에서 발생하는 것으로 추정
  * **Image-Cropper** 외부 라이브러리를 활용해 해결했습니다.

* **MVVM, Repository 아키텍쳐 설계 및 다양한 Jetpack 컴포넌트 도입**   
  * `View` 와 `Model` 그리고 `ViewModel` 을 보다 정확하게 **분리하기 위해 노력**했고, ViewModel에서 Model에 접근하기 위해 `Repository`  패턴도 적용해 보았습니다.   
  * `Navigation`, `LiveData`, `DataBinding`, `ViewModel` 같은 **Jetpack** 컴포넌트를 적극적으로 활용했습니다.   
  * 구글의 **Jetpack 사용 권장**, 개념적으로 이해하고 있던 **디자인 패턴**을 직접 프로젝트에 적용해 보며 이해를 높힐 수 있었습니다.   

* **정규표현식을 이용한 회원가입 유효성 검사 과정**
  * 회원가입 과정에서 이메일, 비밀번호 및 비밀번호 확인, 닉네임에 **TextInputLayout과 정규표현식**을 이용해 **유효성 검사**를 진행
  * **TextInputlayout**에 제공하는 **HelperText, EditText, Counter, EndIcon** 등 다양한 속성을 사용해 볼 수 있었습니다.

* **코루틴(Coroutine)과 비동기(asynchronous) 처리, LifeCycle**
  * ViewModel, Retrofit 통신 과정에서 **코루틴**을 사용해 **효율적인 비동기 처리**를 위해 노력했습니다.   
    → **ViewModel**에서 LiveData와 viewModelScope를 이용해 **Model**에 접근하고, **View**에서 `Observer` 패턴을 통해 **ViewModel**에 접근하는 방법을 적용했습니다.
  * **Fragment, ViewModel의 생명주기, viewModelScope**의 쓰임에 대해 공부했습니다.

* **서버와 Retrofit 통신 중 이미지(File) 전달을 위해 Multipart를 이용한 통신**   
  * 기존에 사용하던 **@Body, @Path** 만으로는 서버에 **File** 형식 전달이 불가능해 `@Multipart` 통신을 이용했습니다.
  * String(Uri)이 아닌 **File(Image)** 형식으로 이미지를 전달하는 이유는 서버측에서 이미지를 Uri로 저장하지 않고, AWS S3 버킷에 **이미지를 직접 저장**하기 위함.   
    → 서버로 **이미지를 전달하는 새로운 방법**에 대해 알게 되었습니다.

* **Hilt 마이그레이션**   
  * ViewModelFactory와 ViewModelProvider로 ViewModel을 생성하던 방식에서 `Hilt` 를 이용해 의존성을 주입할 수 있도록 마이그레이션했습니다.


## 🔗 Playstore   
https://play.google.com/store/apps/details?id=sju.sejong.capstonedesign


## 주차별 진행상황
|주차|내용|
|:---:|---|
|1-2|https://develop-oj.tistory.com/55|
|3-4|https://develop-oj.tistory.com/56|
|5-7|https://develop-oj.tistory.com/59|
|8|중간고사|
|9-10|https://develop-oj.tistory.com/63|
|11-12|https://develop-oj.tistory.com/65|
|13-14|진행 중|

