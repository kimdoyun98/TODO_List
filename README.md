# 정각에 해야지
 나만의 Todo List
 <br>


# 프로젝트 소개
 계획적이지 못한 MBTI P의 규칙적인 생활 및 공부를 위한 나만의 TODO LIST 앱이다.

일정을 등록하면 **해당 일정까지 남은 기간**을 알려주며

일일 루틴을 입력하여 **그 날, 그 시간에 알람**을 띄워 실행할 수 있도록 도와준다.
 <br>


# 개발 기간
 * 22년 09월 ~ 22년 10월 / 23년 12월 ~ 24년 01월 ( 리팩토링 및 기능 추가 )
 <br>

# Tech Stack
`Kotlin` `MVVM` `Jetpack` `AAC` `Databinding` `LiveData` `ViewModel` `Room DB` `Coroutine`
`Flow` `Navigation Component` `Alarm Manager` `WorkManager` `Hilt`
<br>


# 핵심 기능
### 1. 일정 관리 및 루틴 관리
- 오프라인에서도 사용할 수 있게 Room으로 구현
- Calendar View를 구현하여 Calendar를 통해 일정을 한 눈에 확인
- 완료한 루틴을 체크하여 수행하지 못한 루틴 파악 용이

### 2. 정해진 요일과 시간에 루틴 알림 기능
- WorkManager를 통해 매일 자정에 당일 루틴에 대한 알림을 AlarmManager에 등록

### 3. 위젯으로 당일 루틴 및 일정 확인

<br>

# Learn
* [AlarmManager](https://snaildeveloper.tistory.com/49)
* [Data Layer의 DataSource와 Repository](https://snaildeveloper.tistory.com/117)
* [Flow](https://snaildeveloper.tistory.com/130)

# ScreenShot
<p align="center" width="100%">
 <img 
  src="https://github.com/user-attachments/assets/5d407caa-425f-4afa-815c-bf20603d8c40"
  width="250"/>
 <img 
  src="https://github.com/user-attachments/assets/d925a774-1bdb-4867-a4b8-322624baa013"
  width="250"/>
  <img 
 src="https://github.com/user-attachments/assets/763ef7f5-a193-418e-a738-cafdaae2e06d" 
 width="250"/>
 </p>
<p align="center" width="100%">
<img 
 src="https://github.com/user-attachments/assets/b49ee4f9-71aa-44a1-8870-46aacc6fa662" 
 width="250"/>
 <img 
 src="https://github.com/user-attachments/assets/a47c5821-18fd-4151-9bb0-d618f30d8e7b" 
 width="250"/>
</p>

