package jpabook.jpashop.domain;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class Delivery {

	@Id
	@GeneratedValue
	@Column(name = "delivery_id")
	private Long id;
	
	@OneToOne(mappedBy = "delivery")
	private Order order;
	
	@Embedded
	private Address address;
	
	@Enumerated(EnumType.STRING) // EnumType.ORDINAL 이 디폴트 타입인데 숫자로 들어감. String으로 바꿔줘야함
	private DeliveryStatus status;//READY,COMP
	
}
