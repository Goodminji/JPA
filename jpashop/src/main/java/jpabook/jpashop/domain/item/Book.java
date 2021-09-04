package jpabook.jpashop.domain.item;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
@DiscriminatorValue("B")// Item 클래스에서 @DiscriminatorColumn - dtype 에 어떤 값으로 들어갈지 설정
public class Book extends Item{

	private String author;
	private String isbn;
}
