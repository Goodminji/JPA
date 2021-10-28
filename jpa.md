# JPA 기본 속성

**JPA(JAVA Persistence API)**

* 자바 ORM 기술에 대한 표준 명세, JAVA에서 제공하는 API
* 관계형 데이터베이스를 사용하는 방식을 정의한 인터페이스
* 기존 EJB에서 제공되던 엔티티 빈을 대체하는 기술
* 데이터를 객체지향적으로 관리할 수 있기 때문에 개발자는 비즈니스 로직에 집중할 수 있고 객체지향 개발이 가능하다.
* 객체를 통해 쿼리를 작성할 수 있는 JPQL(Java Persistence Query Language)를 지원
* ORM 이기 때문에 자바클래스와 DB 테이블을 매핑.’
* ORM이란 객체와 디비의 데이터를 자동으로 매핑(쿼리가 아니라 메서드로 데이터 조작 할 수 있고 객체간 관계를 바탕으로 SQL 자동 생성)

**JPA 동작순서**

findById()같은 경우는 엔티티를 영속성 컨텍스트에서 먼저 찾아보고 없으면 데이터베이스 찾는 반면 JPQL은 항상 데이터베이스에 SQL을 실행해서 결과를 조회한다. 그리고 아래와 같은 작업을 한다.

1. JPQL을 호출하면 데이터베이스에서 우선 조회해본다.
2. 조회한 값을 영속성컨텍스트에 저장한다.
3. 영속성 컨텍스트에서 조회할 때 이미 조회한 게 있다면 데이터를 린다.

**JPA의 Entity Id를 Long으로 사용하는 이유는?**

* 일단 primitive type이 아닌 Wrapper type인 Long을 사용해야 Null을 사용할 수 있다. 참고로 Wrapper 클래스는 참조형이다. 하이버네이트 공식 문서에도 Nullable한 값을 사용하라고 권장한다.

#### &#x20;JPA saveAll과 save 성능차이?

* save는 기존 트랜잭션이 존재할 때 이에 참여하긴 하지만 애초에 시작부터 @Transactional이 걸려있어서 프록시 로직을 타고 리소스가 더 크게 든다. 기존 트랜잭션이 없으면 트랜잭션이 생성됐다가 종료된다.

#### Maven과 Gradle이 뭐고, 둘의 차이는?

* java 코드와 프로젝트 내에 필요한 각종 xml, properties, jar 파일들을 JVM이나 WAS가 인식할 수 있도록 패키징하는 것이 빌드 과정인데 이런 일을 하는 빌드 자동화 도구이다. 여러 개의 라이브러리를 번거롭게 모두 다운받지 않고, 빌드 도구 설정파일에 필요한 라이브러리의 종류와 버전 등의 정보를 명시해서 자동으로 다운로드 해준다.둘 다 모듈 빌드를 병렬로 실행할 수 있지만 Gradle은 어떤 task가 업데이트 되었고 안되었고를 체크하기 때문에 incremental build를 허용한다. 이미 업데이트된 task에 대해서는 작업이 실행되지 않아서 빌드 시간이 훨씬 단축된다.

**JPA 설정**

* spring-boot-devtools 라이브러리를 추가하면, html 파일을 컴파일만 해주면 서버 재시작 없이 View 파일 변경이 가능하다
* /application.yml
  1. show\_sql : 옵션은 System.out 에 하이버네이트 실행 SQL을 남김
  2. org.hibernate.SQL : 옵션은 logger를 통해 하이버네이트 실행 SQL을 남김

**스프링 부트 신규 설정 (엔티티(필드) -> 테이블(컬럼))**

1. 카멜 케이스 -> 언더스코어(memberPoint -> member\_point)
2. .(점) -> \_(언더스코어)
3. 대문자 -> 소문자      &#x20;

**Spring-data-JPA**

* Jpa를 쉽게 사용하기 위해 스프링에서 제공하는 프레임워크
* 뻔한 코드들의 사용을 줄여주도록하는 인터페이스로 하이버네이트와 같은 JPA provider 필요.

### jpa 동작과정을 설명해주세요(저장할때)

jpa의 경우 트랜잭션 실행 단위안에서 동작한다. 객체를 생성하고 엔티티 매니저에 의해서 생성된 객체를 영속성 컨텍스트에 등록한다. 이때 해당 객체는 영속상태가 된다 즉 1차 캐시에 저장된다는 말. 또한 insert 쿼리는 쓰기지연 SQL저장소에 저장이된다. 트랜잭션이 끝나는 시점에 쓰기 지연 SQL 저장소에 있는 쿼리문들이 flush가 되고 트랜잭션 커밋이 된다.

### jpa 동작과정을 설명해주세요(수정할때) <a href="jpa" id="jpa"></a>

우선 수정할 엔티티를 찾는다. 이때 1차 캐시에 올라가고 영속상태가 된다. 즉 영속성 컨텍스트가 관리하는 상태가 된다. 그리고 set\~\~()으로 데이터를 수정한다. 이때 JPA는 데이터베이스 트랜잭션 커밋 시점에 내부적으로 flush가 되는데 영속성 컨텍스트 기능중 변경감지 기능이 있어서 이 것이 현재 1차 캐시의 엔티티와 최소 1차 캐시에 등록된 상태의 스냅샷과 비교해서 변경내역을 확인하고 update 쿼리를 쓰기지연 저장소에 저장하고 트랜잭션 커밋 끝나기 전에 해당 쿼리를 날리고 커밋을 완료한다.

출처 : [https://velog.io/@adam2/JPA%EB%8A%94-%EB%8F%84%EB%8D%B0%EC%B2%B4-%EB%AD%98%EA%B9%8C-orm-%EC%98%81%EC%86%8D%EC%84%B1-hibernate-spring-data-jpa](https://velog.io/@adam2/JPA%EB%8A%94-%EB%8F%84%EB%8D%B0%EC%B2%B4-%EB%AD%98%EA%B9%8C-orm-%EC%98%81%EC%86%8D%EC%84%B1-hibernate-spring-data-jpa)

[https://eocoding.tistory.com/83](https://eocoding.tistory.com/83)

[https://doorisopen.github.io/developers-library/Interview/2020-06-25-interview-jpa](https://doorisopen.github.io/developers-library/Interview/2020-06-25-interview-jpa)
