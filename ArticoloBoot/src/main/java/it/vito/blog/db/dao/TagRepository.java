package it.vito.blog.db.dao;

import it.vito.blog.db.bean.Tag;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface TagRepository extends CrudRepository<Tag, Integer>{

	@Override
	public List<Tag> findAll();
	
	public Tag findById(Integer id);
	
}
