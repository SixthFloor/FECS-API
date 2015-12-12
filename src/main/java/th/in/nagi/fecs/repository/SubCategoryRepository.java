package th.in.nagi.fecs.repository;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import th.in.nagi.fecs.model.SubCategory;

/**
 * Tools for managing subcategory.
 * 
 * @author Thanachote Visetsuthimont
 *
 */
@Repository("subCategoryRopository")
public class SubCategoryRepository extends AbstractRepository<SubCategory, Integer> {

	/**
	 * Query all subcategories in database.
	 * 
	 * @return List<SubCategory>
	 */
	@Override
	public List<SubCategory> findAll() {
		Criteria criteria = createEntityCriteria();
		return criteria.list();
	}

	/**
	 * Query subcategory by key.
	 * 
	 * @return SubCategory
	 */
	@Override
	public SubCategory findByKey(Integer key) {
		return getByKey(key);
	}

	/**
	 * Save subcategory in database.
	 * 
	 * @param entity
	 */
	@Override
	public void store(SubCategory entity) {
		persist(entity);
	}

	/**
	 * Remove subcategory in database by key.
	 * 
	 * @param key
	 */
	@Override
	public void remove(Integer key) {
		remove(getByKey(key));
	}

	/**
	 * Query SubCategory with limit size and ascending by name.
	 * 
	 * @param start
	 * @param size
	 * 
	 * @return List<SubCategory>
	 */
	public List<SubCategory> findAndAscByName(int start, int size) {
		List<SubCategory> list = createEntityCriteria().addOrder(org.hibernate.criterion.Order.asc("name"))
				.setFirstResult(start).setMaxResults(size).list();
		return list;
	}

	/**
	 * Query SubCategory with limit size and descending by name.
	 * 
	 * @param start
	 * @param size
	 * 
	 * @return List<SubCategory>
	 */
	public List<SubCategory> findAndDescByName(int start, int size) {
		List<SubCategory> list = createEntityCriteria().addOrder(org.hibernate.criterion.Order.desc("name"))
				.setFirstResult(start).setMaxResults(size).list();
		return list;
	}

	/**
	 * Query SubCategory name.
	 * 
	 * @param name
	 * 
	 * @return SubCategory
	 */
	public SubCategory findByName(String name) {
		Criteria criteria = createEntityCriteria();
		criteria.add(Restrictions.eq("name", name));
		return (SubCategory) criteria.uniqueResult();
	}
}
