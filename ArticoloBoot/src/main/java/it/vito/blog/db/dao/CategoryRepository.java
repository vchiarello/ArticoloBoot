package it.vito.blog.db.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import it.vito.blog.db.bean.Category;


public interface CategoryRepository extends CrudRepository<Category, Integer> {

	List<Category> findAll();
}
