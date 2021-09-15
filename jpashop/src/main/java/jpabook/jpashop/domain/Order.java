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
	@JoinColumn(name="memeber_id") // �ܷ�Ű
	private Member member;
	
	@OneToMany(mappedBy = "order", cascade=CascadeType.ALL)// cascade: Order�� db �۾��� �ϸ� orderitem�� ���� �۾�
	private List<OrderItem> orderItems = new ArrayList<>();
	
	@OneToOne(fetch = FetchType.LAZY, cascade=CascadeType.ALL)
	@JoinColumn(name = "delivery_id")
	private Delivery delivery;
	
	private LocalDateTime orderDate; // �ֹ� �ð�
	
	@Enumerated(EnumType.STRING) // EnumType.ORDINAL �� ����Ʈ Ÿ���ε� ���ڷ� ��. String���� �ٲ������
	private OrderStatus status; // �ֹ� ���� ORDER,CANCEL
	
	// �������� �޼��� (����� �����϶�)
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
	
	//���� �޼���
	
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
	
	//����Ͻ� ����
	
	//�ֹ� ���
	public void cancel() {
		if(delivery.getStatus() == DeliveryStatus.COMP) {
			throw new IllegalStateException("�̹� ��ۿϷ�� ��ǰ�� ��Ұ� �Ұ����մϴ�");
		}
		
		this.setStatus(OrderStatus.CANCEL);
		
		for(OrderItem orderItem : this.orderItems) {
			orderItem.cancel();
		}
	}
	
	//��ȸ ����
	//��ü �ֹ� ���� ��ȸ
	public int getTotalPrice() {
		
		int totalPrice = 0;
		
		for(OrderItem orderItem : this.orderItems) {
			totalPrice += orderItem.getTotalPrice();
		}
		
		return this.orderItems.stream().mapToInt(OrderItem::getTotalPrice).sum();
	}
}
