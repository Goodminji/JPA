package jpabook.jpashop;


import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
public class MemberRepositoryTest {

	@Autowired
	MemberRepository memberRepository;
	
	@Test
	@Transactional
	@Rollback(false)
	public void testMember() throws Exception {
		//given
		Member member = new Member();
		member.setName("memberA");
		
		//when
		// 1. 1차 캐시에 저장
		Long saveId = memberRepository.save(member);
		// 2. 1차 캐시에서 조회
		Member findMember = memberRepository.find(saveId);		
		
		//then
		Assertions.assertThat(findMember.getId()).isEqualTo(member.getId());
		Assertions.assertThat(findMember.getName()).isEqualTo(member.getName());
		Assertions.assertThat(findMember).isEqualTo(member);
		/* 
		 * 영속성때문에 같은 트랜잭션으로 같은 식별자로 같은 엔티티로 인식하기 때문에 같은것으로 인식함(select 쿼리도 날리지 않음)
		 * 
		 * 영속성 컨텍스트는 내부에 캐시를 갖고 있는데 이게 1차 캐시
		 * 
		 * 엔티티 생성 후 EM에 persist() 하게 되면 이 영역에 저장됨
		 * 
		 * em.find()를 통해 데이터 조회 시 1차 캐시에서 먼저 찾고 없으면 DB에서 찾은 후 1차 캐시에 보관 후 반환
		 */


	}

}
