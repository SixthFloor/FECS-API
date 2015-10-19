package th.in.nagi.fecs.service;

import java.util.List;

import th.in.nagi.fecs.model.Role;
import th.in.nagi.fecs.model.User;

/**
 * 
 * @author Chonnipa Kittisiriprasert
 *
 */
public interface UserService {

	/**
	 * Finds user by ID.
	 * 
	 * @param id
	 *            id
	 */
	User findByKey(Integer id);

	/**
	 * Saves user to repository
	 * 
	 * @param user
	 *            user
	 */
	void store(User user);

	/**
	 * Updates user
	 * 
	 * @param user
	 *            user
	 */
	void update(User user);

	/**
	 * Deletes user by email
	 * 
	 * @param email
	 *            email
	 */
	void removeByEmail(String email);

	/**
	 * Finds all existing users
	 * 
	 * @return list of users
	 */
	List<User> findAll();

	/**
	 * Find user by email
	 * 
	 * @param email
	 *            email
	 * @return user
	 */
	User findByEmail(String email);

	/**
	 * Returns <tt>true</tt> if email is unique
	 * 
	 * @param i
	 *            id
	 * @param email
	 *            email
	 * @return <tt>true</tt> if email is unique
	 */
	boolean isEmailUnique(Integer i, String username);

}
