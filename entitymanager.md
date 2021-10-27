# EntityManager

**EntityManager**

* 엔티티를 관리하는 역할을 한다.
* 엔티티 매니저 내부에는 영속성 컨텍스트가 있으며, 이를 통해 엔티티 관리.
* 여러 엔티티 매니저가 하나의 영속성 컨텍스트를 공유 가능
* EntityManager 는 Thread-Safe 를 보장해야 한다. 동일한 EntityManager 를 가지고 멀티 스레드 환경에서 호출한다면 데이터가 어떻게 변경될지 모름
* 동일 Transaction 에서 EntityManager 를 여러 번 호출해서 처리하면 같은 영속성 컨텍스트 에서 처리된다.
* 다른 Transaction 이라면 다른 영속성 컨텍스트에서 처리된다.

**EntityManagerFactory**

* EntityManager 를 만드는 것이며, 스레드 간에 공유가 안되게 돼있음. 싱글톤 방식임

**EntityManager 함**

**Persist**

* Persist 메소드가 호출되면 영속상태로 진입하게되고 EntityManager에 의해 변경감지가 적용이 되어 영속성컨텍스트 내의 내용을 DB에 반영하는 flush가 호출 될 때 변경된 필드에 대해 자동으로 update/insert 문이 발생.

**Detach**

* detach메소드 호출하면 비영속상태가 되어 영속성컨텍스트에 의해 관리를 받지 않아 트랜잭션이 종료되더라도 detach된 엔티티에 대한 자동적인 생성/수정작업 일어나지 않는다.

**Merge**

* 비영속상태의 인스턴스를 영속성컨텍스트나 DB에 엔티티를 업데이트 하는것
* update 모든 데이터를 update 하고 만약 null이어도 null로 업데이트 - 사용 주의요망
* merge 후 영속성이 보장중인 엔티티를 반환합니다. 이 때, 기존 merge로 전달된 엔티티는 여전히 detached 상태
* &#x20;merge로 전달된 대상이 존재하지 않아 생성할 때에는, 해당 객체에 들어있는 내용을 그대로 사용하는 것이 아니라 일반적으로 객체가 persist 될 때의 로직(auto\_increment)를 사용

**Remve**

* db에서 엔티티 삭제, 영속성컨텍스트에서 제거되고, 트랜잭션이 종료될 때 db로부터 삭제

**EntityManager 어노테이션**

@PersistenceContext : 엔티티 메니저( EntityManager ) 주입

```
@RequiredArgsConstructor 로 final 선언하면 생성자 주입으로 가능

@Repository
@RequiredArgsConstructor
public class ItemRepository {
 
      private final EntityManager em;
….
}
```

@PersistenceUnit : 엔티티 메니터 팩토리( EntityManagerFactory ) 주입



출처

[https://velog.io/@rainmaker007/Jpa-EntityManager-%EC%84%A4%EB%AA%85-%EC%98%81%EC%86%8D%EC%84%B1-%EC%BB%A8%ED%85%8D%EC%8A%A4%ED%8A%B8](https://velog.io/@rainmaker007/Jpa-EntityManager-%EC%84%A4%EB%AA%85-%EC%98%81%EC%86%8D%EC%84%B1-%EC%BB%A8%ED%85%8D%EC%8A%A4%ED%8A%B8)

[https://tech.junhabaek.net/hibernate-jpa-entitymanager-%ED%95%B5%EC%8B%AC-%EA%B8%B0%EB%8A%A5-%EC%A0%95%EB%A6%AC-3d0d9ff439a2](https://tech.junhabaek.net/hibernate-jpa-entitymanager-%ED%95%B5%EC%8B%AC-%EA%B8%B0%EB%8A%A5-%EC%A0%95%EB%A6%AC-3d0d9ff439a2)
