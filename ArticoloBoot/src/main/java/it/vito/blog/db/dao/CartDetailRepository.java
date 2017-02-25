package it.vito.blog.db.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import it.vito.blog.db.bean.Cart;
import it.vito.blog.db.bean.CartDetail;
import it.vito.blog.db.bean.Item;


public interface CartDetailRepository extends CrudRepository<CartDetail,Integer>{

	public CartDetail findByIdCartDetail(Integer id);
	public List<CartDetail> findByCartAndItem(Cart c, Item i);
	
}
