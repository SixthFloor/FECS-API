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
public class AuthenticationRepository extends AbstractRepository<Authentication, Integer> {

	/**
	 * Query all authentication in database.
	 * 
	 * @return List<Authentication>
	 */
	@Override
	public List<Authentication> findAll() {
		Criteria criteria = createEntityCriteria();
		return criteria.list();
	}

	/**
	 * Query authentication by key.
	 * 
	 * @param id
	 * @return Authentication
	 */
	@Override
	public Authentication findByKey(Integer id) {
		return getByKey(id);
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
	 * @param id
	 */
	@Override
	public void remove(Integer id) {
		remove(getByKey(id));
	}

	/**
	 * Query authentication by token.
	 * 
	 * @param token
	 * @return Authentication
	 */
	public Authentication findByToken(String token) {
		Criteria criteria = createEntityCriteria();
		criteria.add(Restrictions.eq("token", token));
		return (Authentication) criteria.uniqueResult();
	}

	/**
	 * Delete authentication by token.
	 * 
	 * @param token
	 * 
	 */
	public void removeByToken(String token) {
		remove(findByToken(token));
	}

}
