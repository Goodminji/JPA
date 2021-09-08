package jpabook.jpashop.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class Member {

	@Id
	@GeneratedValue
	@Column(name = "member_id")
	private Long id;
	
	private String name;

	@Embedded // 내장 타입
	private Address adress;
	
	@OneToMany(mappedBy = "member") // order 테이블에 있는 member 필드에 의해서 매핑이 된거다.(읽기전용)
	private List<Order> orders = new ArrayList<>(); // 여기서 초기화하면 null 오류가 날 경우가 없다.
}
