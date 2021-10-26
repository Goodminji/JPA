package jpabook.jpashop.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import javax.annotation.security.RunAs;
import javax.persistence.EntityManager;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;

@SpringBootTest
@Transactional
public class MemberServiceTest {

	@Autowired
	MemberService memberService;
	
	@Autowired
	MemberRepository memberRepository;
	
	@Autowired
	EntityManager em;
	
	
	
	/* @Rollback(false)
	 * ���Ӽ� ������ Ʈ����� commit�� �ɶ� insert ������ ����. �⺻������ TEST ���� Rollback Default
	 * �׷��� Rollback���� �Ǿ� ������ insert ������ ������ ����.
	 * ���̰� �Ϸ��� @Rollback(false)�� ����
	 */
	@Test
	public void ȸ������() throws Exception{
		
		//given
		Member member = new Member();
		member.setName("minji");

		//when
		Long saveId = memberService.join(member); 
		
		//then
		em.flush(); /*�׿��ִ� �������� DB�� ������ ������ flush() �̴�.
 			flush() �� 1��ĳ�ø� �������� �ʴ´�. �������� DB�� ������ DB�� ��ũ�� ���ߴ� ������ �Ѵ�.
		 */
		
		/*
		 * ���Ӽ��� �ִ°��� ������ ���̽��� �ݿ��ϴ� ���̹Ƿ� RollBack(false)�� ���� ���� �ʾƵ� insert ���� ����
		 * �׸��� �� �Ŀ� Rollback�� �ȴ�.
		 */
		assertEquals(member, memberRepository.findOne(saveId));
	}
	
	//@Test(expected = IllegalStateException.class)
	@Test
	public void �ߺ�ȸ������() throws Exception{
		//given
		Member member1 = new Member();
		member1.setName("minji");
		
		Member member2 = new Member();
		member2.setName("minji");
		//when
		memberService.join(member1);
		
		try {
			memberService.join(member2); // ���ܰ� �߻� �ؾ���
		} catch (IllegalStateException e) {
			return;
		}
		
		//then
		fail("���ܰ� �߻��ؾ��Ѵ�.");
	}

}
