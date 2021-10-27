# JPA Persistence Context

**영속성**

* 데이터를 생성한 프로그램이 종료되어도 사라지지 않는 데이터의 특성

**JPA 영속성**

* 엔티티가 영속성 컨텍스트에 포함되어 있느냐 아니냐.
*   JPA의 엔티티 매니저가 활성화된 상태로 트랜잭션(@Transactional) 안에서 DB에서 데이트를 가져오면 이 데이터는 영속성으로 컨텍스트가 유지된 상태이다. 이때 해당 데이터 값을 변경 하면 트랜잭션이 끝나는 시점에 해당 테이블을 변경 내용을 반영하게 된다. 따라서 update 쿼리를 날릴 필요가 없어서 이 개념을 더티 체킹이라고 한다.

    &#x20;Ex) ItemService.upteItem , OrderService. cancelOrder 메소드 참고

```
@Transactional
       public void upteItem(Long itemId, String name, int price , int stockQuantity) { // 서비스 계층에서 필요한 파라미터 나 DTO 로 만들어서 Update 하는게 좋음
               // Item findItem는 영속상태이므로 @Transactional로 인해서 update commit한다(변경감지)
               Item findItem = itemRepository.findOne(itemId);
               //findItem.change(price,name,stockQunitity) 메소드를 따로 만들어서 사용하는게 더 좋음
               findItem.setPrice(price);
               findItem.setName(name);
               findItem.setStockQuantity(stockQuantity);
-      }
```

* 영속 컨텍스트: 엔티티를 담고 있는 집합. JPA는 영속 컨텍스트에 속한 엔티티를 DB에 반영한다. 엔티티를 검색, 삭제, 추가 하게 되면 영속 컨텍스트의 내용이 DB에 반영된다. 영속 컨텍스트는 직접 접근이 불가능하고 Entity Manager를 통해서만 접근이 가능하다.
* merge
  1. 준영속 엔티티의 식별자 값으로 영속 엔티티를 조회한다.
  2. 영속 엔티티의 값을 준영속 엔티티의 값으로 모두 교체한다.(병합한다.)
  3. 트랜잭션 커밋 시점에 변경 감지 기능이 동작해서 데이터베이스에 UPDATE SQL이 실행 > 주의: 변경 감지 기능을 사용하면 원하는 속성만 선택해서 변경할 수 있지만, 병합을 사용하면 모든 속성이 변경된다. 병합시 값이 없으면 null 로 업데이트 할 위험도 있다. (병합은 모든 필드를 교체한다.

**엔티티의 생명주기**

* 비영속 : 영속성 컨텍스트와 전혀 관계가 없는 상태(객체만 생성한 상태)
* 영속 : 영속성 컨텍스트에 저장된 상태(DB쿼리 반영 전)
* 준영속 : 영속성 컨텍스트에 저장되었다가 분리된 상태
* 삭제 : 삭제된 상태

**JPA 1차개시와 동일성(identity) 보장 – 캐싱기능**

* 동일성 보장\
  1차 캐시에 이미 있는 엔티티라면 해당 엔티티를 그대로 반환해서 여러 번 조회했을 때의 동일성을 보장한다.
* JPA 1차캐시: 같은 트랜잭션 안에서 같은 엔티티를 반환 (SQL 한번만 실행) , 해당 스레드 하나가 시작할때 부터 끝날때 까지 잠깐 쓰는거다. 공유하지 않는 캐시다\
  Ex) MemberRepository2Test

```
@Test
         @Transactional
         //@Rollback(false)
         public void testMember() throws Exception {
                  //given
                  Member member = new Member();
                  member.setName("memberA");
                 
                  //when
                  // 1. 1차 캐시에 저장
                  Long saveId = memberRepository.save(member);
                  // 2. 1차 캐시에서 조회
                  Member findMember = memberRepository.find(saveId);            
                 
                  //then
                  Assertions.assertThat(findMember.getId()).isEqualTo(member.getId());
              Assertions.assertThat(findMember.getName()).isEqualTo(member.getName());
                  Assertions.assertThat(findMember).isEqualTo(member);
                  /*
                   * 영속성때문에 같은 트랜잭션으로 같은 식별자로 같은 엔티티로 인식하기 때문에 같은것으로 인식함(select 쿼리도 날리지 않음)
                   *
                   * 영속성 컨텍스트는 내부에 캐시를 갖고 있는데 이게 1차 캐시
                   *
                   * 엔티티 생성 후 EM에 persist() 하게 되면 이 영역(영속성 컨텍스트) 에 저장됨
                   *
                   * em.find()를 통해 데이터 조회 시 1차 캐시에서 먼저 찾고 없으면 DB에서 찾은 후 1차 캐시에 보관 후 반환
                   */
 
 
         }
 
 
```

**트랜잭션을 지원하는 쓰기 지연 – 버퍼링 기능**

* 트랜잭션 커밋 할때까지 sql 메모리에 쌓아서 한번에 전송.

**지연로딩(Lazy Loading)**

* 가짜 객체를 조회했다가 객체가 실제로 사용 될떄 로딩, 근데 1차 캐시 이전에 조회한 기록이 있으면 그 실제 인테티를 가져 온다.(모든 연관관계는 지연로딩으로 설정을 하고 필요할 경우에만 변경하도록)

**즉시로딩**

* JOIN SQL로 한번에 연관된 객체까지 미리 조회

**N+1 문제**

* 하나의 쿼리를 수행하는데 N개의 쿼리가 더 수행된다는 의미.
* &#x20;즉시로딩일때 연관된  객체까지 가져오므로 예를 들어 10명의 member를 조회하기 위해 select 쿼리문 1개로 10개 데이터를 조회했는데 각각 team 정보를 알기 위team 테이블 쿼리문이 10개 또 나가는 상황
* 지연로딩일 때 member 1명 조회하면 team 테이블에서도 team select 쿼리문이 1명 나가는데 member 여러명 조회하게 되면 각 멤버에 대해 team 조회하는 sql 하나씩 나가서 N+1문제 발생.
*   해결 방법 - 페치 조인 사용하기

    @Query("select m from Member m left join fetch m.orders")와 같이 적으면 fetch를 사용해서 조인쿼리를 수행한다. fetch 키워드는 연관 객체나 컬렉션을 한 번에 같이 조회하게 한다. 즉, 페치 조인을 사용하면 연관 엔티티는 프록시가 아닌 실제 엔티티를 조회하게 되고 이로써 연관 객체까지 한번의 쿼리로 다 가져올 수 있다. N번 실행하지 않게 된다.

**Flush()**

* 플러시는 영속성 컨텍스트의 변경 내용을 데이터베이스에 반영한다.
* 트랜잭션 커밋이 일어날 때 플러시가 동작하는데, 쓰기 지연 저장소에 쌓아 놨던 INSERT, UPDATE, DELETE SQL들이 데이터베이스에 날라간다.
* 쉽게 얘기해서 영속성 컨텍스트의 변경 사항들과 데이터베이스를 싱크하는 작업이다.
* &#x20;flush는 영속성 컨텍스트에 있는 엔티티 정보를 DB에 동기화를 하는 작업이며,아직 트랜잭션 commit이 안됐기 때문에, 에러가 발생할 경우 롤백 할 수 있다. 반면, 트랜잭션이 commit이 된 이후에는 DB에 동기화된 정보는 영구히 반영되는, 즉 롤백 할 수 없는 상태가 되는 것이다.
* 트랜잭션 커밋시 플러시 자동 호출
* JPQL 쿼리 실행하면 플러시 자동 호출
* 동작순
  1. 변경을 감지한다. Dirty Checking.
  2. 수정된 엔티티를 쓰기 지연 SQL 저장소에 등록한다.(1차 캐시 삭제되지 않는다.)
  3. 쓰기 지연 SQL 저장소의 쿼리를 데이터베이스에 전송한다.(등록, 수정, 삭제 SQL)
  4. 플러시가 발생한 다고 커밋이 이루어지는게 아니고, 플러시 다음에 커밋이 일어난다.

출처 : [https://velog.io/@adam2/JPA%EB%8A%94-%EB%8F%84%EB%8D%B0%EC%B2%B4-%EB%AD%98%EA%B9%8C-orm-%EC%98%81%EC%86%8D%EC%84%B1-hibernate-spring-data-jpa](https://velog.io/@adam2/JPA%EB%8A%94-%EB%8F%84%EB%8D%B0%EC%B2%B4-%EB%AD%98%EA%B9%8C-orm-%EC%98%81%EC%86%8D%EC%84%B1-hibernate-spring-data-jpa)

[https://eocoding.tistory.com/83](https://eocoding.tistory.com/83)

출처: [https://ict-nroo.tistory.com/130](https://ict-nroo.tistory.com/130) \[개발자의 기록습관]
