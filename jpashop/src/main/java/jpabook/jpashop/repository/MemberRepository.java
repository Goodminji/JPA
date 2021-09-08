package jpabook.jpashop.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import jpabook.jpashop.domain.Member;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class MemberRepository {

	private final EntityManager em;
	
	public void save(Member member) {
		em.persist(member);
	}
	
	public Member findOne(Long id) {
		return em.find(Member.class,id);
	}
	
	public List<Member> findAll(){
		// select m from Member 엔티티 객체로 기준이 되어서 Member 엔티티를 조회하라는 쿼리다.
		return em.createQuery("select m from Member m",Member.class).getResultList();
	}
	
	public List<Member> findByName(String name){
		return em.createQuery("select m from Member m where m.name = :name ",Member.class)
				.setParameter("name", name)  //파라미터 바인딩 :name
				.getResultList();
	}
}
