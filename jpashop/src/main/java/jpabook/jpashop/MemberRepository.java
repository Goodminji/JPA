package jpabook.jpashop;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import jpabook.jpashop.domain.Member;

@Repository
public class MemberRepository {
	
	@PersistenceContext // ���Ӽ� ���ؽ�Ʈ(Persistence Context)
	private EntityManager em; // ��ƼƼ �Ŵ����� ��ƼƼ�� ���Ӽ� ���׽�Ʈ�� �����ϰ� �����մϴ�.

	public Long save(Member member) {
		em.persist(member);
		return member.getId();
	}
	
	public Member find(Long id) {
		return em.find(Member.class, id);
	}
}
