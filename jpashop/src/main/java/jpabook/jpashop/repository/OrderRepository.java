package jpabook.jpashop.repository;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import jpabook.jpashop.domain.Order;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class OrderRepository {

	private final EntityManager em;
	
	public void save(Order order) {
		em.persist(order);
	}
	
	public Order findOne(Long orderId) {
		return em.find(Order.class, orderId);
	}
	
	
	public List<Order> findAll(OrderSearch orderSearch){ 
		return em.createQuery("select o from order o join o.member m"+
	                          "where o.status = :status"+
	                          "and m.name like :name",Order.class)
				.setParameter("status", orderSearch.getOrderStatus())
				.setParameter("name", orderSearch.getMemberName())
				.setMaxResults(1000)//�ִ� 100��
				.getResultList(); 
	}
	
	
	
}
