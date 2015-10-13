package th.in.nagi.fecs.repository;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.criterion.CriteriaQuery;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.hibernate.engine.spi.TypedValue;
import org.springframework.stereotype.Repository;

import th.in.nagi.fecs.model.Authenticate;
import th.in.nagi.fecs.model.Product;
import th.in.nagi.fecs.model.User;

@Repository("authenticateRopository")
public class AuthenticateRopositoryImpl extends AbstractRepository<Authenticate, Integer> implements AuthenticateRepository{

	@Override
	public List<Authenticate> findAll() {
		Criteria criteria = createEntityCriteria();
        return (List<Authenticate>) criteria.list();
	}

	@Override
	public Authenticate findByKey(Integer key) {
		return getByKey(key);
	}

	@Override
	public void store(Authenticate entity) {
		persist(entity);
	}

	@Override
	public void remove(Integer key) {
		remove(getByKey(key));
	}


	@Override
	public Authenticate findByToken(String token) {
		Criteria criteria = createEntityCriteria();
        criteria.add(Restrictions.eq("token", token));
        return (Authenticate) criteria.uniqueResult();
	}

	@Override
	public Authenticate findByUser(User user) {
		Criteria criteria = createEntityCriteria();
        criteria.add(Restrictions.eq("user", user));
        return (Authenticate) criteria.uniqueResult();
	}

	@Override
	public void removeByToken(String token) {
		Query query = getSession().createSQLQuery(
                "delete from authenticate where token = :token");
        query.setString("token", token);
        query.executeUpdate();
	}

}
