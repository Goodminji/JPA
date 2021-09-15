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
	public void ��ǰ�ֹ�() throws Exception{
		//given
		Member member = createMember();
		
		Item book = createBook("JPA",10000,10);
		
		int orderCount = 2;
		
		//when
		Long orderId = orderSerive.order(member.getId(), book.getId(), orderCount);
		
		//then
		Order getOrder = orderRepository.findOne(orderId);
		
		assertEquals(OrderStatus.ORDER, getOrder.getStatus()); // �ֹ� ���´� Order
		assertEquals(1, getOrder.getOrderItems().size()); // �ֹ��� ��ǰ ���� ���� ��Ȯ�ؾ� �Ѵ�.
		assertEquals(10000 * orderCount, getOrder.getTotalPrice());//�ֹ� ������ ���� * ����
		assertEquals(8, book.getStockQuantity());//�ֹ� ������ŭ ��� �پ�� �Ѵ�.
		
		
	}
	
	@Test
	public void �ֹ����() throws Exception{
		//given
		Member member = createMember();
		Item book = createBook("JPA", 10000, 10);
		
		int orderCount = 2;
		
		Long orderId = orderSerive.order(member.getId(), book.getId(), orderCount);
		
		//when
		orderSerive.cancelOrder(orderId);
		
		//then
		Order getOrder = orderRepository.findOne(orderId);
		
		assertEquals(OrderStatus.CANCEL, getOrder.getStatus()); // �ֹ� ���´� CANCEL
		assertEquals(10, book.getStockQuantity()); // �ֹ��� ��ҵ� ��ǰ�� �׸�ŭ ��� ���� �ؾ��Ѵ�.
		
	}
	
	@Test//(expected = NotEnoughStockException.class)
	public void ��ǰ�ֹ�_�������ʰ�() throws Exception{
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
		fail("��� ���� �������� ���ܰ� �߻� �ؾ��Ѵ�.");
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
		member.setName("����");
		member.setAdress(new Address("����", "���", "123123"));
		em.persist(member);
		return member;
	}

}
