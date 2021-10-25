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
		itemRepository.save(item);// merge��뺸�ٴ� ItemService.upteItem ���氨�� ����� �� ����
	}
	
	
	@Transactional
	public void upteItem(Long itemId, String name, int price , int stockQuantity) { // ���� �������� �ʿ��� �Ķ���� �� DTO �� ���� Update �ϴ°� ����
		// Item findItem�� ���ӻ����̹Ƿ� @Transactional�� ���ؼ� update commit�Ѵ�(���氨��)
		Item findItem = itemRepository.findOne(itemId);
		//findItem.change(price,name,stockQunitity) �޼ҵ带 ���� ���� ����ϴ°� �� ����
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
