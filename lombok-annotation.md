# Lombok Annotation

**Lombok 어노테이션**

@getter,@setter get,set 사용

@AllArgsConstructor  모든 필드를 생성자 주입 자동으로 생성

```
 public MemberService(MemberRepository memberRepository) {
                      this.memberRepository = memberRepository;
           }
```

&#x20;@RequiredArgsConstructor final or @Notnull 로 선언 된것만 생성자 주입 자동으로 생성

```
public MemberService(MemberRepository memberRepository) {
                       this.memberRepository = memberRepository;
}
```

출처 : [https://velog.io/@adam2/JPA%EB%8A%94-%EB%8F%84%EB%8D%B0%EC%B2%B4-%EB%AD%98%EA%B9%8C-orm-%EC%98%81%EC%86%8D%EC%84%B1-hibernate-spring-data-jpa](https://velog.io/@adam2/JPA%EB%8A%94-%EB%8F%84%EB%8D%B0%EC%B2%B4-%EB%AD%98%EA%B9%8C-orm-%EC%98%81%EC%86%8D%EC%84%B1-hibernate-spring-data-jpa)

[https://eocoding.tistory.com/83](https://eocoding.tistory.com/83)

출처: [https://ict-nroo.tistory.com/130](https://ict-nroo.tistory.com/130) \[개발자의 기록습관]

&#x20;[https://www.daleseo.com/lombok-popular-annotations/](https://www.daleseo.com/lombok-popular-annotations/)

https://www.icatpark.com/entry/JPA-%EA%B8%B0%EB%B3%B8-Annotation-%EC%A0%95%EB%A6%AC
