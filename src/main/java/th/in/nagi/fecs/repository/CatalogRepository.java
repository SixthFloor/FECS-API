package th.in.nagi.fecs.repository;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import th.in.nagi.fecs.model.Catalog;
import th.in.nagi.fecs.model.Category;
import th.in.nagi.fecs.model.SubCategory;
import th.in.nagi.fecs.model.Type;

/**
 * Tool for managing Catalog in database.
 * 
 * @author Nara Surawit
 *
 */
@Repository("catalogRepository")
public class CatalogRepository extends AbstractRepository<Catalog, Integer> {

	/**
	 * Query all Catalog in database.
	 * 
	 * @return List<Authentication>
	 */
	@Override
	public List<Catalog> findAll() {
		Criteria criteria = createEntityCriteria();
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		return criteria.list();
	}

	/**
	 * Query Catalog by key.
	 * 
	 * @param key
	 * @return Authentication
	 */
	@Override
	public Catalog findByKey(Integer key) {
		return getByKey(key);
	}

	/**
	 * Save Catalog to database.
	 * 
	 * @param entity
	 */
	@Override
	public void store(Catalog entity) {
		persist(entity);
	}

	/**
	 * Remove Catalog by key.
	 * 
	 * @param key
	 */
	@Override
	public void remove(Integer key) {
		remove(getByKey(key));
	}

	public List<Catalog> findByType(Type type) {
		Criteria criteria = createEntityCriteria();
		criteria.add(Restrictions.eq("type", type)).setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		return criteria.list();
	}
}
