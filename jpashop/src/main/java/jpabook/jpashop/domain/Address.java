package jpabook.jpashop.domain;

import javax.persistence.Embeddable;

import lombok.Getter;

@Embeddable //내장타입
@Getter
/*
 * 값 타입은 변경 불가능하도록 서례
 * @Setter 제거 하고 생성자에서 값을 모두 초기화해서 변경 불가능한 클래슬 만들어야 한다.
 * 자바 기본 생성자는 protected 설정
*/
public class Address {

	private String city;
	private String street;
	private String zipcode;
	
	protected Address() { // 함부로 생성 못하도록 protected 설정 - 기본 생성자 생성
		
	}
	
	public Address(String city, String street, String zipcode) {
		this.city = city;
		this.street = street;
		this.zipcode = zipcode;
	}
	
}
