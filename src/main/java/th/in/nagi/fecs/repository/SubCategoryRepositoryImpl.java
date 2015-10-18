package th.in.nagi.fecs.repository;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import th.in.nagi.fecs.model.Category;
import th.in.nagi.fecs.model.SubCategory;

@Repository("subCategoryRopository")
public class SubCategoryRepositoryImpl extends AbstractRepository<SubCategory, Integer>implements SubCategoryRepository {

	@Override
	public List<SubCategory> findAll() {
		Criteria criteria = createEntityCriteria();
		return (List<SubCategory>) criteria.list();
	}

	@Override
	public SubCategory findByKey(Integer key) {
		return getByKey(key);
	}

	@Override
	public void store(SubCategory entity) {
		persist(entity);
	}

	@Override
	public void remove(Integer key) {
		remove(getByKey(key));
	}

	@Override
	public List<SubCategory> findAndAscByName(int start, int size) {
		List<SubCategory> list = createEntityCriteria().addOrder(org.hibernate.criterion.Order.asc("name"))
				.setFirstResult(start).setFetchSize(size).list();
		return list;
	}

	@Override
	public List<SubCategory> findAndDescByName(int start, int size) {
		List<SubCategory> list = createEntityCriteria().addOrder(org.hibernate.criterion.Order.desc("name"))
				.setFirstResult(start).setFetchSize(size).list();
		return list;
	}

	@Override
	public SubCategory findByName(String name) {
		Criteria criteria = createEntityCriteria();
		criteria.add(Restrictions.eq("name", name));
		return (SubCategory) criteria.uniqueResult();
	}
}
