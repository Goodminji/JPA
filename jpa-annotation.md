# JPA Annotation

**JPA 어노테이션**

@Entity: 데이터베이스의 테이블하고 일대일 매칭되는 객체 단위.

@Table : 엔티티와 매핑할 테이블을 지정.

@id : 기본키 할당.

@GeneratedValue : 기본키 자동 생성(주로 Long 사용,mysql:auto increment. Orcle:sequence)

@Column : 데이터베이스에 테이블에 있는 컬럼과 동일

@Enumerated(EnumType.STRING)

* JAVA의 enum 형태로 되어있는 미리 정의되어 있는 코드값이나 구분값을 데이터 타입으로 사용 하고자 할 때 사용
* EnumType.ORDINAL 이 디폴트 타입인데 숫자로 들어감. String으로 바꿔줘야함

@Inheritance(strategy = InheritanceType.SINGLE\_TABLE) (부모 클래스에 설정)

* 상속관계 매핑을 위한 어노테이션
* SINGLE\_TABLE 한테이블로 설정(JOINED,Table\_PER\_CALSS 등 옵션 존재)
* @DiscriminatorColumn(name="dtype") // 부모클래스에 자식클래스를 어떤 컬럼으로 구분을 할지 설정
* @DiscriminatorValue("M")// @DiscriminatorColumn - dtype 에 어떤 값으로 들어갈지 설정, 자식클래스 엔티키를 저장할 때 구분컬럼 입력값 지정
* Ex) ITEM 엔티티에서 book,Album,Movie를 자식클래스로 하여 단일 테이블 생성

JPA 스펙상 엔티 티나 임베디드 타입( @Embeddable )은 자바 기본 생성자(default constructor)를 public 또는 protected 로 설정해야 한다. public 으로 두는 것 보다는 protected 로 설정하는 것이 그나마 더 안전

출처 : [https://velog.io/@adam2/JPA%EB%8A%94-%EB%8F%84%EB%8D%B0%EC%B2%B4-%EB%AD%98%EA%B9%8C-orm-%EC%98%81%EC%86%8D%EC%84%B1-hibernate-spring-data-jpa](https://velog.io/@adam2/JPA%EB%8A%94-%EB%8F%84%EB%8D%B0%EC%B2%B4-%EB%AD%98%EA%B9%8C-orm-%EC%98%81%EC%86%8D%EC%84%B1-hibernate-spring-data-jpa)

[https://eocoding.tistory.com/83](https://eocoding.tistory.com/83)

출처: [https://ict-nroo.tistory.com/130](https://ict-nroo.tistory.com/130) \[개발자의 기록습관]

&#x20;

https://www.icatpark.com/entry/JPA-%EA%B8%B0%EB%B3%B8-Annotation-%EC%A0%95%EB%A6%AC

