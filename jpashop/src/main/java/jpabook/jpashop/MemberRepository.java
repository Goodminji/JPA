package jpabook.jpashop;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import jpabook.jpashop.domain.Member;

@Repository
public class MemberRepository {
	
	@PersistenceContext // 영속성 컨텍스트(Persistence Context)
	private EntityManager em; // 엔티티 매니저는 엔티티를 영속성 컨테스트에 보관하고 관리합니다.

	public Long save(Member member) {
		em.persist(member);
		return member.getId();
	}
	
	public Member find(Long id) {
		return em.find(Member.class, id);
	}
}
