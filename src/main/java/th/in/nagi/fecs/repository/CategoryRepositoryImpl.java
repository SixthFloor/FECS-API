package th.in.nagi.fecs.repository;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import th.in.nagi.fecs.model.Category;

/**
 * Tools for managing category.
 * 
 * @author Thanachote Visetsuthimont
 *
 */
@Repository("categoryRopository")
public class CategoryRepositoryImpl extends AbstractRepository<Category, Integer>implements CategoryRepository {

	/**
	 * Query all categories in database.
	 * 
	 * @return List<Category>
	 */
	@Override
	public List<Category> findAll() {
		Criteria criteria = createEntityCriteria();
		return (List<Category>) criteria.list();
	}

	/**
	 * Query category by key.
	 * 
	 * @return Category
	 */
	@Override
	public Category findByKey(Integer key) {
		return getByKey(key);
	}

	/**
	 * Save category in database.
	 * 
	 * @param entity
	 */
	@Override
	public void store(Category entity) {
		persist(entity);
	}

	/**
	 * Remove category in database by key.
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
	public List<Category> findAndAscByName(int start, int size) {
		List<Category> list = createEntityCriteria().addOrder(org.hibernate.criterion.Order.asc("name"))
				.setFirstResult(start).setFetchSize(size).list();
		return list;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Category> findAndDescByName(int start, int size) {
		List<Category> list = createEntityCriteria().addOrder(org.hibernate.criterion.Order.desc("name"))
				.setFirstResult(start).setFetchSize(size).list();
		return list;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Category findByName(String name) {
		Criteria criteria = createEntityCriteria();
		criteria.add(Restrictions.eq("name", name));
		return (Category) criteria.uniqueResult();
	}

}
