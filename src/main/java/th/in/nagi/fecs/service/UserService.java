package th.in.nagi.fecs.service;

import java.util.List;

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
     * Deletes user by username
     * 
     * @param username
     *            username
     */
    void removeByUsername(String username);

    /**
     * Finds all existing users
     * 
     * @return list of users
     */
    List<User> findAll();

    /**
     * Find user by username
     * 
     * @param username
     *            username
     * @return user
     */
    User findByUsername(String username);

    /**
     * Returns <tt>true</tt> if username is unique
     * 
     * @param i
     *            id
     * @param username
     *            username
     * @return <tt>true</tt> if username is unique
     */
    boolean isUsernameUnique(Integer i, String username);

}
