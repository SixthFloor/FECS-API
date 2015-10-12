package th.in.nagi.fecs.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import th.in.nagi.fecs.model.Category;
import th.in.nagi.fecs.model.Product;
import th.in.nagi.fecs.model.User;
import th.in.nagi.fecs.repository.ProductRepository;
import th.in.nagi.fecs.repository.UserRepository;

//@Service("categoryService")
@Transactional
public class CategoryServiceImpl implements CategoryService {

	@Autowired
    private CategoryService categoryRepository;

	@Override
	public Category findByKey(Integer id) {
		return categoryRepository.findByKey(id);
	}

	@Override
	public void store(Category category) {
		categoryRepository.store(category);
		
	}

	@Override
	public void update(Category category) {
		Category entity = categoryRepository.findByKey(category.getId());
        if (entity != null) {
            entity.setName(category.getName());
       }	
	}

	@Override
	public List<Category> findAll() {
		return categoryRepository.findAll();
	}
	


}