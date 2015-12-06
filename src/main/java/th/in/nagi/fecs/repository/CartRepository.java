package th.in.nagi.fecs.repository;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import th.in.nagi.fecs.model.Authentication;
import th.in.nagi.fecs.model.Cart;
import th.in.nagi.fecs.model.User;

/**
 * Tool for managing authentication in database.
 * 
 * @author Nara Surawit
 *
 */
@Repository("cartRepository")
public class CartRepository extends AbstractRepository<Cart, Integer> {

	/**
	 * Query all authentication in database.
	 * 
	 * @return List<Authentication>
	 */
	@Override
	public List<Cart> findAll() {
		Criteria criteria = createEntityCriteria();
		return criteria.list();
	}

	/**
	 * Query authentication by key.
	 * 
	 * @param key
	 * @return Authentication
	 */
	@Override
	public Cart findByKey(Integer key) {
		return getByKey(key);
	}

	/**
	 * Save authentication to database.
	 * 
	 * @param entity
	 */
	@Override
	public void store(Cart entity) {
		persist(entity);
	}

	/**
	 * Remove authentication by key.
	 * 
	 * @param key
	 */
	@Override
	public void remove(Integer key) {
		remove(getByKey(key));
	}

	// @Override
	// public List<Authentication> findByUser(User user) {
	// Criteria criteria = createEntityCriteria();
	// criteria.add(Restrictions.eq("user", user));
	// return (List<Authentication>) criteria.list();
	// }

	/**
	 * {@inheritDoc}
	 */
	public List<Cart> findByUser(User user) {
		Criteria criteria = createEntityCriteria();
		criteria.add(Restrictions.eq("user", user)).setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		return criteria.list();
	}

}
