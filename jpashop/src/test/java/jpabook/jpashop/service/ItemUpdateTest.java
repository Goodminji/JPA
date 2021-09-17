package jpabook.jpashop.service;

import java.awt.print.Book;

import javax.persistence.EntityManager;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ItemUpdateTest {

	/* 변경감지와 병합(merge)
	   
	
	*/
	
	@Autowired
	EntityManager em;
	
	@Test
	public void updateTest() throws Exception{
		Book book = em.find(Book.class, 1L);	
	
		
		// 변경감지 = dirty
		// 준영속 엔티티? - 영속성 컨텍스트가 더는 관리하는 않는 엔티티를 말한다. 
		// itemServie.saveItem(book)에서 수정을 시도할때 Book 객테는 이미 DB에 한번 저장되어서 식별자가 존재. 기존 식별자를 가지고 있으므로 준영속엔티티다.
		// 준영속 엔티티를 수정하는 방법 : 변경감지기능 사용 , 병합 (merge) 사용
	}
	
}
