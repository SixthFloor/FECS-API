package th.in.nagi.fecs.repository;

import java.util.List;

import org.hibernate.Criteria;

import th.in.nagi.fecs.model.Category;
import th.in.nagi.fecs.model.Product;

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

}
