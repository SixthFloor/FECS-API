package th.in.nagi.fecs.repository;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import th.in.nagi.fecs.model.Product;

/**
 * Tools for managing product.
 * 
 * @author Thanachote Visetsuthimont
 *
 */
@Repository("productRepository")
public class ProductRepositoryImpl extends AbstractRepository<Product, Integer>implements ProductRepository {

	/**
	 * Query all products in database.
	 * 
	 * @return List<Product>
	 */
	@Override
	public List<Product> findAll() {
		Criteria criteria = createEntityCriteria();
		return (List<Product>) criteria.list();
	}

	/**
	 * Query product by key.
	 * 
	 * @return Product
	 */
	@Override
	public Product findByKey(Integer key) {
		return getByKey(key);
	}

	/**
	 * Save product in database.
	 * 
	 * @param entity
	 */
	@Override
	public void store(Product entity) {
		persist(entity);

	}

	/**
	 * Remove product in database by key.
	 * 
	 * @param key
	 */
	@Override
	public void remove(Integer key) {
		remove(getByKey(key));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Product findBySerialNumber(String serialNumber) {
		Criteria criteria = createEntityCriteria();
		criteria.add(Restrictions.eq("serialNumber", serialNumber));
		return (Product) criteria.uniqueResult();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void removeBySerialNumber(String serialNumber) {
		Query query = getSession().createSQLQuery(
                "delete from product where serial_number = :serialNumber");
        query.setString("serialNumber", serialNumber);
        query.executeUpdate();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Product> findAndAscByName(int start, int size) {
		List<Product> products = createEntityCriteria().addOrder(org.hibernate.criterion.Order.asc("name"))
				.setFirstResult(start).setFetchSize(size).list();
		return products;
	}

	@Override
	public List<Product> findAndDescByName(int start, int size) {
		List<Product> products = createEntityCriteria().addOrder(org.hibernate.criterion.Order.desc("name"))
				.setFirstResult(start).setFetchSize(size).list();
		return products;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Product> findAndAscByPrice(int start, int size) {
		List<Product> products = createEntityCriteria().addOrder(org.hibernate.criterion.Order.asc("price"))
				.setFirstResult(start).setFetchSize(size).list();
		return products;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Product> findAndDescByPrice(int start, int size) {
		List<Product> products = createEntityCriteria().addOrder(org.hibernate.criterion.Order.desc("price"))
				.setFirstResult(start).setFetchSize(size).list();
		return products;
	}

}
