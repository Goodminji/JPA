package jpabook.jpashop.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true) // public method에 전부 적용 , readOnly = true -> 읽기 전용
/*
 lombok Annotation  
 @AllArgsConstructor  모든 필들를 생성자 주입 자동으로 생성
  => public MemberService(MemberRepository memberRepository) {
		this.memberRepository = memberRepository;
	}
 @RequiredArgsConstructor final or @Notnull 로 선언 된것만 생성자 주입 자동으로 생성
   => public MemberService(MemberRepository memberRepository) {
		this.memberRepository = memberRepository;
	  } 
 */
@RequiredArgsConstructor
public class MemberService {

	private final MemberRepository memberRepository;

	//회원 가입
	@Transactional(readOnly = false) // 메소드에 직접 붙은것이 우선순위 1순위
	public Long join(Member member) {
		validateDuplicateMember(member);// 중복 회원 검증
		memberRepository.save(member);
		return member.getId();
	}


	private void validateDuplicateMember(Member member) {
		//Exception
		List<Member> findMembers = memberRepository.findByName(member.getName());
		
		if(!findMembers.isEmpty()) {
			throw new IllegalStateException("이미 존재하는 사람입니다");
		}
	}
	
	// 전체 회원 조회
	public List<Member> findMembers(){
		return memberRepository.findAll();
	}
	
	public Member findOne(Long memberId) {
		return memberRepository.findOne(memberId);
	}
	
}
