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

	@Embedded // ���� Ÿ��
	private Address adress;
	
	@OneToMany(mappedBy = "member") // order ���̺� �ִ� member �ʵ忡 ���ؼ� ������ �ȰŴ�.(�б�����)
	private List<Order> orders = new ArrayList<>(); // ���⼭ �ʱ�ȭ�ϸ� null ������ �� ��찡 ����.
}
