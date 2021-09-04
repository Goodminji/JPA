package jpabook.jpashop.domain;

import javax.persistence.Embeddable;

import lombok.Getter;

@Embeddable //����Ÿ��
@Getter
/*
 * �� Ÿ���� ���� �Ұ����ϵ��� ����
 * @Setter ���� �ϰ� �����ڿ��� ���� ��� �ʱ�ȭ�ؼ� ���� �Ұ����� Ŭ���� ������ �Ѵ�.
 * �ڹ� �⺻ �����ڴ� protected ����
*/
public class Address {

	private String city;
	private String street;
	private String zipcode;
	
	protected Address() { // �Ժη� ���� ���ϵ��� protected ���� - �⺻ ������ ����
		
	}
	
	public Address(String city, String street, String zipcode) {
		this.city = city;
		this.street = street;
		this.zipcode = zipcode;
	}
	
}
