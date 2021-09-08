package jpabook.jpashop.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import jpabook.jpashop.domain.item.Item;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class Category {

	@Id
	@GeneratedValue
	@Column(name = "category_id")
	private Long id;
	
	private String name;
	
	@ManyToMany
	// 중간 테이블 생성 (필드가 더 추가가 불가해서 운영에서 사용 잘안함)
	@JoinTable(name="category_item",
		joinColumns = @JoinColumn(name="category_id"),
		inverseJoinColumns = @JoinColumn(name="item_id"))
	private List<Item> items = new ArrayList<>();
	
	@ManyToOne(fetch = FetchType.LAZY) // 내 부모 - 계층구조 설정
	@JoinColumn(name="parent_id")
	private Category parent;
	
	@OneToMany(mappedBy = "parent") // 내 부모 - 계층구조 설정
	private List<Category> child = new ArrayList<Category>();
	
	// 연관관계 메서드 (양방향 관계일때)
	public void addChildCategory(Category child) {
		this.child.add(child);
		child.setParent(this);
	}
	

}
