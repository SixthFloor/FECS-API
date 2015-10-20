package th.in.nagi.fecs.repository;

import java.util.List;

import th.in.nagi.fecs.model.User;

/**
 * Repository for users
 * 
 * @author Chonnipa Kittisiriprasert
 *
 */
public interface UserRepository extends Repository<User, Integer> {

	/**
	 * Finds a user that matches the given email.
	 * 
	 * @param email
	 *            email
	 * @return user that matches the given email
	 */
	User findByEmail(String email);

	/**
	 * Removes an existing user from this repository.
	 * 
	 * @param email
	 *            email
	 */
	void removeByEmail(String email);

	/**
	 * Query users with limit size and ascending by name.
	 * 
	 * @param start
	 * @param size
	 * @return List<User>
	 */
	List<User> findAndAscByFirstName(int start, int size);

	/**
	 * Query users with limit size and descending by name.
	 * 
	 * @param start
	 * @param size
	 * @return List<User>
	 */
	List<User> findAndDescByFirstName(int start, int size);

}
