package th.in.nagi.fecs.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import th.in.nagi.fecs.model.Catalog;
import th.in.nagi.fecs.model.Category;
import th.in.nagi.fecs.model.ProductDescription;
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

	public void update(Type type) {
		Type entity = typeRepository.findByKey(type.getId());
		if (entity != null) {
			if (type.getCategory() != null) {
				entity.setCategory(type.getCategory());
			}
			if (type.getSubCategory() != null) {
				entity.setSubCategory(type.getSubCategory());
			}
			if (type.getCatalogs() != null) {
				entity.setCatalogs(type.getCatalogs());
			}
		}
	}
	
	public List<Type> findByCategory(Category category) {
		return typeRepository.findByCategory(category);
	}

	public Type findByCategoryAndSubCategory(Category category, SubCategory subcategory) {
		return typeRepository.findByCategoryAndSubCategory(category, subcategory);
	}

	public List<SubCategory> findSubcategoryByCategory(Category category) {
		List<SubCategory> categories = new ArrayList<>();
		for (Type type : typeRepository.findByCategory(category)) {
			categories.add(type.getSubCategory());
		}
		return categories;
	}

	public List<ProductDescription> findProductByCategory(Category category) {
		List<ProductDescription> productDescriptions = new ArrayList<>();
		for (Type type : typeRepository.findByCategory(category)) {
			for (Catalog catalog : type.getCatalogs()) {
				productDescriptions.add(catalog.getProductDescription());
			}
		}
		return productDescriptions;
	}

	public List<ProductDescription> findProductByCategoryAndSubCategory(Category category, SubCategory subcategory) {
		List<ProductDescription> productDescriptions = new ArrayList<>();
		// for (Type type:typeRepository.findByCategoryAndSubCategory(category,
		// subcategory)) {
		for (Catalog catalog : typeRepository.findByCategoryAndSubCategory(category, subcategory).getCatalogs()) {
			productDescriptions.add(catalog.getProductDescription());
			// }
		}
		return productDescriptions;
	}

}
