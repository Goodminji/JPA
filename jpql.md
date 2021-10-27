# JPQL

**JPQL(JAVA Persistence Query Language)**

* 테이블이 아닌 _엔티티 객체_를 대상으로 검색하는 객체지향 쿼리
* SQL을 추상화해서 특정 데이터베이스 _SQL에 의존하지 않음_
* JPA는 JPQL을 분석한 후 적절한 SQL을 만들어 데이터베이스를 조회

**Select**

* 대소문자 구분
  * 엔티티와 속성은 대소문자 구분
  * JPQL 키워드는 구분하지 않음
* 엔티티 이름
  * 테이블 명 대신 엔티티 명을 사용, @Entity(name=" ") 으로 설정 가능
  * 지정하지 않을 시 클래스 명을 기본값으로 사용(기본값을 추천)
* 별칭은 필수
  * &#x20;JPQL은 별칭을 필수
  * AS는 생략 가

```
public List<Member> findAll(){
               // select m from Member 엔티티 객체로 기준이 되어서 Member 엔티티를 조회하라는 쿼리다.
               return em.createQuery("select m from Member m",Member.class).getResultList();
       }
      
       public List<Member> findByName(String name){
              return em.createQuery("select m from Member m where m.name = :name ",Member.class)
                             .setParameter("name", name)  //파라미터 바인딩 :name
                             .getResultList(); // 결과가 없을 경우 빈 컬렉션 반환
      }


```

**결과조회**

* getResultList(); // 결과가 없을 경우 빈 컬렉션 반환
* getSingleResult() : 결과가 정확히 하나일 때 사용 ( 없거나 1개보다 많으면 예외 발생)

**페이징**

* setFirstResult 조회시작 위치
* setMaxReults 조회할 테이터 수

**Join 사용**

```
public List<Order> findAll(OrderSearch orderSearch){
               return em.createQuery("select o from order o join o.member m"+
                                 "where o.status = :status"+
                                 "and m.name like :name",Order.class)
                             .setParameter("status", orderSearch.getOrderStatus())
                             .setParameter("name", orderSearch.getMemberName())
                             .setMaxResults(1000)//최대 100건
                             .getResultList();
       }
```

**fetch Join**

* &#x20;JPQL에서 _성능 최적화_를 위해 제공하는 기능
* 연관된 엔티티나 컬렉션을 _한 번에 같이 조회_ (JPQL은 결과를 반환할 때 연관관계까지 고려하지 않음 -> fetch join)
* &#x20;SQL 호출 횟수를 줄여 성능 최적화
* &#x20;쿼리 시점에 조회하므로 지연 로딩이 발생하지 않음
* &#x20;준영속 상태에서도 객체 그래프를 탐색
* 글로벌 로딩 전략보다 우선

&#x20;**fetch join의 한계**

* 페치 조인 대상에는 별칭을 줄 수 없음
* &#x20;둘 이상의 컬렉션을 페치할 수 없음 (카타시안 곱 발생)
* 컬렉션을 페치 조인하면 페이징 API를 사용할 수 없음 (단일 값 연관 필드(일대일, 다대일)는 가능)

```
select m
from Member m join fetch m.team
```



* 서브 쿼리 지원
* &#x20;EXISTS, IN
* BETWEEN, LIKE, IS NULL
* CONCAT,SUBSTRING,TRIM,LOWER, UPPER,LENGTH,LOCATE
* ABS, SQRT, MOD
* GROUP BY, HAVING
* ORDER BY
* SIZE, INDEX(JPA 용도)
* Count,sum,avg,max,min
* CASE, COALESCE, NULLIF

@NamedQuery

* 미리 정의해서 이름을 부여해두고 사용하는 JPQL
* 어노테이션, XML에 정의
* 애플리케이션 로딩 시점에 초기화 후 재사용
* 애플리케이션 로딩 시점에 쿼리를 검증

출처

[https://ict-nroo.tistory.com/116](https://ict-nroo.tistory.com/116) \[개발자의 기록습관]\
\
[https://data-make.tistory.com/614](https://data-make.tistory.com/614) \[Data Makes Our Future]
