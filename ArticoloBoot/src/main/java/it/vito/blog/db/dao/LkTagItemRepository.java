package it.vito.blog.db.dao;

import java.util.List;

import it.vito.blog.db.bean.Item;
import it.vito.blog.db.bean.LkTagItem;
import it.vito.blog.db.bean.Tag;

import org.springframework.data.repository.CrudRepository;

public interface LkTagItemRepository extends CrudRepository<LkTagItem, Integer> {
	
	public List<LkTagItem> findByTagAndItem(Tag tag, Item item);
	public List<LkTagItem> findByItem(Item item);
	public void deleteByItem(Item item);
	
	

	
}
