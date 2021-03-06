package jpabook.jpashop.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
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

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
	
	//생성 메서드
	
	public static Order createORder(Member member,Delivery dilivery, OrderItem... orderItems) {
		Order order = new Order();
		order.setMember(member);
		order.setDelivery(dilivery);
		for (OrderItem orderItem : orderItems) {
			order.addOrderItem(orderItem);
		}
		order.setStatus(OrderStatus.ORDER);
		order.setOrderDate(LocalDateTime.now());
		return order;
	}
	
	//비즈니스 로직
	
	//주문 취소
	public void cancel() {
		if(delivery.getStatus() == DeliveryStatus.COMP) {
			throw new IllegalStateException("이미 배송완료된 상품은 취소가 불가능합니다");
		}
		
		this.setStatus(OrderStatus.CANCEL);
		
		for(OrderItem orderItem : this.orderItems) {
			orderItem.cancel();
		}
	}
	
	//조회 로직
	//전체 주문 가격 조회
	public int getTotalPrice() {
		
		int totalPrice = 0;
		
		for(OrderItem orderItem : this.orderItems) {
			totalPrice += orderItem.getTotalPrice();
		}
		
		return this.orderItems.stream().mapToInt(OrderItem::getTotalPrice).sum();
	}
}
