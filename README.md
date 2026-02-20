# Vue.js Backend Server

Spring Boot + MyBatis + MySQL 기반의 백엔드 서버

## 특징

- ✅ Spring Boot 3.2.1
- ✅ MyBatis XML Mapper
- ✅ MySQL 연결
- ✅ CORS 완전 개방
- ✅ Spring Security 방화벽 해제

## 프로젝트 구조

```
backend/
├── src/main/java/com/example/vuebackend/
│   ├── VueBackendApplication.java (Main 클래스)
│   ├── controller/
│   │   └── SampleController.java
│   ├── service/
│   │   └── SampleService.java
│   ├── mapper/
│   │   └── SampleMapper.java
│   ├── model/ (엔티티 클래스)
│   └── config/
│       ├── WebConfig.java (CORS 설정)
│       └── SecurityConfig.java (방화벽 해제)
├── src/main/resources/
│   ├── application.yml
│   └── mapper/sample/
│       └── SampleMapper.xml
└── build.gradle
```

## MySQL 설정

```yaml
url: jdbc:mysql://localhost:3306/testdb
username: root
password: 1234
```

필요한 경우 `application.yml` 파일에서 수정하세요.

## 실행 방법

```bash
./gradlew bootRun
```

## Vue.js 호출 예시

```javascript
axios.get("http://localhost:8080/api/now")
    .then(response => {
        console.log("현재 시간:", response.data);
    })
    .catch(error => {
        console.error("에러:", error);
    });
```

## API 엔드포인트

- **GET** `/api/now` - 데이터베이스의 현재 시간 반환

## 설정 파일

### build.gradle
- Spring Boot Web, MyBatis, MySQL Driver, Lombok, Spring Security 포함

### application.yml
- 포트: 8080
- MyBatis Mapper 위치: classpath:/mapper/**/*.xml
- 카멜케이스 자동 변환 활성화

### CORS 설정 (WebConfig.java)
- 모든 Origin 허용
- 모든 메서드 허용
- 모든 헤더 허용
- Credentials 허용

### Security 설정 (SecurityConfig.java)
- CORS 비활성화
- CSRF 비활성화
- 모든 요청 허가

## 추가 개발 가이드

### 새로운 Mapper 추가

1. `src/main/java/com/example/vuebackend/mapper/`에 인터페이스 생성
2. `src/main/resources/mapper/`에 XML 파일 생성
3. Service와 Controller에서 사용

### 엔티티 클래스 추가

`src/main/java/com/example/vuebackend/model/` 디렉토리에 클래스 추가

### 새로운 API 엔드포인트 추가

1. Controller에 메서드 추가
2. Service 로직 구현
3. Mapper에 쿼리 정의
