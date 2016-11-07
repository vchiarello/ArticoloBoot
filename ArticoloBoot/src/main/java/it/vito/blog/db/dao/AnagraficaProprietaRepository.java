package it.vito.blog.db.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import it.vito.blog.db.bean.AnagraficaProprieta;

public interface AnagraficaProprietaRepository extends CrudRepository<AnagraficaProprieta, Integer>{

	@Override
	public List<AnagraficaProprieta> findAll();
	
	public List<AnagraficaProprieta> findByNomeProprieta(String nome);

}
