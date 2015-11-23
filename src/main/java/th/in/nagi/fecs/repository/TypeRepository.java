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
 * Tool for managing role in database.
 * 
 * @author Thanachote Visetsuthimont
 *
 */
@Repository("typeRepository")
public class TypeRepository extends AbstractRepository<Type, Integer> {

	public List<Type> findByCategory(Category category) {
		Criteria criteria = createEntityCriteria();
		criteria.add(Restrictions.eq("category_id", category.getId()));
		return criteria.list();
	}

	public List<Type> findByCategoryAndSubCategory(Category category, SubCategory subcategory) {
		Criteria criteria = createEntityCriteria();
		criteria.add(Restrictions.eq("category_id", category.getId()));
		criteria.add(Restrictions.eq("sub_category_id", subcategory.getId()));
		return criteria.list();
	}

}
