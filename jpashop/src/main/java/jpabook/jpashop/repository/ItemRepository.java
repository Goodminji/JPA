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
			em.merge(item);//update ��� �����͸� update �ϰ� ���� null�̾ null�� ������Ʈ - ��� ���ǿ��
			               //merge ���� ���氨���� ����ϴ°� ����(ItemService.upteItem ���氨�� ����� �� ����)
		}
	}

	
	public Item findOne(Long id) {
		return em.find(Item.class, id);
	}
	
	public List<Item> findAll(){
		return em.createQuery("select i from Item i",Item.class).getResultList();
	}
}
