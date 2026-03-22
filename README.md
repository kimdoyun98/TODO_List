# 정각에 해야지

<img width="1280" height="720" alt="Image" src="https://github.com/user-attachments/assets/0ad3d0a5-cc73-4a21-b617-340646c9eee2" />

## 프로젝트 소개
 계획적이지 못한 MBTI P의 규칙적인 생활 및 공부를 위한 나만의 TODO LIST 앱이다.

일정을 등록하면 **해당 일정까지 남은 기간**을 알려주며

일일 루틴을 입력하여 **그 날, 그 시간에 알람**을 띄워 실행할 수 있도록 도와준다.
 <br>
 <br>    

## 핵심 기능

### 1. 일정 관리 및 루틴 관리
- 서버 없이 오프라인 앱으로 사용하기 위해 Room으로 구현
- 루틴 진행 상황을 직관적으로 보여주기 위해 커스텀 구현
- 스케줄 등록 시 필수 입력 상황에 따라 Button Enabled 처리
- Calendar View를 구현하여 Calendar를 통해 스케줄을 한 눈에 확인

### 2. 정해진 요일과 시간에 루틴 알림 기능
- 자정마다 금일 루틴 초기화를 위한 작업을 위해 WorkManager 사용
- 루틴 시간에 정확한 알림을 위해 AlarmManager 사용

### 3. 위젯으로 당일 루틴 및 일정 확인
- 스케줄과 루틴 각각의 위젯 구현
<br>

## Tech Stack
`Kotlin` `App Architecture` `MVVM` `Jetpack` `AAC` `Databinding` `LiveData` `ViewModel` `Room DB` `Coroutine`
`Flow` `Navigation Component` `Alarm Manager` `WorkManager` `Hilt` `MPAndroidChart`
<br>    
<br>    

## 개발 기간
**프로젝트 기획 및 구현 (22.09 ~ 22.10)**
1. 일정 관리 및 루틴 관리
- 서버 없이 오프라인 앱으로 사용하기 위해 Room으로 구현
- 루틴 진행 상황을 직관적으로 보여주기 위해 커스텀 구현
- 스케줄 등록 시 필수 입력 상황에 따라 Button Enabled 처리
- Calendar View를 구현하여 Calendar를 통해 스케줄을 한 눈에 확인

2. 정해진 요일과 시간에 루틴 알림 기능
- 자정마다 금일 루틴 초기화를 위한 작업을 위해 WorkManager 사용
- 루틴 시간에 정확한 알림을 위해 AlarmManager 사용
  
**리팩토링 및 기능 추가 (25.01 ~ 26.02)**
- 위젯으로 당일 루틴 및 일정 확인(스케줄과 루틴 각각의 위젯 구현)
- CalenderView 라이브러리 제거 및 직접 구현
- LiveData에서 Flow로 리팩토링
- Schedule 추가 UI Fragment Navigation으로 구현
- Schedule 추가 UI Button, Flow를 통해 Enabled 상태 관리
- App Architecture로 멀티 모듈 리팩토링
- kapt에서 ksp로 이전
- 배터리 최적화 예외를 위해 WhiteList에 추가

<br>

## 앱 아키텍처
<img width="800" alt="Image" src="https://github.com/user-attachments/assets/f337b4f5-fc65-4527-946a-b33581ced49c" />

<br>
<br>

## Module Dependency Graph
<img width="1514" height="598" alt="Image" src="https://github.com/user-attachments/assets/988756cc-95d8-4719-a8a8-4dd21c52b3d1" />

<br>     
<br>

 ## 루틴 시간에 예약한 작업인 알림이 지연 또는 울리지 않는 현상
- 문제 정의
    - 루틴 등록과 함께 루틴 시간에 알림 예약
    - 등록 시점과 알림 시간이 가까운 경우 문제 없이 진행되었지만 시간이 길거나 다음 날로 넘어가는 경우 알림이 밀리거나 울리지 않는 현상 발생
- 해결 과정
    - AlarmManager의 setExactAndAllowWhileIdle 메서드의 경우 Doze모드를 깨워 작업을 수행하는 기능을 갖고 있다.
    - 하지만 Doze모드의 특성 상 반복적인 알람의 경우 제약이 생겨 시간이 길어지는 경우 알림이 밀리거나 울리지 않는 현상이 발생
    - OS의 제약을 벗어나기 위해선 배터리 최적화 예외가 필요하다.
- 결과
    - 배터리 최적화 예외를 위해 화이트 리스트에 등록
    - WorkManager와 AlarmManager를 SCHEDULE_EXACT_ALARM 권한과 함께 사용
    - 설정한 시간에 맞춰 Notification 알림

<br>    

## Learn
* ### [AlarmManager](https://snaildeveloper.tistory.com/49)
* ### [Data Layer의 DataSource와 Repository](https://snaildeveloper.tistory.com/117)
* ### [Flow로 Enable 검사하기](https://snaildeveloper.tistory.com/150)
* ### [Fragment](https://snaildeveloper.tistory.com/147)
* ### [Widget](https://snaildeveloper.tistory.com/149)
* ### [커스텀 목차 구현하기](https://snaildeveloper.tistory.com/146)
* ### [WorkManager에 대해서](https://snaildeveloper.tistory.com/154)

<br>

## ScreenShot
### 메인
<p align="center" width="100%">
 <img 
  src="https://github.com/user-attachments/assets/86974924-48d3-4075-af7f-c43bd7de9a64"
  width="240"/>
 <img 
  src="https://github.com/user-attachments/assets/907112c4-af31-414c-b8d2-7d9f7a3f9c99"
  width="240"/>
  <img 
 src="https://github.com/user-attachments/assets/3d7006f2-dc44-4498-a8df-54a1c3dbacd7" 
 width="240"/>
 <img 
 src="https://github.com/user-attachments/assets/975c42af-7287-4b71-956e-e2d7bb8e64f1" 
 width="240"/> 
</p>

### 스케줄 및 루틴 추가
<p align="center" width="100%">
 <img 
 src="https://github.com/user-attachments/assets/44fce508-7442-42fa-8d06-e79d113a6289" 
 width="240"/> 
<img 
 src="https://github.com/user-attachments/assets/7094054a-a730-4616-9844-bb719639e8a4" 
 width="240"/> 
 <img 
 src="https://github.com/user-attachments/assets/78d86044-3e2f-4dee-8093-92c5976d7866" 
 width="240"/> 
</p>

### 위젯
<img 
 src="https://github.com/user-attachments/assets/15649eb4-6cd6-4d5e-8fcf-cfdcc203f4b4" 
 width="240"/> 
