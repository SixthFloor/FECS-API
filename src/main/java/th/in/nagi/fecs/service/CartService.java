package th.in.nagi.fecs.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import th.in.nagi.fecs.model.Cart;
import th.in.nagi.fecs.model.User;
import th.in.nagi.fecs.repository.CartRepository;

/**
 * Provide Cart service for managing easier. Ex. add, find.
 * 
 * @author Thanachote Visetsuthimont
 *
 */
@Service("cartService")
@Transactional
public class CartService {

	/**
	 * Tool for managing cart in database.
	 */
	@Autowired
	private CartRepository cartRepository;

	/**
	 * Find Cart by using id.
	 * 
	 * @param id
	 * @return Cart
	 */
	public Cart findByKey(Integer id) {
		return cartRepository.findByKey(id);
	}

	/**
	 * Save Cart in database.
	 * 
	 * @param Cart
	 *            new cart
	 */
	public void store(Cart cart) {
		cartRepository.store(cart);
	}

	/**
	 * Save Cart in database.
	 * 
	 * @param cart
	 *            new cart
	 * @return Integer cart's id
	 */
	public Integer save(Cart cart) {
		return cartRepository.save(cart);
	}

	/**
	 * Find all Cart in database.
	 * 
	 * @return List<Cart>
	 */
	public List<Cart> findAll() {
		return cartRepository.findAll();
	}

	/**
	 * Find Cart by User.
	 * 
	 * @param user
	 * @return List<Cart>
	 */
	public List<Cart> findByUser(User user) {
		return cartRepository.findByUser(user);
	}

	/**
	 * Find latest Cart.
	 * 
	 * @return Cart
	 */
	public Cart findLastInserted() {
		List<Cart> carts = cartRepository.findAll();
		return carts.get(carts.size() - 1);
	}
}
