# 🎪 FestiMap - Backend
축제의 디지털 전환을 위한 플랫폼 FestiMap의 백엔드 서버입니다. <br>
축제 방문객에게 실시간 정보, 지도 기반 편의 서비스, 스탬프 투어, 주차 정보 등을 제공하여
축제 운영과 방문 경험을 디지털로 개선합니다. 

## 주요 기능 예시
- 📍 축제 지도 기반 위치 서비스
- 🎟️ 스탬프 투어 시스템
- 🚗 주차장 정보 제공
- 📢 축제 공지 및 이벤트 관리
- 📊 운영 데이터 관리

--- 

## 🛠 Development Guidelines

**일관된 코드 품질과 유지보수성을 유지하기 위해 다음 규칙을 따릅니다.**


## 1️⃣ Code Style

**코드 포맷 규칙**

* 한 줄 최대 **120 characters**
* import 순서 정렬
* 파일 마지막 **빈 줄 1개**
* 불필요한 **공백 / 줄바꿈 제거**

**코드 포맷 검사**

```bash
./gradlew spotlessJavaCheck
```

**코드 포맷 자동 수정**

```bash
./gradlew spotlessJavaApply
```

개발자는 코드를 작성한 후 반드시 **코드 포맷 검사를 수행한 뒤 push** 해야 합니다.

---

## 2️⃣ Dependency Injection

**생성자 주입 사용**

필드 주입(Field Injection)은 사용하지 않습니다.

```java
@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

}
```

이유

* 테스트 용이성
* 불변성 보장
* Spring 권장 방식

---

## 3️⃣ Layer Responsibilities

다음과 같은 **계층 구조**를 따릅니다.

```text
Controller
   ↓
Service
   ↓
Repository
```

### Controller

* HTTP 요청 처리
* DTO validation
* Service 호출

```java
@PostMapping
public ResponseEntity<?> create(@Valid @RequestBody UserCreateRequest request)
```

---

### Service

* **비즈니스 로직 처리**
* 도메인 규칙 검증
* 트랜잭션 관리

```java
@Transactional
public User createUser(UserCreateRequest request)
```

Service는 **DTO 검증이 아니라 비즈니스 검증을 담당합니다.**

* 중복 사용자 확인
* 권한 검증
* 상태 검증

---

### Repository

* DB 접근 담당
* JPA Query / QueryDSL

---

## 4️⃣ Validation Rules

검증 책임은 다음과 같이 분리합니다.

### API Layer

DTO validation

```java
@NotNull
@Size(min = 1, max = 20)
private String name;
```

### Service Layer

비즈니스 규칙 검증

예

* 이미 존재하는 데이터
* 권한 체크
* 상태 검증

---

## 5️⃣ Database Rules

### JPA 규칙

* Lazy Loading 기본 사용
* N+1 발생 여부 확인
* 조회 API는 **DTO Projection 권장**

예

```java
select new UserResponse(u.id, u.name)
```

---

### Transaction 규칙

조회 API

```java
@Transactional(readOnly = true)
```

쓰기 API

```java
@Transactional
```

---

### DB 변경 관리

DB Schema 변경 시

1️⃣ 로컬에서 JPA update로 변경 확인
2️⃣ SQL migration 작성
3️⃣ migration 폴더에 기록

```text
database
 └ migrations
     ├ 2026-03-13-add-phone-column.sql
     └ 2026-03-20-create-order-table.sql
```

---

## 6️⃣ Test Rules

테스트 코드 작성 시 **Fixture 패턴**을 사용합니다.

Fixture는 테스트 코드의 **가독성과 재사용성을 높이기 위해 사용됩니다.**

예

```java
public static Duration 기간_엔티티() {
    Duration duration = Duration.of(festival, durationCreateDto);
    return duration;
}
```

Fixture 메서드는

* 한글 메서드명 사용
* 테스트 목적에 맞게 생성

---

### Controller Test 기본 구조

```java
@SuppressWarnings("NonAsciiCharacters")
@WebMvcTest(UserController.class)
@AutoConfigureRestDocs
@ActiveProfiles("test")
@Import({
    ControllerTestSecurityBeans.class,
    SecurityConfig.class,
    CustomRestDocsConfig.class
})
class UserControllerTest {
}
```

---

## 7️⃣ API Documentation

**Spring REST Docs + restdocs-api-spec** 기반으로 API 문서를 생성합니다.

문서 작성 예시

```java
document(
    "duration 생성",
    resource(ResourceSnippetParameters.builder()
        .tag(TAG)
        .summary("기간 생성")
        .description("축제 진행 날짜와 일차로 기간 생성")
        .build())
);
```

---

### API 문서 생성

```bash
./gradlew fixOpenApiExamples
```

또는

```bash
./gradlew build
```

문서 확인

```text
http://localhost:8080/docs/index.html
```

---

## 8️⃣ Local Development

로컬 개발 환경은 Docker 기반으로 구성합니다.

```bash
docker compose -f docker-compose.local.yml up -d
```

현재 구성

* MySQL
* Redis

