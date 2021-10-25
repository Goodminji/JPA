package jpabook.jpashop.repository;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import jpabook.jpashop.domain.item.Item;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ItemRepository {

	private final EntityManager em;
	
	public void save(Item item) {
		if(item.getId() == null) {
			em.persist(item);//insert
		}else {
			em.merge(item);//update 모든 데이터를 update 하고 만약 null이어도 null로 업데이트 - 사용 주의요망
			               //merge 보다 변경감지로 사용하는게 좋음(ItemService.upteItem 변경감지 사용이 더 좋음)
		}
	}

	
	public Item findOne(Long id) {
		return em.find(Item.class, id);
	}
	
	public List<Item> findAll(){
		return em.createQuery("select i from Item i",Item.class).getResultList();
	}
}
