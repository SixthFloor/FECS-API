package th.in.nagi.fecs.repository;

import th.in.nagi.fecs.model.User;

/**
 * Repository for users
 * 
 * @author Chonnipa Kittisiriprasert
 *
 */
public interface UserRepository extends Repository<User, Integer> {

    /**
     * Finds a user that matches the given username.
     * 
     * @param username
     *            username
     * @return user that matches the given username
     */
    User findByUsername(String username);

    /**
     * Removes an existing user from this repository.
     * 
     * @param username
     *            username
     */
    void removeByUsername(String username);

}
