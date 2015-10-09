package th.in.nagi.fecs.repository;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
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
public class UserRepositoryImpl extends AbstractRepository<User, Integer>
        implements UserRepository {

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<User> findAll() {
        Criteria criteria = createEntityCriteria();
        return (List<User>) criteria.list();
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
        // TODO Auto-generated method stub

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public User findByUsername(String username) {
        Criteria criteria = createEntityCriteria();
        criteria.add(Restrictions.eq("username", username));
        return (User) criteria.uniqueResult();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeByUsername(String username) {
        Query query = getSession().createSQLQuery(
                "delete from f_user where username = :username");
        query.setString("username", username);
        query.executeUpdate();
    }

}
