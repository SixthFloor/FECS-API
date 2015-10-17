package th.in.nagi.fecs.repository;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import th.in.nagi.fecs.model.Category;
import th.in.nagi.fecs.model.Product;
import th.in.nagi.fecs.model.User;

@Repository("categoryRopository")
public class CategoryRepositoryImpl extends AbstractRepository<Category, Integer> implements CategoryRepository{

	@Override
	public List<Category> findAll() {
		Criteria criteria = createEntityCriteria();
        return (List<Category>) criteria.list();
	}

	@Override
	public Category findByKey(Integer key) {
		return getByKey(key);
	}

	@Override
	public void store(Category entity) {
		persist(entity);
	}

	@Override
	public void remove(Integer key) {
		remove(getByKey(key));	
	}

	@Override
	public List<Category> findAndAscByName(int start, int size) {
		List<Category> list = createEntityCriteria().addOrder(org.hibernate.criterion.Order.asc("name")).setFirstResult(start).setFetchSize(size).list();
		return list;
	}

	@Override
	public List<Category> findAndDescByName(int start, int size) {
		List<Category> list = createEntityCriteria().addOrder(org.hibernate.criterion.Order.desc("name")).setFirstResult(start).setFetchSize(size).list();
		return list;
	}

	@Override
	public Category findByName(String name) {
		Criteria criteria = createEntityCriteria();
        criteria.add(Restrictions.eq("name", name));
        return (Category) criteria.uniqueResult();
	}
}
