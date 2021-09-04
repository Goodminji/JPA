package jpabook.jpashop.domain.item;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToMany;

import jpabook.jpashop.domain.Category;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
@Inheritance(strategy = InheritanceType.SINGLE_TABLE) // 상속 설정 - SINGLE_TABLE 한테이블로 설정할꺼다.
@DiscriminatorColumn(name="dtype") // 어떤 컬럼으로 구분을 할지 설정 
public abstract class Item {

	@Id
	@GeneratedValue
	@Column(name = "item_id")
	private Long id;
	
	private String name;
	private int price;
	private int stockQuantity;
	
	@ManyToMany(mappedBy = "items")
	private List<Category> categories = new ArrayList<>();
	
}
