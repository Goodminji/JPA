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
		// 1. 1�� ĳ�ÿ� ����
		Long saveId = memberRepository.save(member);
		// 2. 1�� ĳ�ÿ��� ��ȸ
		Member findMember = memberRepository.find(saveId);		
		
		//then
		Assertions.assertThat(findMember.getId()).isEqualTo(member.getId());
		Assertions.assertThat(findMember.getName()).isEqualTo(member.getName());
		Assertions.assertThat(findMember).isEqualTo(member);
		/* 
		 * ���Ӽ������� ���� Ʈ��������� ���� �ĺ��ڷ� ���� ��ƼƼ�� �ν��ϱ� ������ ���������� �ν���(select ������ ������ ����)
		 * 
		 * ���Ӽ� ���ؽ�Ʈ�� ���ο� ĳ�ø� ���� �ִµ� �̰� 1�� ĳ��
		 * 
		 * ��ƼƼ ���� �� EM�� persist() �ϰ� �Ǹ� �� ������ �����
		 * 
		 * em.find()�� ���� ������ ��ȸ �� 1�� ĳ�ÿ��� ���� ã�� ������ DB���� ã�� �� 1�� ĳ�ÿ� ���� �� ��ȯ
		 */


	}

}
