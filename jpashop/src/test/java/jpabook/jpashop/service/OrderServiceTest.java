package jpabook.jpashop.service;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import javax.persistence.EntityManager;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderStatus;
import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.exception.NotEnoughStockException;
import jpabook.jpashop.repository.OrderRepository;

@SpringBootTest
@Transactional
class OrderServiceTest {
	
	@Autowired
	EntityManager em;
	
	@Autowired
	OrderService orderSerive;
	
	@Autowired
	OrderRepository orderRepository;

	@Test
	public void 상품주문() throws Exception{
		//given
		Member member = createMember();
		
		Item book = createBook("JPA",10000,10);
		
		int orderCount = 2;
		
		//when
		Long orderId = orderSerive.order(member.getId(), book.getId(), orderCount);
		
		//then
		Order getOrder = orderRepository.findOne(orderId);
		
		assertEquals(OrderStatus.ORDER, getOrder.getStatus()); // 주문 상태는 Order
		assertEquals(1, getOrder.getOrderItems().size()); // 주문한 상품 종류 수가 정확해야 한다.
		assertEquals(10000 * orderCount, getOrder.getTotalPrice());//주문 가격은 가격 * 수량
		assertEquals(8, book.getStockQuantity());//주문 수량만큼 재고가 줄어야 한다.
		
		
	}
	
	@Test
	public void 주문취소() throws Exception{
		//given
		Member member = createMember();
		Item book = createBook("JPA", 10000, 10);
		
		int orderCount = 2;
		
		Long orderId = orderSerive.order(member.getId(), book.getId(), orderCount);
		
		//when
		orderSerive.cancelOrder(orderId);
		
		//then
		Order getOrder = orderRepository.findOne(orderId);
		
		assertEquals(OrderStatus.CANCEL, getOrder.getStatus()); // 주문 상태는 CANCEL
		assertEquals(10, book.getStockQuantity()); // 주문이 취소된 상품은 그만큼 재고가 증가 해야한다.
		
	}
	
	@Test//(expected = NotEnoughStockException.class)
	public void 상품주문_재고수량초과() throws Exception{
		//given
		
		Member member = createMember();
		
		Item book = createBook("JPA",10000,10);
		
		int orderCount = 11;
		//when
		try {
			orderSerive.order(member.getId(), book.getId(), orderCount);
		} catch (NotEnoughStockException e) {
			return;
		}
		
		//then
		fail("재고 수량 부족으로 예외가 발생 해야한다.");
	}
	
	private Item createBook(String name,int price, int stockQuantity) {
		
		Item book = new Book();
		book.setName(name);
		book.setPrice(price);
		book.setStockQuantity(stockQuantity);
		em.persist(book);
		
		return book;
		
	}
	
	private Member createMember() {
		Member member = new Member();
		member.setName("민지");
		member.setAdress(new Address("서울", "경기", "123123"));
		em.persist(member);
		return member;
	}

}
