package th.in.nagi.fecs.repository;

import java.util.List;

import th.in.nagi.fecs.model.Category;

/**
 * Collection of tools for category.
 * 
 * @author Thanachote Visetsuthimont
 *
 */
public interface CategoryRepository extends Repository<Category, Integer> {

	/**
	 * Query categories with limit size and ascending by name.
	 * 
	 * @param start
	 * @param size
	 * @return List<Category>
	 */
	List<Category> findAndAscByName(int start, int size);

	/**
	 * Query categories with limit size and descending by name.
	 * 
	 * @param start
	 * @param size
	 * @return List<Category>
	 */
	List<Category> findAndDescByName(int start, int size);

	/**
	 * Query category by name.
	 * 
	 * @param name
	 * @return Category
	 */
	Category findByName(String name);
}
