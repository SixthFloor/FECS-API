package th.in.nagi.fecs.repository;

import th.in.nagi.fecs.model.Authentication;

/**
 * Collection of tool for managing authentication in database.
 * 
 * @author Nara Surawit
 *
 */
public interface AuthenticationRepository extends Repository<Authentication, Integer> {

	/**
	 * Find authentication by token
	 * 
	 * @param token
	 * @return Authentication
	 */
	public Authentication findByToken(String token);

	// public List<Authentication> findByUser(User username);

	/**
	 * Remove authentication by token
	 * 
	 * @param token
	 */
	public void removeByToken(String token);

}
