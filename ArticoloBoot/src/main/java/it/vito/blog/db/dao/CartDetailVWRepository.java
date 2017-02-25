package it.vito.blog.db.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import it.vito.blog.db.bean.CartDetailVW;


public interface CartDetailVWRepository extends CrudRepository<CartDetailVW,Integer>{

	public CartDetailVW findByIdCartDetail(Integer id);
	public List<CartDetailVW> findByUtente(String utente);
	
}
