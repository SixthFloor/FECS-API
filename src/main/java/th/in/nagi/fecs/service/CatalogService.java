package th.in.nagi.fecs.service;

import java.util.List;

import th.in.nagi.fecs.model.Catalog;
import th.in.nagi.fecs.model.Category;
import th.in.nagi.fecs.model.SubCategory;

public interface CatalogService {

	Catalog findByKey(Integer id);

	void store(Catalog catalog);

	void update(Catalog catalog);

	void removeById(Integer id);

//	void removeByName(String name);

	List<Catalog> findAll();

	List<Catalog> findAndAscByName(int start, int size);

	List<Catalog> findAndDescByName(int start, int size);

//	Category findByName(String name);
	
	List<Catalog> findByCategory(Category category);
	
	List<Catalog> findByCategoryAndSubCategory(Category category, SubCategory subcategory);
}
