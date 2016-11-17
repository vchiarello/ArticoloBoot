package it.vito.blog.db.dao;

import it.vito.blog.db.bean.AllegatoInfo;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface AllegatoInfoRepository extends CrudRepository<AllegatoInfo, Integer>{
	
	public List<AllegatoInfo> findByIdAndNome(Integer idItem, String nomeAllegato);

}
