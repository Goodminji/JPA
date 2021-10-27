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

@Embedded

JPA entity안의 컬럼을 하나의 객체로 사용 하고 싶다면 내부객체 @embedded.@Embeddabel 어노테이션 사용

Ex > address,Delivery 참고 (delivery DB 조회했을때 address에 있는 필드 합쳐서 나옴)

```
@Entity
@Getter @Setter
public class Delivery {

	@Id
	@GeneratedValue
	@Column(name = "delivery_id")
	private Long id;
	
	@OneToOne(mappedBy = "delivery" ,fetch = FetchType.LAZY)
	private Order order;
	
	@Embedded
	private Address address;
}
@Embeddable //내장타입
@Getter
/*
 * 값 타입은 변경 불가능하도록 서례
 * @Setter 제거 하고 생성자에서 값을 모두 초기화해서 변경 불가능한 클래슬 만들어야 한다.
 * 자바 기본 생성자는 protected 설정
*/
public class Address {

	private String city;
	private String street;
	private String zipcode;
protected Address() { // 함부로 생성 못하도록 protected 설정 - 기본 생성자 생성
		
	}
	
	public Address(String city, String street, String zipcode) {
		this.city = city;
		this.street = street;
		this.zipcode = zipcode;
	}
}

```

@AttributeOverrid 사용

* 같은 객체를 여러 번 내장 타입으로 사용할경우 컬럼명이 겹칠

```
@Embedded
@ AttributeOverrid(name = “city”,column=@Column(name=”home_city”))
@ AttributeOverrid(name = “street”,column=@Column(name=”home_ street”))
@ AttributeOverrid(name = “zipCode”,column=@Column(name=”home_zipCode”))
	private Address homeaddress;

@Embedded
@ AttributeOverrid(name = “city”,column=@Column(name=”company_city”))
@ AttributeOverrid(name = “street”,column=@Column(name=” company_ street”))
@ AttributeOverrid(name = “zipCode”,column=@Column(name=” company _zipCode”))
	private Address companyaddress;

```



출처&#x20;

[https://velog.io/@adam2/JPA%EB%8A%94-%EB%8F%84%EB%8D%B0%EC%B2%B4-%EB%AD%98%EA%B9%8C-orm-%EC%98%81%EC%86%8D%EC%84%B1-hibernate-spring-data-jpa](https://velog.io/@adam2/JPA%EB%8A%94-%EB%8F%84%EB%8D%B0%EC%B2%B4-%EB%AD%98%EA%B9%8C-orm-%EC%98%81%EC%86%8D%EC%84%B1-hibernate-spring-data-jpa)

[https://eocoding.tistory.com/83](https://eocoding.tistory.com/83)

[https://ict-nroo.tistory.com/130](https://ict-nroo.tistory.com/130) \[개발자의 기록습관]

https://galid1.tistory.com/592

https://www.icatpark.com/entry/JPA-%EA%B8%B0%EB%B3%B8-Annotation-%EC%A0%95%EB%A6%AC

