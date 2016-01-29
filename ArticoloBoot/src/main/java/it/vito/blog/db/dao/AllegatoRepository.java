package it.vito.blog.db.dao;

import it.vito.blog.db.bean.Allegato;
import it.vito.blog.db.bean.Item;

import org.springframework.data.repository.CrudRepository;

public interface AllegatoRepository extends CrudRepository<Allegato, Integer>{
	
	@SuppressWarnings("unchecked")
	public Allegato save(Allegato allegato);

	public Allegato findOne(Integer id);
	public Allegato findByIdAndNomeAllegato(Integer id, String nomeAllegato);
	public void deleteByItem(Item item);

}
