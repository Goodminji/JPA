package jpabook.jpashop.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import jpabook.jpashop.domain.item.Item;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter
/*
 * 기본 생성자 protected로 설정
 * protected OrderItem() {
 * 
 * }
 */
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderItem {

		@Id
		@GeneratedValue
		@Column(name="order_item_id")
		private Long id;
		
		@ManyToOne(fetch = FetchType.LAZY)
		@JoinColumn(name = "item_id") // 외래키
		private Item item;
		
		@ManyToOne(fetch = FetchType.LAZY)
		@JoinColumn(name = "order_id") // 외래키
		private Order order;
		
		private int orderPrice; // 주문 가격
		private int count; // 주문 수량
		
		
		
		//생성 메소드
		
		public static OrderItem createOrderItem(Item item,int orderPrice,int count) {
			OrderItem orderItem = new OrderItem();
			orderItem.setItem(item);
			orderItem.setOrderPrice(orderPrice);
			orderItem.setCount(count);
			
			item.removeStock(count); // 주문 했으니깐 재고 줄이기
			
			return orderItem;
		}
		
		
		//비즈니스 로직
		
		//재고 수량 원복
		public void cancel() {
			getItem().addStock(count);
			
		}

		//조회 로직
		
		//주문 가격 * 주문 수량
		public int getTotalPrice() {
			return getOrderPrice() * getCount();
		}
	
}
