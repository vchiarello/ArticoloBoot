package it.vito.blog.db.dao;

import org.springframework.data.repository.CrudRepository;

import it.vito.blog.db.bean.Cart;


public interface CartRepository extends CrudRepository<Cart,Integer>{

	public Cart findByIdCart(Integer id);
	public Cart findByUtente(String utente);

}
