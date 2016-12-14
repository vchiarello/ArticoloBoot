package it.vito.blog.db.dao;

import org.springframework.data.repository.CrudRepository;

import it.vito.blog.db.bean.Cart;
import it.vito.blog.db.bean.CartDetail;
import it.vito.blog.db.bean.CartDetailId;


public interface CartDetailRepository extends CrudRepository<CartDetail,CartDetailId>{

	public Cart findByIdCartDetail(CartDetailId id);

}
