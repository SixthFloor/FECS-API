package th.in.nagi.fecs.repository;


import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import th.in.nagi.fecs.model.User;

@Repository("userRepository")
public class UserRepositoryImpl extends AbstractRepository<Integer, User> implements UserRepository {

	public User findById(int id) {
		return getByKey(id);
	}

	public void save(User user) {
		persist(user);
	}

	public void deleteByUsername(String username) {
		Query query = getSession().createSQLQuery("delete from f_user where username = :username");
		query.setString("username", username);
		query.executeUpdate();
	}

	@SuppressWarnings("unchecked")
	public List<User> findAll() {
		Criteria criteria = createEntityCriteria();
		return (List<User>) criteria.list();
	}

	public User findByUsername(String username) {
		Criteria criteria = createEntityCriteria();
		criteria.add(Restrictions.eq("username", username));
		return (User) criteria.uniqueResult();
	}
}
