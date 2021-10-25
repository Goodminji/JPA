package jpabook.jpashop.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.repository.ItemRepository;
import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemService {

	private final ItemRepository itemRepository;
	
	@Transactional
	public void saveItem(Item item) {
		itemRepository.save(item);// merge사용보다는 ItemService.upteItem 변경감지 사용이 더 좋음
	}
	
	
	@Transactional
	public void upteItem(Long itemId, String name, int price , int stockQuantity) { // 서비스 계층에서 필요한 파라미터 나 DTO 로 만들어서 Update 하는게 좋음
		// Item findItem는 영속상태이므로 @Transactional로 인해서 update commit한다(변경감지)
		Item findItem = itemRepository.findOne(itemId);
		//findItem.change(price,name,stockQunitity) 메소드를 따로 만들어서 사용하는게 더 좋음
		findItem.setPrice(price);
		findItem.setName(name);
		findItem.setStockQuantity(stockQuantity);
	}
	
	public List<Item> findItems(){
		return itemRepository.findAll();
	}
	
	public Item findOne(Long itemId) {
		return itemRepository.findOne(itemId);
	}
}
