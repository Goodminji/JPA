# Lombok Annotation

**Lombok의 장점**

* 어노테이션 기반의 코드 자동 생성을 통한 생산성 향상
* 반복되는 코드 다이어트를 통한 가독성 및 유지보수성 향상
* Getter, Setter 외에 빌더 패턴이나 로그 생성 등 다양한 방면으로 활용 가능

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

#### **@NoArgsConstructor**

@NoArgsConstructor는 어떠한 변수도 사용하지 않는 기본 생성자를 자동완성 시켜주는 어노테이션이다.

```
public Member() { }
```

@EqualsAndHashCode

* @EqualsAndHashCode 어노테이션을 활용하면 클래스에 대한 equals 함수와 hashCode 함수를 자동으로 생성해준다.
* 서로 다른 두 객체에서 특정 변수의 이름이 똑같은 경우 같은 객체로 판단을 하고 싶다면 아래와 같이 해줄 수 있다.

```
@EqualsAndHashCode(of = {"companyName", "industryTypeCode"}, callSuper = false))
// companyName과 industryTypeCode가 동일하다면 같은 객체로 인식
```

&#x20;\* callSuper 상속관련으로 상위클래스는 적용 안하기(false)

@ToString

@ToString 어노테이션을 활용하면 클래스의 변수들을 기반으로 ToString 메소드를 자동으로 완성시켜 준다. ( @ToString.Exclude 제외 변수)

\
@Builder 어노테이션을 활용하면 해당 클래스의 객체의 생성에 Builder패턴\
\
@Log4j2와 같은 어노테이션을 활용하면 해당 클래스의 로그 클래스를 자동 완성 시켜준다\
\
\
출처&#x20;

[https://velog.io/@adam2/JPA%EB%8A%94-%EB%8F%84%EB%8D%B0%EC%B2%B4-%EB%AD%98%EA%B9%8C-orm-%EC%98%81%EC%86%8D%EC%84%B1-hibernate-spring-data-jpa](https://velog.io/@adam2/JPA%EB%8A%94-%EB%8F%84%EB%8D%B0%EC%B2%B4-%EB%AD%98%EA%B9%8C-orm-%EC%98%81%EC%86%8D%EC%84%B1-hibernate-spring-data-jpa)

[https://eocoding.tistory.com/83](https://eocoding.tistory.com/83)

[https://ict-nroo.tistory.com/130](https://ict-nroo.tistory.com/130) \[개발자의 기록습관]

&#x20;[https://www.daleseo.com/lombok-popular-annotations/](https://www.daleseo.com/lombok-popular-annotations/)

https://www.icatpark.com/entry/JPA-%EA%B8%B0%EB%B3%B8-Annotation-%EC%A0%95%EB%A6%AC

[https://mangkyu.tistory.com/78](https://mangkyu.tistory.com/78) \[MangKyu's Diary]
