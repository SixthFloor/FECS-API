package th.in.nagi.fecs.service;

import java.util.List;

import th.in.nagi.fecs.model.Category;


public interface CategoryService {

    Category findByKey(Integer id);

    void store(Category category);

    void update(Category category);

    List<Category> findAll();

	public List<Category> findAndAscByName(int start, int size);
	
	public List<Category> findAndDescByName(int start, int size);

}
