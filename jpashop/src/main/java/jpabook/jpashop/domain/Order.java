package jpabook.jpashop.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="orders")
@Getter @Setter
public class Order {

	@Id
	@GeneratedValue
	@Column(name="order_id")
	private Long id;
	
	@ManyToOne
	@JoinColumn(name="memeber_id") // 외래키
	private Member member;
	
	@OneToMany(mappedBy = "order")
	private List<OrderItem> orderItem = new ArrayList<>();
	
	@OneToOne
	@JoinColumn(name = "delivery_id")
	private Delivery delivery;
	
	private LocalDateTime orderDate; // 주문 시간
	
	@Enumerated(EnumType.STRING) // EnumType.ORDINAL 이 디폴트 타입인데 숫자로 들어감. String으로 바꿔줘야함
	private OrderStatus status; // 주문 상태 ORDER,CANCEL
}
