package th.in.nagi.fecs.repository;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import th.in.nagi.fecs.model.Product;
import th.in.nagi.fecs.model.User;

@Repository("productRepository")
public class ProductRepositoryImpl extends AbstractRepository<Product, Integer> implements ProductRepository{

	@Override
	public List<Product> findAll() {
		Criteria criteria = createEntityCriteria();
        return (List<Product>) criteria.list();
	}

	@Override
	public Product findByKey(Integer key) {
		return getByKey(key);
	}

	@Override
	public void store(Product entity) {
		persist(entity);
		
	}

	@Override
	public void remove(Integer key) {
		remove(getByKey(key));
	}

	@Override
	public Product findBySerialNumber(String serialNumber) {
		Criteria criteria = createEntityCriteria();
        criteria.add(Restrictions.eq("serialNumber", serialNumber));
        return (Product) criteria.uniqueResult();
	}

	@Override
	public void removeBySerialNumber(String serialNumber) {
		Query query = getSession().createSQLQuery(
                "delete from product where username = :serialNumber");
        query.setString("serialNumber", serialNumber);
        query.executeUpdate();
		
	}



}
