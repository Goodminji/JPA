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
	
	/*
	 * public List<Order> findAll(){ return
	 * em.createQuery("select i from order i",Order.class).getResultList(); }
	 */
	
}