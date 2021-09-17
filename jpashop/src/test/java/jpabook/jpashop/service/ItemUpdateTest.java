package jpabook.jpashop.service;

import java.awt.print.Book;

import javax.persistence.EntityManager;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ItemUpdateTest {

	/* ���氨���� ����(merge)
	   
	
	*/
	
	@Autowired
	EntityManager em;
	
	@Test
	public void updateTest() throws Exception{
		Book book = em.find(Book.class, 1L);	
	
		
		// ���氨�� = dirty
		// �ؿ��� ��ƼƼ? - ���Ӽ� ���ؽ�Ʈ�� ���� �����ϴ� �ʴ� ��ƼƼ�� ���Ѵ�. 
		// itemServie.saveItem(book)���� ������ �õ��Ҷ� Book ���״� �̹� DB�� �ѹ� ����Ǿ �ĺ��ڰ� ����. ���� �ĺ��ڸ� ������ �����Ƿ� �ؿ��ӿ�ƼƼ��.
		// �ؿ��� ��ƼƼ�� �����ϴ� ��� : ���氨����� ��� , ���� (merge) ���
	}
	
}
