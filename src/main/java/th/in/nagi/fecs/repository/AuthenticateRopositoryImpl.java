package th.in.nagi.fecs.repository;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import th.in.nagi.fecs.model.Authenticate;

/**
 * Tool for managing authentication in database.
 * 
 * @author Nara Surawit
 *
 */
@Repository("authenticateRopository")
public class AuthenticateRopositoryImpl extends AbstractRepository<Authenticate, Integer>
		implements AuthenticateRepository {

	/**
	 * Query all authentication in database.
	 * 
	 * @return List<Authenticate>
	 */
	@Override
	public List<Authenticate> findAll() {
		Criteria criteria = createEntityCriteria();
		return (List<Authenticate>) criteria.list();
	}

	/**
	 * Query authentication by key.
	 * 
	 * @param key
	 * @return Authenticate
	 */
	@Override
	public Authenticate findByKey(Integer key) {
		return getByKey(key);
	}

	/**
	 * Save authentication to database.
	 * 
	 * @param entity
	 */
	@Override
	public void store(Authenticate entity) {
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
	public Authenticate findByToken(String token) {
		Criteria criteria = createEntityCriteria();
		criteria.add(Restrictions.eq("token", token));
		return (Authenticate) criteria.uniqueResult();
	}

	// @Override
	// public List<Authenticate> findByUser(User user) {
	// Criteria criteria = createEntityCriteria();
	// criteria.add(Restrictions.eq("user", user));
	// return (List<Authenticate>) criteria.list();
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
