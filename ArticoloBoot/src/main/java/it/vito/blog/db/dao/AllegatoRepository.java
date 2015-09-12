package it.vito.blog.db.dao;

import it.vito.blog.db.bean.Allegato;

import org.springframework.data.repository.CrudRepository;

public interface AllegatoRepository extends CrudRepository<Allegato, Integer>{
	
	@SuppressWarnings("unchecked")
	public Allegato save(Allegato allegato);

}
