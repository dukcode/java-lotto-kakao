# 객체지향 설계 리팩토링

## 개요
README.md의 요구사항을 기반으로 로또 애플리케이션을 객체지향 설계 원칙에 따라 리팩토링했습니다.

## 주요 객체지향 설계 원칙 적용

### 1. 계층 분리 (Layered Architecture)
패키지 구조를 계층별로 명확히 분리하여 관심사의 분리를 달성했습니다.

- **domain**: 도메인 모델 및 비즈니스 규칙
  - `LottoNumber`, `LottoNumbers`, `WinningNumbers`, `Prize`, `GameResult` 등
- **service**: 비즈니스 로직 처리
  - `LottoService`: 로또 구매 관련 로직
  - `StatisticsService`: 통계 계산 로직
- **controller**: 사용자 요청 처리 및 흐름 제어
  - `LottoController`: 전체 게임 흐름 제어
- **view**: 입출력 처리
  - `InputConsoleView`: 사용자 입력 처리
  - `OutputConsoleView`: 결과 출력 처리
- **util**: 유틸리티 클래스
  - `Console`, `Input`: 콘솔 입출력 추상화

### 2. 단일 책임 원칙 (Single Responsibility Principle)
각 클래스가 하나의 명확한 책임만 가지도록 설계했습니다.

- **LottoService**: 로또 구매 금액 검증 및 로또 게임 생성만 담당
- **StatisticsService**: 당첨 통계 계산만 담당
- **InputConsoleView**: 사용자 입력 처리만 담당
- **OutputConsoleView**: 결과 출력만 담당
- **WinningNumbers**: 당첨 번호와 보너스 번호 관리 및 게임 결과 계산만 담당
- **WinningPrizes**: 당첨 등급별 개수 집계만 담당

### 3. 의존성 역전 원칙 (Dependency Inversion Principle)
구체적인 구현체가 아닌 추상화에 의존하도록 설계했습니다.

#### LottoNumbersGenerator 인터페이스
```java
public interface LottoNumbersGenerator {
    LottoNumbers generate();
}
```
- `RandomLottoNumbersGenerator`가 인터페이스를 구현
- `LottoService`는 인터페이스에 의존하여 다양한 생성 전략을 주입받을 수 있음
- 향후 테스트용 Mock 생성기나 다른 생성 전략 추가가 용이함

#### Input 인터페이스
```java
public interface Input {
    String readNext();
}
```
- `Console`이 인터페이스를 구현
- `InputConsoleView`는 인터페이스에 의존하여 테스트 시 Mock 객체 주입 가능
- 파일 입력, 네트워크 입력 등 다양한 입력 소스로 확장 가능

### 4. 의존성 주입 (Dependency Injection)
생성자를 통한 의존성 주입을 통해 결합도를 낮췄습니다.

- `LottoController`: `InputConsoleView`, `OutputConsoleView`, `LottoService`, `StatisticsService`를 생성자로 주입받음
- `LottoService`: `LottoNumbersGenerator`를 생성자로 주입받음
- `InputConsoleView`: `Input` 인터페이스를 생성자로 주입받음

이를 통해 테스트 용이성과 유연성이 향상되었습니다.

### 5. 캡슐화 (Encapsulation)
도메인 객체가 자신의 불변성을 보장하고 검증 로직을 내부에 캡슐화했습니다.

#### LottoNumber
- 숫자 범위 검증(1~45)을 생성자에서 수행
- 불변 객체로 설계 (final 필드)

#### LottoNumbers
- 6개 숫자 제한 및 중복 검증을 생성자에서 수행
- `countMatchingNumbers()` 메서드로 일치하는 숫자 개수 계산 책임을 가짐

#### WinningNumbers
- 보너스 번호가 당첨 번호와 중복되지 않도록 검증
- `calculateResult()` 메서드로 게임 결과 계산 책임을 가짐

### 6. 도메인 모델 중심 설계
DTO를 제거하고 의미 있는 도메인 객체로 대체했습니다.

- **제거된 DTO들**: `LottoNumberListDto`, `PreviousWinningNumbersDto`, `PurchaseAmountDto`, `PurchasedLottoCountDto`
- **도메인 객체로 대체**:
  - `PurchaseAmount`: 구매 금액을 나타내는 값 객체
  - `PurchaseGameAmount`: 구매할 게임 수를 나타내는 값 객체
  - `LottoGames`: 여러 로또 게임을 담는 컬렉션 객체
  - `GameResult`: 게임 결과를 나타내는 불변 객체 (record)
  - `Statistics`: 통계 정보를 나타내는 불변 객체 (record)

### 7. 불변성 (Immutability)
불변 객체를 적극 활용하여 부작용을 방지했습니다.

- **Record 타입 사용**: `GameResult`, `Statistics`, `PurchaseAmount`, `PurchaseGameAmount`, `LottoGames`
- **Final 필드**: 모든 도메인 객체의 필드를 final로 선언
- **불변 컬렉션**: 내부 상태를 외부에서 변경할 수 없도록 설계

### 8. 인터페이스 분리 원칙 (Interface Segregation Principle)
클라이언트가 필요로 하는 메서드만 포함하는 작은 인터페이스를 설계했습니다.

- `LottoNumbersGenerator`: 단일 메서드 `generate()`만 포함
- `Input`: 단일 메서드 `readNext()`만 포함

### 9. 명확한 네이밍
의미 있는 도메인 용어를 사용하여 코드의 가독성을 높였습니다.

- `PrizeStore` → `WinningPrizes`: 당첨 등급별 개수를 관리하는 의미가 더 명확함
- `PurchasedLottoCountDto` → `PurchaseGameAmount`: DTO 제거 및 의미 있는 도메인 객체로 변경
- `LottoNumbersList` → `LottoGames`: 컬렉션을 나타내는 더 적절한 이름

### 10. 열거형(Enum)을 활용한 상수 관리
매직 넘버와 상수를 Enum으로 관리하여 응집도를 높였습니다.

- **Prize Enum**: 당첨 등급, 일치 개수, 보너스 일치 개수, 상금을 하나의 Enum으로 관리
- `Prize.of(GameResult)` 메서드로 게임 결과에 따른 등급 판정 로직을 Enum 내부에 캡슐화

## 리팩토링 결과

### Before
- 모든 클래스가 루트 패키지에 위치
- DTO 클래스로 데이터 전달
- 구체적인 구현체에 직접 의존
- 검증 로직이 분산되어 있음

### After
- 계층별 패키지 구조로 명확한 책임 분리
- 도메인 객체 중심의 설계
- 인터페이스를 통한 추상화
- 도메인 객체 내부에 검증 로직 캡슐화
- 의존성 주입을 통한 느슨한 결합
- 불변 객체를 통한 안전한 상태 관리

## 테스트 가능성 향상
- `Input` 인터페이스를 통한 Mock 객체 주입으로 `InputConsoleView` 테스트 가능
- `LottoNumbersGenerator` 인터페이스를 통한 다양한 생성 전략 테스트 가능
- 의존성 주입을 통한 단위 테스트 작성 용이

## 확장성 향상
- 새로운 로또 번호 생성 전략 추가 용이 (예: 파일 기반, API 기반)
- 새로운 입력 소스 추가 용이 (예: 파일, 네트워크)
- 새로운 출력 형식 추가 용이 (예: 파일, 웹)
