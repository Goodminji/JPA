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
import jpabook.jpashop.exception.NotEnoughStockException;
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
	
	//비지니스 로직
	//재고 수량 증가
	public void addStock(int quantity) {
		this.stockQuantity += quantity;
	}
	
	//재고 수량 감소
	public void removeStock(int quantity) {
		int restStock = this.stockQuantity - quantity;
		
		if(restStock < 0) {
			throw new NotEnoughStockException("need more stock");
		}
		
		this.stockQuantity = restStock;
	}
	
}
