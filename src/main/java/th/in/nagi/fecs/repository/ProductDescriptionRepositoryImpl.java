package th.in.nagi.fecs.repository;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import th.in.nagi.fecs.model.ProductDescription;

/**
 * Tools for managing product.
 * 
 * @author Thanachote Visetsuthimont
 *
 */
@Repository("productDescriptionRepository")
public class ProductDescriptionRepositoryImpl extends AbstractRepository<ProductDescription, Integer>
		implements ProductDescriptionRepository {

	/**
	 * Query all products in database.
	 * 
	 * @return List<FurnitureDescription>
	 */
	@Override
	public List<ProductDescription> findAll() {
		Criteria criteria = createEntityCriteria();
		return (List<ProductDescription>) criteria.setFetchMode("images", FetchMode.LAZY).list();
	}

	/**
	 * Query product by key.
	 * 
	 * @return FurnitureDescription
	 */
	@Override
	public ProductDescription findByKey(Integer key) {
		return getByKey(key);
	}

	/**
	 * Save product in database.
	 * 
	 * @param entity
	 */
	@Override
	public void store(ProductDescription entity) {
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
	public ProductDescription findBySerialNumber(String serialNumber) {
		Criteria criteria = createEntityCriteria();
		criteria.add(Restrictions.eq("serialNumber", serialNumber));
		return (ProductDescription) criteria.uniqueResult();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void removeBySerialNumber(String serialNumber) {
		remove(findBySerialNumber(serialNumber));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<ProductDescription> findAndAscByName(int start, int size) {
		List<ProductDescription> furnitureDescriptions = createEntityCriteria().setFetchMode("images", FetchMode.LAZY)
				.addOrder(org.hibernate.criterion.Order.asc("name")).setFirstResult(start).setMaxResults(size).list();
		return furnitureDescriptions;
	}

	@Override
	public List<ProductDescription> findAndDescByName(int start, int size) {
		List<ProductDescription> furnitureDescriptions = createEntityCriteria().setFetchMode("images", FetchMode.LAZY)
				.addOrder(org.hibernate.criterion.Order.desc("name")).setFirstResult(start).setMaxResults(size).list();
		return furnitureDescriptions;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<ProductDescription> findAndAscByPrice(int start, int size) {
		List<ProductDescription> furnitureDescriptions = createEntityCriteria()
				.addOrder(org.hibernate.criterion.Order.asc("price")).setFirstResult(start).setMaxResults(size).list();
		return furnitureDescriptions;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<ProductDescription> findAndDescByPrice(int start, int size) {
		List<ProductDescription> furnitureDescriptions = createEntityCriteria()
				.addOrder(org.hibernate.criterion.Order.desc("price")).setFirstResult(start).setMaxResults(size).list();
		return furnitureDescriptions;
	}

}