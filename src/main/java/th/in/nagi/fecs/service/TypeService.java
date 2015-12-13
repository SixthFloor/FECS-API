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

/**
 * Provide Type service for managing data easier. Ex. add, remove, edit.
 * 
 * @author Thanachote Visetsuthimont
 *
 */
@Service("typeService")
@Transactional
public class TypeService {

	/**
	 * Tool for managing data in database.
	 */
	@Autowired
	TypeRepository typeRepository;

	/**
	 * Find Type by using id.
	 * 
	 * @param id
	 *            id of Type
	 * @return Type
	 * 
	 */
	public Type findByKey(Integer id) {
		return typeRepository.findByKey(id);
	}

	/**
	 * Add new Type.
	 * 
	 * @param type
	 *            new Type
	 * 
	 */
	public void store(Type type) {
		typeRepository.store(type);
	}

	/**
	 * Remove Type by id
	 * 
	 * @param id
	 *            id of Type
	 * 
	 */
	public void removeById(Integer id) {
		typeRepository.remove(id);
	}

	/**
	 * Get all Type from database.
	 * 
	 * @return List<type>
	 */
	public List<Type> findAll() {
		return typeRepository.findAll();
	}

	/**
	 * Edit Type.
	 * 
	 * @param type
	 *            old type
	 * 
	 */
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

	/**
	 * Return list of Types in a category.
	 * 
	 * @param category
	 * 
	 * @return List<type>
	 * 
	 */
	public List<Type> findByCategory(Category category) {
		return typeRepository.findByCategory(category);
	}

	/**
	 * Return Type in a category and a subcategory.
	 * 
	 * @param category
	 * @param subcategory
	 * 
	 * @return Type
	 * 
	 */
	public Type findByCategoryAndSubCategory(Category category, SubCategory subcategory) {
		return typeRepository.findByCategoryAndSubCategory(category, subcategory);
	}

	/**
	 * Return list of SubCategories in a category.
	 * 
	 * @param category
	 * 
	 * @return List<SubCategory>
	 * 
	 */
	public List<SubCategory> findSubcategoryByCategory(Category category) {
		List<SubCategory> categories = new ArrayList<>();
		for (Type type : typeRepository.findByCategory(category)) {
			categories.add(type.getSubCategory());
		}
		return categories;
	}

	/**
	 * Return list of ProductDescriptions in a category.
	 * 
	 * @param category
	 * 
	 * @return List<ProductDescription>
	 * 
	 */
	public List<ProductDescription> findProductByCategory(Category category) {
		List<ProductDescription> productDescriptions = new ArrayList<>();
		for (Type type : typeRepository.findByCategory(category)) {
			for (Catalog catalog : type.getCatalogs()) {
				productDescriptions.add(catalog.getProductDescription());
			}
		}
		return productDescriptions;
	}

	/**
	 * Return list of ProductDescriptions in a category and a subcategory.
	 * 
	 * @param category
	 * @param subcategory
	 * 
	 * @return List<ProductDescription>
	 * 
	 */
	public List<ProductDescription> findProductByCategoryAndSubCategory(Category category, SubCategory subcategory) {
		List<ProductDescription> productDescriptions = new ArrayList<>();
		for (Catalog catalog : typeRepository.findByCategoryAndSubCategory(category, subcategory).getCatalogs()) {
			productDescriptions.add(catalog.getProductDescription());
		}
		return productDescriptions;
	}

}
