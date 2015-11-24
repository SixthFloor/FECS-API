package th.in.nagi.fecs.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import th.in.nagi.fecs.model.Catalog;
import th.in.nagi.fecs.model.Category;
import th.in.nagi.fecs.model.SubCategory;
import th.in.nagi.fecs.model.Type;
import th.in.nagi.fecs.repository.TypeRepository;

@Service("typeService")
@Transactional
public class TypeService {

	@Autowired
	TypeRepository typeRepository;

	public Type findByKey(Integer id) {
		return typeRepository.findByKey(id);
	}

	public void store(Type role) {
		typeRepository.store(role);
	}

	public void removeById(Integer id) {
		typeRepository.remove(id);
	}

	public List<Type> findAll() {
		return typeRepository.findAll();
	}
	
	public List<Type> findByCategory(Category category) {
		return typeRepository.findByCategory(category);
	}

	public List<Type> findByCategoryAndSubCategory(Category category, SubCategory subcategory) {
		return typeRepository.findByCategoryAndSubCategory(category, subcategory);
	}
	
	public List<SubCategory> findSubcategoryByCategory(Category category) {
		List<SubCategory> categories = new ArrayList<>();
		for (Type type:typeRepository.findByCategory(category)) {
			categories.add(type.getSubCategory());
		}
		return categories;
	}
	
	

}
