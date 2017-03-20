package it.vito.blog.db.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import it.vito.blog.db.bean.Property;

public interface AnagraficaProprietaRepository extends CrudRepository<Property, Integer>{

	@Override
	public List<Property> findAll();
	
	public List<Property> findByNomeProprieta(String nome);

}
