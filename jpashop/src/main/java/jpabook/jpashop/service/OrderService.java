package jpabook.jpashop.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jpabook.jpashop.domain.Delivery;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderItem;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.repository.ItemRepository;
import jpabook.jpashop.repository.MemberRepository;
import jpabook.jpashop.repository.OrderRepository;
import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {
	
	private final OrderRepository orderRepository;
	private final MemberRepository memberRepository;
	private final ItemRepository itemRepository;
	
	
	//주문 - 도메인 모델 패턴!!(JPA,ORM에서)
	@Transactional
	public Long order(Long memberId, Long itemId,int count) {
		
		//엔티티 조회
		Member member = memberRepository.findOne(memberId);
		Item item = itemRepository.findOne(itemId);
		
		//배송 정보 생성
		Delivery delivery = new Delivery();
		delivery.setAddress(member.getAddress());
		
		//주문상품생성
		OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), count);
		
		//주문 생성
		Order order = Order.createORder(member, delivery, orderItem);
		
		//주문 저장
		orderRepository.save(order); // Order 에 casecade 설정이 되어 있어서 delivery, orderItem 모두 저장이 된다.
		
		return order.getId();
	}
	
	//주문 취소
	@Transactional
	public void cancelOrder(Long orderId) {
		//주문 엔티티 조회
		Order order = orderRepository.findOne(orderId);
		
		//주문 취소
		order.cancel(); // JPA 장점 : 데이터만 바꿔주면 알아서 Update 코드가 실행이 된다.
	}
	
	//검색
	/*
	 * public List<Order> findOrders(){
	 * 
	 * }
	 */
}
