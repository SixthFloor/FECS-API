package th.in.nagi.fecs.repository;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import th.in.nagi.fecs.model.User;

/**
 * Implemented user repository
 * 
 * @author Chonnipa Kittisiriprasert
 *
 */
@Repository("userRepository")
public class UserRepository extends AbstractRepository<User, Integer> {

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<User> findAll() {
		Criteria criteria = createEntityCriteria();
		return criteria.setFetchMode("authenticate", FetchMode.LAZY).list();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public User findByKey(Integer key) {
		return getByKey(key);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void store(User user) {
		persist(user);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void remove(Integer key) {
		remove(key);
	}

	/**
	 * {@inheritDoc}
	 */
	public User findByEmail(String email) {
		Criteria criteria = createEntityCriteria();
		criteria.add(Restrictions.eq("email", email));
		return (User) criteria.uniqueResult();
	}

	/**
	 * Delete User by email.
	 * 
	 * @param email
	 */
	public void removeByEmail(String email) {
		remove(findByEmail(email));
	}

	/**
	 * Query category with limit size and ascending by name.
	 * 
	 * @param start
	 * @param size
	 * 
	 * @return List<User>
	 */
	public List<User> findAndAscByFirstName(int start, int size) {
		List<User> list = createEntityCriteria().setFetchMode("authenticate", FetchMode.LAZY)
				.addOrder(org.hibernate.criterion.Order.asc("firstName")).setFirstResult(start).setMaxResults(size)
				.list();
		return list;
	}

	/**
	 * Query category with limit size and descending by name.
	 * 
	 * @param start
	 * @param size
	 * 
	 * @return List<User>
	 */
	public List<User> findAndDescByFirstName(int start, int size) {
		List<User> list = createEntityCriteria().setFetchMode("authenticate", FetchMode.LAZY)
				.addOrder(org.hibernate.criterion.Order.desc("firstName")).setFirstResult(start).setMaxResults(size)
				.list();
		return list;
	}

}
