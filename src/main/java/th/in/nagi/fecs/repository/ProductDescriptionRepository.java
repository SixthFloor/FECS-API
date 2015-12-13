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
public class ProductDescriptionRepository extends AbstractRepository<ProductDescription, Integer> {

	/**
	 * Query all Product Descriptions in database.
	 * 
	 * @return List<ProductDescription>
	 */
	@Override
	public List<ProductDescription> findAll() {
		Criteria criteria = createEntityCriteria();
		return criteria.setFetchMode("images", FetchMode.LAZY).list();
	}

	/**
	 * Query Product Description by key.
	 * 
	 * @return ProductDescription
	 */
	@Override
	public ProductDescription findByKey(Integer key) {
		return getByKey(key);
	}

	/**
	 * Save Product Description in database.
	 * 
	 * @param productDescription
	 */
	@Override
	public void store(ProductDescription productDescription) {
		persist(productDescription);

	}

	/**
	 * Remove Product Description in database by key.
	 * 
	 * @param key
	 */
	@Override
	public void remove(Integer key) {
		remove(getByKey(key));
	}

	/**
	 * Query Product Description by serial number.
	 * 
	 * @param serialNumber
	 * 
	 * @return ProductDescription
	 */
	public ProductDescription findBySerialNumber(String serialNumber) {
		Criteria criteria = createEntityCriteria();
		criteria.add(Restrictions.eq("serialNumber", serialNumber));
		return (ProductDescription) criteria.uniqueResult();
	}

	/**
	 * Delete Product Description by serial number.
	 * 
	 * @param serialNumber
	 */
	public void removeBySerialNumber(String serialNumber) {
		remove(findBySerialNumber(serialNumber));
	}

	/**
	 * Query Product Descriptions with limit size and ascending by name.
	 * 
	 * @param start
	 * @param size
	 * 
	 * @return List<ProductDescription>
	 */
	public List<ProductDescription> findAndAscByName(int start, int size) {
		List<ProductDescription> furnitureDescriptions = createEntityCriteria().setFetchMode("images", FetchMode.LAZY)
				.addOrder(org.hibernate.criterion.Order.asc("name")).setFirstResult(start).setMaxResults(size).list();
		return furnitureDescriptions;
	}

	/**
	 * Query Product Descriptions with limit size and descending by name.
	 * 
	 * @param start
	 * @param size
	 * 
	 * @return List<ProductDescription>
	 */
	public List<ProductDescription> findAndDescByName(int start, int size) {
		List<ProductDescription> furnitureDescriptions = createEntityCriteria().setFetchMode("images", FetchMode.LAZY)
				.addOrder(org.hibernate.criterion.Order.desc("name")).setFirstResult(start).setMaxResults(size).list();
		return furnitureDescriptions;
	}

	/**
	 * Query Product Descriptions with limit size and ascending by price.
	 * 
	 * @param start
	 * @param size
	 * 
	 * @return List<ProductDescription>
	 */
	public List<ProductDescription> findAndAscByPrice(int start, int size) {
		List<ProductDescription> furnitureDescriptions = createEntityCriteria()
				.addOrder(org.hibernate.criterion.Order.asc("price")).setFirstResult(start).setMaxResults(size).list();
		return furnitureDescriptions;
	}

	/**
	 * Query Product Descriptions with limit size and descending by price.
	 * 
	 * @param start
	 * @param size
	 * 
	 * @return List<ProductDescription>
	 */
	public List<ProductDescription> findAndDescByPrice(int start, int size) {
		List<ProductDescription> furnitureDescriptions = createEntityCriteria()
				.addOrder(org.hibernate.criterion.Order.desc("price")).setFirstResult(start).setMaxResults(size).list();
		return furnitureDescriptions;
	}

	/**
	 * Query Product Descriptions with serial number or name. Return list of
	 * product descriptions that they may be according.
	 * 
	 * @param searchName
	 * 
	 * @return List<ProductDescription>
	 */
	public List<ProductDescription> search(String searchName) {
		Criteria criteria = createEntityCriteria();
		criteria.add(Restrictions.or(Restrictions.like("serialNumber", "%" + searchName + "%").ignoreCase(),
				Restrictions.like("name", "%" + searchName + "%").ignoreCase()));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		return criteria.list();
	}

}
