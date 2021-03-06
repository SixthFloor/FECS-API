package th.in.nagi.fecs.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import th.in.nagi.fecs.model.Category;
import th.in.nagi.fecs.repository.CategoryRepository;

/**
 * Provide Category service for managing easier. Ex. add, edit, delete,
 * find.
 * 
 * @author Thanachote Visetsuthimont
 *
 */
@Service("categoryService")
@Transactional
public class CategoryService {

	/**
	 * Tool for managing category
	 */
	@Autowired
	private CategoryRepository categoryRepository;

	/**
	 * Find category by using index.
	 * 
	 * @param id
	 * @return Category
	 */
	public Category findByKey(Integer id) {
		return categoryRepository.findByKey(id);
	}

	/**
	 * Save category in database.
	 * 
	 * @param category
	 */
	public void store(Category category) {
		categoryRepository.store(category);

	}

	/**
	 * Update detail of category.
	 * 
	 * @param category
	 */
	public void update(Category category) {
		Category entity = categoryRepository.findByKey(category.getId());
		if (entity != null) {
			if (category.getName() != null) {
				entity.setName(category.getName());
			}
		}
	}

	/**
	 * Find all category in database.
	 * 
	 * @return List<Category>
	 */
	public List<Category> findAll() {
		return categoryRepository.findAll();
	}

	/**
	 * Find category by using name.
	 * 
	 * @param name
	 * @return Category
	 */
	public Category findByName(String name) {
		return categoryRepository.findByName(name);
	}

	/**
	 * Find category with limit size and ascending by name.
	 * 
	 * @param start
	 * @param size
	 * @return List<Category>
	 */
	public List<Category> findAndAscByName(int start, int size) {
		return categoryRepository.findAndAscByName(start, size);
	}

	/**
	 * Find category with limit size and descending by name.
	 * 
	 * @param start
	 * @param size
	 * @return List<Category>
	 */
	public List<Category> findAndDescByName(int start, int size) {
		return categoryRepository.findAndDescByName(start, size);
	}

	/**
	 * Remove category by using id.
	 * 
	 * @param id
	 */
	public void removeById(Integer id) {
		categoryRepository.remove(id);
	}

	/**
	 * Remove category by using category's name.
	 * 
	 * @param name
	 */
	public void removeByName(String name) {
		categoryRepository.remove(categoryRepository.findByName(name));
	}
}
