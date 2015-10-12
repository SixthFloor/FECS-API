package th.in.nagi.fecs.repository;

import java.util.List;

import javax.persistence.criteria.Order;

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

	@Override
	public List<Product> findAndAscByName(int start, int size) {
		List<Product> products = createEntityCriteria().addOrder(org.hibernate.criterion.Order.asc("name")).setFirstResult(start).setFetchSize(size).list();
		return products;
	}

	@Override
	public List<Product> findAndDescByName(int start, int size) {
		List<Product> products = createEntityCriteria().addOrder(org.hibernate.criterion.Order.desc("name")).setFirstResult(start).setFetchSize(size).list();
		return products;
	}

	@Override
	public List<Product> findAndAscByPrice(int start, int size) {
		List<Product> products = createEntityCriteria().addOrder(org.hibernate.criterion.Order.asc("price")).setFirstResult(start).setFetchSize(size).list();
		return products;
	}

	@Override
	public List<Product> findAndDescByPrice(int start, int size) {
		List<Product> products = createEntityCriteria().addOrder(org.hibernate.criterion.Order.desc("price")).setFirstResult(start).setFetchSize(size).list();
		return products;
	}



}
