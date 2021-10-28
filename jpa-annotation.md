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

**JPA 관계**

@OneToOne

* 단방향 관계
  * DB상의 1대1 관계
  * 부모테이블과 자식테이블의 레코드가 각각 하나씩 연결되어 의미를 가짐
* 양방향 관계
  * 기존 단방향 OneToOne에서 부모 Entity에서 자식 Entity에 대한 mappedBy 설정만 추가 .
  * mappedBy 설정에는 자식 Entity에서 바라보는 부모 Entity의 변수 이름을 지정.

```
	@Entity
	@Getter @Setter
	public class Delivery {
	
		@Id
		@GeneratedValue
		@Column(name = "delivery_id")
		private Long id; // parentId
		
		@OneToOne(mappedBy = "delivery" ,fetch = FetchType.LAZY) //사용 시점에 조회
		private Order order;
	…
	}
	@Entity
	@Table(name="orders")
	@Getter @Setter
	@NoArgsConstructor(access = AccessLevel.PROTECTED)
	public class Order {
	
		@Id
		@GeneratedValue
		@Column(name="order_id")
		private Long id; // child Id
		
		……
		@OneToOne(fetch = FetchType.LAZY, cascade=CascadeType.ALL) //사용 시점에 조회
		@JoinColumn(name = "delivery_id") //child에 지정되어 있는 FK delivery_id 기준으로 delivery 조회
		private Delivery delivery;
	….
	}

```

* &#x20;FetchType.EAGER(디폴트) : OneToOne 관계를 맺었을 때 디폴트 설정. 자식 Entity를 조회했을 때 자동으로 부모 Entity를 조회함.
* &#x20; FetchType.LAZY : 지정된 Entity 객체를 미리 가지고 있는 것이 아니라 Entity 객체를 사용하려고 할 때 그 즉시 데이터를 DB에서 가져옴. 바로 부모 Entity를 사용할 필요가 없을 때 속도를 위해 사용함.
* 기본적으로 FK 있는 쪽이 자식 entity 객체가 된다.

@ManyToOne

* 자식Entity 객체에서 부모 객체를 바라볼 때 사용하는 어노테이션
* 부도Entity 데이터를 가지는 자식 Entity 데이터가 여러 개 일수 있따
* FK 있는 쪽이 자식 Entity 객체
* &#x20;FetchType.Eager 디폴트

```
@Entity
@Table(name="orders")
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order {

	@Id
	@GeneratedValue
	@Column(name="order_id")
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="memeber_id") // 외래키 – 주문이 자식
		private Member member;  // 하나의 맴버가 여러 개의 주문(자식) 가능 
	..
	}

```

@OneToMany

* &#x20;데이터를 바라보는 주체가 부모 Entity이며 하나의 부모 Entity데이터와 연관이 있는 여러개의 자식 Entity 데이터 사용
* 부모 Entity에 @OneToMany 어노테이션 지정
* FK가 위치한 곳이 부모 Entity 어노테이션.
* FetchType.Lazy 디폴트
* 양방향 : @OneToMany 일때는 부모 Entity 에 @JoinColumn 어노테이션이 제거되고 @OneToMany 의 mappedBy 속성을 추가해 자식 Entity 와의 관계를 설정. 이때 mappedBy 속성에는 자식 Entity 에서 부모 Entity를 바라보는 변수이름을 지정. 자식 Entity 에서는 단방향 @ManyToOne 와 동일하게 부모와의 관계를 지정

```
Ex) 단방향
@Entity(name = "parent")
public class Parent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @OneToMany
    @JoinColumn(name = "parent_id") //child 테이블에 있는 parent_id FK 
    private List<Child> childList;
}

Ex) 양방향
@Entity
@Getter @Setter
public class Member {

	@Id
	@GeneratedValue
	@Column(name = "member_id")
	private Long id;
	
	private String name;

	@Embedded // 내장 타입
	private Address address;
	
	@OneToMany(mappedBy = "member") // order 테이블에 있는 member 필드에 의해서 매핑이 된거다.(읽기전용)
//Member가 부모 member가 여러 개 주문 가능.
	private List<Order> orders = new ArrayList<>(); // 여기서 초기화하면 null 오류가 날 경우가 없다.
}

```



@ManyToMany

* DB상에 표현할수 없는 관계이며 JPA 상에서만 표현 가능
* &#x20;@ManyToMany 를 사용하기 위해서 @JoinTable 어노테이션의 name 속성으로 중간 맵핑 테이블을 정의. 이 중간 맵핑 테이블로 부모 Entity 와 자식 Entity 간의 관계를 알 수 있으며 다른 관계와는 다르게 부모와 자식 Entity 가 동등한 위치이기 때문에 자식 Entity 의 FK 는 존재하지 않으며 중간 맵핑 테이블에 부모 Entity 의 PK 와 자식 Entity 의 PK 가 둘다 존재.
* 양방향 : 서로의 Entity 가 동등한 위치를 가지고 있기 때문에 관계 설정 정보를 어디에 지정할 것 인가를 결정해야 함, 관계 설정 정보를 둘 중 어디에 둘 것인지를 결정 했다면 반대쪽 Entity 에 @ManyToMany 어노테이션을 정의하고 mappedBy 속성에 정의된 변수이름을 지정

```
Ex) 양방향
@Entity
@Getter @Setter
public class Category { // 부모 엔티티

	@Id
	@GeneratedValue
	@Column(name = "category_id")
	private Long id;
	
	private String name;
	
	@ManyToMany
	// 중간 테이블 생성 (필드가 더 추가가 불가해서 운영에서 사용 잘안함)
	@JoinTable(name="category_item",
		joinColumns = @JoinColumn(name="category_id"),//부모PK
		inverseJoinColumns = @JoinColumn(name="item_id"))//자식PK
	private List<Item> items = new ArrayList<>();
	....
	}	
@Entity
@Getter @Setter
@Inheritance(strategy = InheritanceType.SINGLE_TABLE) // 상속 설정 - SINGLE_TABLE 한테이블로 설정할꺼다.
@DiscriminatorColumn(name="dtype") // 어떤 컬럼으로 구분을 할지 설정 
public abstract class Item { //자식엔티티

	@Id
	@GeneratedValue
	@Column(name = "item_id")
	private Long id;
	
	private String name;
	private int price;
	private int stockQuantity;
	
	@ManyToMany(mappedBy = "items")
		private List<Category> categories = new ArrayList<>(); ..
	}

```



&#x20;      &#x20;

출처&#x20;

[https://velog.io/@adam2/JPA%EB%8A%94-%EB%8F%84%EB%8D%B0%EC%B2%B4-%EB%AD%98%EA%B9%8C-orm-%EC%98%81%EC%86%8D%EC%84%B1-hibernate-spring-data-jpa](https://velog.io/@adam2/JPA%EB%8A%94-%EB%8F%84%EB%8D%B0%EC%B2%B4-%EB%AD%98%EA%B9%8C-orm-%EC%98%81%EC%86%8D%EC%84%B1-hibernate-spring-data-jpa)

[https://eocoding.tistory.com/83](https://eocoding.tistory.com/83)

[https://ict-nroo.tistory.com/130](https://ict-nroo.tistory.com/130) \[개발자의 기록습관]

https://galid1.tistory.com/592

https://www.icatpark.com/entry/JPA-%EA%B8%B0%EB%B3%B8-Annotation-%EC%A0%95%EB%A6%AC

[https://velog.io/@jsw7000/JAVA-JPA-%EA%B8%B0%EB%B3%B8-%EC%96%B4%EB%85%B8%ED%85%8C%EC%9D%B4%EC%85%98](https://velog.io/@jsw7000/JAVA-JPA-%EA%B8%B0%EB%B3%B8-%EC%96%B4%EB%85%B8%ED%85%8C%EC%9D%B4%EC%85%98)

