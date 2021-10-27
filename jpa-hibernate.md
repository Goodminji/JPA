# JPA Hibernate

**Hibernate란?**

* &#x20;JPA 구현체. ORM 프레임워크 중 하나다. SQL문을 직접 작성하지 않고 메소드 호출만으로 쿼리 수행을 가능하게 해서 생산성을 높일 수 있게 한다.
* &#x20;Hibernate가 지원하는 메서드 내부에서는 JDBC API가 동작하고 있으며, 단지 개발자가 직접 SQL을 직접 작성하지 않을 뿐이다.
*   JPA 구현체이며 JPA의 핵심체인 EntityManagerFactory,EntityManager,EntityTransaction을 hibernate에서는 sesstionFactory,Session,Transaction으로 상속받고 각각 impl로 구현하고 있다.

    객체-관계 모델 매핑솔루션으로 낮은 결합도의 이점을 가진 ORM툴로 JAVA persistence API 를 참조하여 구현하는 기능.

출처

[https://blog.neonkid.xyz/224](https://blog.neonkid.xyz/224)
