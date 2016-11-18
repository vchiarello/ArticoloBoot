package it.vito.blog.db.dao;

import it.vito.blog.db.bean.AllegatoInfo;
import it.vito.blog.db.bean.Item;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface AllegatoInfoRepository extends CrudRepository<AllegatoInfo, Integer>{
	
	public List<AllegatoInfo> findByIdItemAndNome(Integer idItem, String nomeAllegato);

}
