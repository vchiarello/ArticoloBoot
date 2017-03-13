package it.vito.blog.db.dao;


import java.util.List;
import java.util.stream.Stream;

import it.vito.blog.db.bean.Item;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface ItemRepository extends CrudRepository<Item, Integer>, QueryDslPredicateExecutor<Item>{

	@Override
	public List<Item> findAll();
	
	@Query("select a from Item a where a.dataPubblicazione <= sysdate() and (a.dataHidden > sysdate() or dataHidden is null) and (dataScadenza > sysdate() or dataScadenza is null) order by a.dataPubblicazione desc ")
	public List<Item> findAllAttivi();
	
	public Item findById(Integer id);
	
	//public int getCountById();
	
	@Query("select a from Item a where a.id > :idArticolo order by a.dataPubblicazione desc ")
	public Stream<Item> streamAll(@Param("idArticolo") Integer idArticolo);
	
	
	
	@SuppressWarnings("unchecked")
	public Item save(Item item);
	
}
