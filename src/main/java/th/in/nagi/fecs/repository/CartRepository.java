package th.in.nagi.fecs.repository;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import th.in.nagi.fecs.model.Cart;
import th.in.nagi.fecs.model.User;

/**
 * Implemented cart repository.
 * 
 * @author Chonnipa Kittisiriprasert
 *
 */
@Repository("cartRepository")
public class CartRepository extends AbstractRepository<Cart, Integer> {

	/**
	 * Query all cart in database.
	 * 
	 * @return List<Cart>
	 */
	@Override
	public List<Cart> findAll() {
		Criteria criteria = createEntityCriteria();
		return criteria.list();
	}

	/**
	 * Query cart by key.
	 * 
	 * @param key
	 * @return Cart
	 */
	@Override
	public Cart findByKey(Integer key) {
		return getByKey(key);
	}

	/**
	 * Save cart to database.
	 * 
	 * @param entity
	 */
	@Override
	public void store(Cart entity) {
		persist(entity);
	}
	
	/**
	 * Remove cart by key.
	 * 
	 * @param key
	 */
	@Override
	public void remove(Integer key) {
		remove(getByKey(key));
	}

	/**
	 * {@inheritDoc}
	 */
	public List<Cart> findByUser(User user) {
		Criteria criteria = createEntityCriteria();
		criteria.add(Restrictions.eq("user", user));
		return criteria.list();
	}

}
