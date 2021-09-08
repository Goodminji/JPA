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
		// select m from Member ��ƼƼ ��ü�� ������ �Ǿ Member ��ƼƼ�� ��ȸ�϶�� ������.
		return em.createQuery("select m from Member m",Member.class).getResultList();
	}
	
	public List<Member> findByName(String name){
		return em.createQuery("select m from Member m where m.name = :name ",Member.class)
				.setParameter("name", name)  //�Ķ���� ���ε� :name
				.getResultList();
	}
}
