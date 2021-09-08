package jpabook.jpashop.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true) // public method�� ���� ���� , readOnly = true -> �б� ����
/*
 lombok Annotation  
 @AllArgsConstructor  ��� �ʵ鸦 ������ ���� �ڵ����� ����
  => public MemberService(MemberRepository memberRepository) {
		this.memberRepository = memberRepository;
	}
 @RequiredArgsConstructor final or @Notnull �� ���� �Ȱ͸� ������ ���� �ڵ����� ����
   => public MemberService(MemberRepository memberRepository) {
		this.memberRepository = memberRepository;
	  } 
 */
@RequiredArgsConstructor
public class MemberService {

	private final MemberRepository memberRepository;

	//ȸ�� ����
	@Transactional(readOnly = false) // �޼ҵ忡 ���� �������� �켱���� 1����
	public Long join(Member member) {
		validateDuplicateMember(member);// �ߺ� ȸ�� ����
		memberRepository.save(member);
		return member.getId();
	}


	private void validateDuplicateMember(Member member) {
		//Exception
		List<Member> findMembers = memberRepository.findByName(member.getName());
		
		if(!findMembers.isEmpty()) {
			throw new IllegalStateException("�̹� �����ϴ� ����Դϴ�");
		}
	}
	
	// ��ü ȸ�� ��ȸ
	public List<Member> findMembers(){
		return memberRepository.findAll();
	}
	
	public Member findOne(Long memberId) {
		return memberRepository.findOne(memberId);
	}
	
}
