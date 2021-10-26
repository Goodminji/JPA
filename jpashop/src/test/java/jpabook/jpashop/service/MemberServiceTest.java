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
	 * 영속성 때문에 트랜잭션 commit이 될때 insert 쿼리가 나감. 기본적으로 TEST 에서 Rollback Default
	 * 그래서 Rollback으로 되어 있으면 insert 쿼리가 보이지 않음.
	 * 보이게 하려면 @Rollback(false)로 설정
	 */
	@Test
	public void 회원가입() throws Exception{
		
		//given
		Member member = new Member();
		member.setName("minji");

		//when
		Long saveId = memberService.join(member); 
		
		//then
		em.flush(); /*쌓여있는 쿼리들을 DB에 보내는 동작이 flush() 이다.
 			flush() 는 1차캐시를 지우지는 않는다. 쿼리들을 DB에 날려서 DB와 싱크를 맞추는 역할을 한다.
		 */
		
		/*
		 * 영속성에 있는것을 데이터 베이스에 반영하는 것이므로 RollBack(false)로 설정 하지 않아도 insert 쿼리 나감
		 * 그리고 난 후에 Rollback이 된다.
		 */
		assertEquals(member, memberRepository.findOne(saveId));
	}
	
	//@Test(expected = IllegalStateException.class)
	@Test
	public void 중복회원예외() throws Exception{
		//given
		Member member1 = new Member();
		member1.setName("minji");
		
		Member member2 = new Member();
		member2.setName("minji");
		//when
		memberService.join(member1);
		
		try {
			memberService.join(member2); // 예외가 발생 해야함
		} catch (IllegalStateException e) {
			return;
		}
		
		//then
		fail("예외가 발생해야한다.");
	}

}
