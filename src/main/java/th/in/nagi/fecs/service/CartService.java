package th.in.nagi.fecs.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import th.in.nagi.fecs.model.Cart;
import th.in.nagi.fecs.model.ProductDescription;
import th.in.nagi.fecs.model.User;
import th.in.nagi.fecs.repository.CartRepository;

/**
 * Provide Category service for managing category easier. Ex. add, edit, delete,
 * find.
 * 
 * @author Thanachote Visetsuthimont
 *
 */
@Service("cartService")
@Transactional
public class CartService {

	@Autowired
	private CartRepository cartRepository;

	public Cart findByKey(Integer id) {
		return cartRepository.findByKey(id);
	}

	public void store(Cart cart) {
		cartRepository.store(cart);
	}

	public List<Cart> findAll() {
		return cartRepository.findAll();
	}

	public List<Cart> findByUser(User user) {
		return cartRepository.findByUser(user);
	}
	
	public Cart findLastInserted() {
		List<Cart> carts = cartRepository.findAll();
		return carts.get(carts.size() - 1);
	}
}
