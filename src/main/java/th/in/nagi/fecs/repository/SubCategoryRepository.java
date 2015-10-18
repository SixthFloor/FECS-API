package th.in.nagi.fecs.repository;

import java.util.List;

import th.in.nagi.fecs.model.SubCategory;

/**
 * Collection of tools for subcategory.
 * 
 * @author Thanachote Visetsuthimont
 *
 */
public interface SubCategoryRepository extends Repository<SubCategory, Integer> {

	/**
	 * Query subcategories with limit size and ascending by name.
	 * 
	 * @param start
	 * @param size
	 * @return List<SubCategory>
	 */
	List<SubCategory> findAndAscByName(int start, int size);

	/**
	 * Query subcategories with limit size and descending by name.
	 * 
	 * @param start
	 * @param size
	 * @return List<SubCategory>
	 */
	List<SubCategory> findAndDescByName(int start, int size);

	/**
	 * Query subcategory by name.
	 * 
	 * @param name
	 * @return SubCategory
	 */
	SubCategory findByName(String name);
}
