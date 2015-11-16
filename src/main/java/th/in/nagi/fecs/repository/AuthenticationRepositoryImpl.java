package th.in.nagi.fecs.repository;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import th.in.nagi.fecs.model.Authentication;

/**
 * Tool for managing authentication in database.
 * 
 * @author Nara Surawit
 *
 */
@Repository("authenticationRepository")
public class AuthenticationRepositoryImpl extends AbstractRepository<Authentication, Integer>
		implements AuthenticationRepository {

	/**
	 * Query all authentication in database.
	 * 
	 * @return List<Authentication>
	 */
	@Override
	public List<Authentication> findAll() {
		Criteria criteria = createEntityCriteria();
		return (List<Authentication>) criteria.list();
	}

	/**
	 * Query authentication by key.
	 * 
	 * @param key
	 * @return Authentication
	 */
	@Override
	public Authentication findByKey(Integer key) {
		return getByKey(key);
	}

	/**
	 * Save authentication to database.
	 * 
	 * @param entity
	 */
	@Override
	public void store(Authentication entity) {
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

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Authentication findByToken(String token) {
		Criteria criteria = createEntityCriteria();
		criteria.add(Restrictions.eq("token", token));
		return (Authentication) criteria.uniqueResult();
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
	@Override
	public void removeByToken(String token) {
		Query query = getSession().createSQLQuery("delete from authenticate where token = :token");
		query.setString("token", token);
		query.executeUpdate();
	}

}
