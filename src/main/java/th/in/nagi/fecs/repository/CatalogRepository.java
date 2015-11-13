package th.in.nagi.fecs.repository;

import java.util.List;

import th.in.nagi.fecs.model.Catalog;
import th.in.nagi.fecs.model.Category;
import th.in.nagi.fecs.model.SubCategory;

/**
 * Collection of tool for managing authentication in database.
 * 
 * @author Nara Surawit
 *
 */
public interface CatalogRepository extends Repository<Catalog, Integer> {

	List<Catalog> findByCategory(Category category);

	List<Catalog> findByCategoryAndSubCategory(Category category, SubCategory subcategory);

}
