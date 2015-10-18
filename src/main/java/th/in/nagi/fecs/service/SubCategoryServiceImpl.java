package th.in.nagi.fecs.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import th.in.nagi.fecs.model.SubCategory;
import th.in.nagi.fecs.repository.SubCategoryRepository;

/**
 * Provide SubCategory for managing data easier. 
 * Ex. add, remove, edit.
 * 
 * @author Thanachote Visetsuthimont
 *
 */
@Service("subCategoryService")
@Transactional
public class SubCategoryServiceImpl implements SubCategoryService {

	/**
	 * Tool for managing data in database
	 */
	@Autowired
	private SubCategoryRepository subCategoryRepository;

	/**
	 * Find subcategory by using index.
	 * 
	 * @param id
	 */
	@Override
	public SubCategory findByKey(Integer id) {
		return subCategoryRepository.findByKey(id);
	}

	/**
	 * Save subcategory in database.
	 * 
	 * @param subCategory
	 */
	@Override
	public void store(SubCategory subCategory) {
		subCategoryRepository.store(subCategory);

	}

	/**
	 * Update subcategory's detail.
	 * 
	 * @param subCategory
	 */
	@Override
	public void update(SubCategory subCategory) {
		SubCategory entity = subCategoryRepository.findByKey(subCategory.getId());
		if (entity != null) {
			entity.setName(subCategory.getName());
			entity.setCategory(subCategory.getCategory());
		}
	}

	/**
	 * Find all subcategorys in database.
	 * 
	 * @return List<SubCategory>
	 */
	@Override
	public List<SubCategory> findAll() {
		return subCategoryRepository.findAll();
	}

	/**
	 * Find subcategory by using name.
	 * 
	 * @param name
	 * @return SubCategory
	 */
	@Override
	public SubCategory findByName(String name) {
		return subCategoryRepository.findByName(name);
	}

	/**
	 * Find subcategorys with limit size and ascending by name.
	 * 
	 * @param start
	 * @param size
	 * @return List<SubCategory>
	 */
	@Override
	public List<SubCategory> findAndAscByName(int start, int size) {
		return subCategoryRepository.findAndAscByName(start, size);
	}

	/**
	 * Find subcategorys with limit size and descending by name.
	 * 
	 * @param start
	 * @param size
	 * @return List<SubCategory>
	 */
	@Override
	public List<SubCategory> findAndDescByName(int start, int size) {
		return subCategoryRepository.findAndDescByName(start, size);
	}

	/**
	 * Remove subcategory by using id
	 * 
	 * @param id
	 */
	@Override
	public void removeById(Integer id) {
		subCategoryRepository.remove(id);
	}

	/**
	 * Remove subcategory by using name.
	 * 
	 * @param name
	 */
	@Override
	public void removeByName(String name) {
		subCategoryRepository.remove(subCategoryRepository.findByName(name));
	}

}
