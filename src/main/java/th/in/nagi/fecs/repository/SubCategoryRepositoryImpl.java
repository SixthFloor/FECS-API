package th.in.nagi.fecs.repository;

import java.util.List;

import javax.annotation.PostConstruct;

import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.ModelAttribute;

import th.in.nagi.fecs.model.SubCategory;

/**
 * Tools for managing subcategory.
 * 
 * @author Thanachote Visetsuthimont
 *
 */
@Repository("subCategoryRopository")
public class SubCategoryRepositoryImpl extends AbstractRepository<SubCategory, Integer>
		implements SubCategoryRepository {

	/**
	 * Query all subcategories in database.
	 * 
	 * @return List<SubCategory>
	 */
	@Override
	public List<SubCategory> findAll() {
		Criteria criteria = createEntityCriteria();
		return (List<SubCategory>) criteria.setFetchMode("products", FetchMode.LAZY).list();
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
	 * {@inheritDoc}
	 */
	@Override
	public List<SubCategory> findAndAscByName(int start, int size) {
		List<SubCategory> list = createEntityCriteria().setFetchMode("products", FetchMode.LAZY)
				.addOrder(org.hibernate.criterion.Order.asc("name")).setFirstResult(start).setMaxResults(size).list();
		return list;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<SubCategory> findAndDescByName(int start, int size) {
		List<SubCategory> list = createEntityCriteria().setFetchMode("products", FetchMode.LAZY)
				.addOrder(org.hibernate.criterion.Order.desc("name")).setFirstResult(start).setMaxResults(size).list();
		return list;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public SubCategory findByName(String name) {
		Criteria criteria = createEntityCriteria();
		criteria.add(Restrictions.eq("name", name));
		return (SubCategory) criteria.uniqueResult();
	}
}