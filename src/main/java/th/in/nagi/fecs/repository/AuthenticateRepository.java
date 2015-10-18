package th.in.nagi.fecs.repository;

import th.in.nagi.fecs.model.Authenticate;

/**
 * Collection of tool for managing authentication in database.
 * 
 * @author Nara Surawit
 *
 */
public interface AuthenticateRepository extends Repository<Authenticate, Integer> {

	/**
	 * Find authentication by token
	 * 
	 * @param token
	 * @return Authenticate
	 */
	public Authenticate findByToken(String token);

	// public List<Authenticate> findByUser(User username);

	/**
	 * Remove authentication by token
	 * 
	 * @param token
	 */
	public void removeByToken(String token);

}
