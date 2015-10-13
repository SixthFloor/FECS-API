package th.in.nagi.fecs.repository;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import th.in.nagi.fecs.model.Authenticate;
import th.in.nagi.fecs.model.Product;
import th.in.nagi.fecs.model.User;

@Repository("AuthenticateRopository")
public class AuthenticateRopositoryImpl extends AbstractRepository<Authenticate, Integer> implements AuthenticateRepository{

	@Override
	public List<Authenticate> findAll() {
		// TODO Auto-generated method stub
		Criteria criteria = createEntityCriteria();
        return (List<Authenticate>) criteria.list();
	}

	@Override
	public Authenticate findByKey(Integer key) {
		// TODO Auto-generated method stub
		return getByKey(key);
	}

	@Override
	public void store(Authenticate entity) {
		// TODO Auto-generated method stub
		persist(entity);
	}

	@Override
	public void remove(Integer key) {
		// TODO Auto-generated method stub
		remove(getByKey(key));
	}

	@Override
	public void remove(Authenticate entity) {
		// TODO Auto-generated method stub
		
		
	}

	@Override
	public Authenticate findByToken(String token) {
		// TODO Auto-generated method stub
		Criteria criteria = createEntityCriteria();
        criteria.add(Restrictions.eq("token", token));
        return (Authenticate) criteria.uniqueResult();
	}

	@Override
	public Authenticate findByUsername(String username) {
		// TODO Auto-generated method stub
		Criteria criteria = createEntityCriteria();
        criteria.add(Restrictions.eq("username", username));
        return (Authenticate) criteria.uniqueResult();
	}

	@Override
	public void removeByToken(String token) {
		// TODO Auto-generated method stub
		Query query = getSession().createSQLQuery(
                "delete from authenticate where token = :token");
        query.setString("token", token);
        query.executeUpdate();
	}

}
