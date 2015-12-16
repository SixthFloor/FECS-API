package th.in.nagi.fecs.repository;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import th.in.nagi.fecs.model.Category;
import th.in.nagi.fecs.model.SubCategory;
import th.in.nagi.fecs.model.Type;

/**
 * Tool for managing role in database.
 * 
 * @author Thanachote Visetsuthimont
 *
 */
@Repository("typeRepository")
public class TypeRepository extends AbstractRepository<Type, Integer> {

	/**
	 * Query Types by Category.
	 * 
	 * @param category
	 * @return List<Type>
	 */
	public List<Type> findByCategory(Category category) {
		Criteria criteria = createEntityCriteria();
		criteria.add(Restrictions.eq("category", category)).setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		return criteria.list();
	}

	/**
	 * Query Types by Category and SubCategory.
	 * 
	 * @param category
	 * @param subcategory
	 * @return List<Type>
	 */
	public Type findByCategoryAndSubCategory(Category category, SubCategory subcategory) {
		Criteria criteria = createEntityCriteria();
		criteria.add(Restrictions.eq("category", category));
		criteria.add(Restrictions.eq("subCategory", subcategory)).setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		return (Type) criteria.uniqueResult();
	}

}
