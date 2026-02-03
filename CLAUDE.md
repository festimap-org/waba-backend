# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

**waba-backend-univ** - 대학 축제 관리 백엔드 서비스 (Spring Boot 3.5.3, Java 17)

축제 정보, 스탬프 투어, 주차 관리, 위젯 기반 콘텐츠 기능을 제공하는 REST API 서버.

## Build Commands

```bash
./gradlew clean build           # 빌드
./gradlew test                  # 전체 테스트
./gradlew test --tests "*.StampServiceTest"              # 특정 클래스 테스트
./gradlew test --tests "*.StampServiceTest.testMethod"   # 특정 메서드 테스트
./gradlew spotlessApply         # 코드 포맷팅 (Palantir Java Format)
./gradlew jacocoTestReport      # 커버리지 리포트 (build/reports/jacoco/test/html/)
```

## Architecture

```
src/main/java/com/halo/eventer/
├── domain/          # 비즈니스 도메인
│   ├── stamp/       # 스탬프 투어 (핵심 기능)
│   ├── festival/    # 축제 정보
│   ├── member/      # 회원 인증 (SUPER_ADMIN, AGENCY, VISITOR)
│   ├── map/         # 지도/위치
│   ├── widget/      # UI 위젯
│   ├── parking/     # 주차 관리
│   └── ...
├── global/          # 공통 모듈
│   ├── config/      # Spring 설정 (security/ 하위에 Security 관련)
│   ├── constants/   # SecurityConstants 등 상수
│   ├── security/    # JWT Provider, Filter, UserDetails
│   ├── error/       # BaseException, ErrorCode, GlobalExceptionHandler
│   └── common/      # BaseTime, PageInfo
└── infra/           # 외부 연동 (Naver SMS)
```

각 도메인은 `Controller → Service → Repository → Entity` 패턴을 따름.

## Security Architecture

JWT 기반 인증. `JwtProvider.getAuthentication()`에서 3가지 사용자 타입 분기:

| Role | JWT Subject | 조회 방식 |
|------|-------------|-----------|
| `ROLE_VISITOR` | memberId (Long) | `loadVisitorById()` |
| `STAMP` | uuid (String) | `loadUserByUuid()` - 레거시 StampUser |
| 기타 (ADMIN 등) | loginId (String) | `loadUserByUsername()` |

공개 API 경로: `global/constants/SecurityConstants.java`에 정의

## Member-StampUser 통합

기존 축제별 `StampUser` → 통합 `Member` 시스템으로 전환 완료:

- `Member`에 `MemberRole.VISITOR` 추가 (SUPER_ADMIN, AGENCY, VISITOR)
- `StampUser`에 `Member` 연관관계 추가 (하위 호환 유지)
- VISITOR 인증 API: `/api/v1/auth/login`, `/api/v1/auth/signup`

## Testing

- JUnit 5 + Mockito, AssertJ
- Fixture 패턴: `src/test/.../fixture/` 디렉토리에 테스트 데이터 정의
- 한글 메서드명 허용: `@SuppressWarnings("NonAsciiCharacters")`
- `@DataJpaTest` + H2 인메모리 DB

## Key Notes

- **Timezone**: `Asia/Seoul` (EventerApplication.java @PostConstruct)
- **Code Style**: 커밋 전 `./gradlew spotlessApply` 필수
- **Error Handling**: `BaseException` 상속, `ErrorCode` enum 사용 (`global/error/`)
- **API Docs**: `/swagger-ui.html`
- **Import Order**: java/jakarta → org → com.halo.eventer → 나머지 → static
