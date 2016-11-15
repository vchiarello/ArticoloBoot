package it.vito.blog.db.dao;

import java.util.List;

import it.vito.blog.db.bean.AnagraficaProprieta;
import it.vito.blog.db.bean.Item;
import it.vito.blog.db.bean.LkPropertyItem;

import org.springframework.data.repository.CrudRepository;

public interface LkItemPropertyItemRepository extends CrudRepository<LkPropertyItem, Integer> {
	
	public List<LkPropertyItem> findByPropAndItem(AnagraficaProprieta prop, Item item);
	public List<LkPropertyItem> findByItem(Item item);
	public void deleteByItem(Item item);
	public void deleteByItemAndProp(Item item, AnagraficaProprieta prop);
	@SuppressWarnings("unchecked")
	public LkPropertyItem save(LkPropertyItem lk);
	
}
