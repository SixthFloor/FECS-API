package th.in.nagi.fecs.repository;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import th.in.nagi.fecs.model.FurnitureDescription;

/**
 * Tools for managing product.
 * 
 * @author Thanachote Visetsuthimont
 *
 */
@Repository("furnitureDescriptionRepository")
public class FurnitureDescriptionRepositoryImpl extends AbstractRepository<FurnitureDescription, Integer>implements FurnitureDescriptionRepository {

	/**
	 * Query all products in database.
	 * 
	 * @return List<FurnitureDescription>
	 */
	@Override
	public List<FurnitureDescription> findAll() {
		Criteria criteria = createEntityCriteria();
		return (List<FurnitureDescription>) criteria.setFetchMode("images", FetchMode.LAZY).list();
	}

	/**
	 * Query product by key.
	 * 
	 * @return FurnitureDescription
	 */
	@Override
	public FurnitureDescription findByKey(Integer key) {
		return getByKey(key);
	}

	/**
	 * Save product in database.
	 * 
	 * @param entity
	 */
	@Override
	public void store(FurnitureDescription entity) {
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
	public FurnitureDescription findBySerialNumber(String serialNumber) {
		Criteria criteria = createEntityCriteria();
		criteria.add(Restrictions.eq("serialNumber", serialNumber));
		return (FurnitureDescription) criteria.uniqueResult();
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
	public List<FurnitureDescription> findAndAscByName(int start, int size) {
		List<FurnitureDescription> furnitureDescriptions = createEntityCriteria().setFetchMode("images", FetchMode.LAZY).addOrder(org.hibernate.criterion.Order.asc("name"))
				.setFirstResult(start).setMaxResults(size).list();
		return furnitureDescriptions;
	}

	@Override
	public List<FurnitureDescription> findAndDescByName(int start, int size) {
		List<FurnitureDescription> furnitureDescriptions = createEntityCriteria().setFetchMode("images", FetchMode.LAZY).addOrder(org.hibernate.criterion.Order.desc("name"))
				.setFirstResult(start).setMaxResults(size).list();
		return furnitureDescriptions;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<FurnitureDescription> findAndAscByPrice(int start, int size) {
		List<FurnitureDescription> furnitureDescriptions = createEntityCriteria().addOrder(org.hibernate.criterion.Order.asc("price"))
				.setFirstResult(start).setMaxResults(size).list();
		return furnitureDescriptions;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<FurnitureDescription> findAndDescByPrice(int start, int size) {
		List<FurnitureDescription> furnitureDescriptions = createEntityCriteria().addOrder(org.hibernate.criterion.Order.desc("price"))
				.setFirstResult(start).setMaxResults(size).list();
		return furnitureDescriptions;
	}

}
