package jpabook.jpashop.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
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
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="memeber_id") // 외래키
	private Member member;
	
	@OneToMany(mappedBy = "order", cascade=CascadeType.ALL)// cascade: Order를 db 작업을 하면 orderitem도 같이 작업
	private List<OrderItem> orderItems = new ArrayList<>();
	
	@OneToOne(fetch = FetchType.LAZY, cascade=CascadeType.ALL)
	@JoinColumn(name = "delivery_id")
	private Delivery delivery;
	
	private LocalDateTime orderDate; // 주문 시간
	
	@Enumerated(EnumType.STRING) // EnumType.ORDINAL 이 디폴트 타입인데 숫자로 들어감. String으로 바꿔줘야함
	private OrderStatus status; // 주문 상태 ORDER,CANCEL
	
	// 연관관계 메서드 (양방향 관계일때)
	public void setMember(Member member) {
		this.member = member;
		member.getOrders().add(this);
	}
	
	public void addOrderItem(OrderItem orderItem) {
		orderItems.add(orderItem);
		orderItem.setOrder(this);
	}
	
	public void setDelivery(Delivery delivery) {
		this.delivery = delivery;
		delivery.setOrder(this);
	}
}
