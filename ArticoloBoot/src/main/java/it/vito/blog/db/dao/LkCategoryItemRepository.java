package it.vito.blog.db.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import it.vito.blog.db.bean.LkCategoryItem;

public interface LkCategoryItemRepository extends CrudRepository<LkCategoryItem, Integer> {

	List<LkCategoryItem> findAll();
}
