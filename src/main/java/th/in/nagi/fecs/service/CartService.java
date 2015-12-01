package th.in.nagi.fecs.service;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import th.in.nagi.fecs.model.Cart;
import th.in.nagi.fecs.model.Catalog;
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

//	public void store(Cart cart) {
//		cartRepository.store(cart);
//	}

	public void update(Cart cart) {
		Cart entity = cartRepository.findByKey(cart.getId());
		if (entity != null) {
			if (cart.getQuantity() > 0) {
				entity.setQuantity(cart.getQuantity());
			}
		}
	}

	public void removeById(Integer id) {
		cartRepository.remove(id);
	}

	public List<Cart> findAll() {
		return cartRepository.findAll();
	}

	public List<Cart> findByUser(User user) {
		return cartRepository.findByUser(user);
	}
	
	public boolean createCart(User user, ProductDescription productDescription, int quantity) {
		if (user == null || productDescription == null || quantity < 1){
			return false;
		}
		Cart cart = new Cart();
		cart.setProductDescription(productDescription);
		cart.setQuantity(quantity);
		cart.setUser(user);
		cartRepository.store(cart);
		return true;
	}
	
	public Cart findByProductAndUser(ProductDescription product, User user){
		return cartRepository.findByProductAndUser(product, user);
	}

}
