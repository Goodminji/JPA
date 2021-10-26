# EntityManager

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
