package th.in.nagi.fecs.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import th.in.nagi.fecs.model.Category;
import th.in.nagi.fecs.model.Product;
import th.in.nagi.fecs.model.SubCategory;
import th.in.nagi.fecs.model.User;
import th.in.nagi.fecs.repository.CategoryRepository;
import th.in.nagi.fecs.repository.ProductRepository;
import th.in.nagi.fecs.repository.SubCategoryRepository;
import th.in.nagi.fecs.repository.UserRepository;

@Service("subCategoryService")
@Transactional
public class SubCategoryServiceImpl implements SubCategoryService {

	@Autowired
    private SubCategoryRepository subCategoryRepository;

	@Override
	public SubCategory findByKey(Integer id) {
		return subCategoryRepository.findByKey(id);
	}

	@Override
	public void store(SubCategory subCategory) {
		subCategoryRepository.store(subCategory);
		
	}

	@Override
	public void update(SubCategory subCategory) {
		SubCategory entity = subCategoryRepository.findByKey(subCategory.getId());
        if (entity != null) {
            entity.setName(subCategory.getName());
            entity.setCategory(subCategory.getCategory());
       }	
	}

	@Override
	public List<SubCategory> findAll() {
		return subCategoryRepository.findAll();
	}
	
	@Override
	public SubCategory findByName(String name) {
		return subCategoryRepository.findByName(name);
	}

	@Override
	public List<SubCategory> findAndAscByName(int start, int size) {
		return subCategoryRepository.findAndAscByName(start, size);
	}

	@Override
	public List<SubCategory> findAndDescByName(int start, int size) {
		return subCategoryRepository.findAndDescByName(start, size);
	}
	


}
