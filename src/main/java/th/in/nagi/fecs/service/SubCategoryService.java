package th.in.nagi.fecs.service;

import java.util.List;

import th.in.nagi.fecs.model.SubCategory;

public interface SubCategoryService {

	SubCategory findByKey(Integer id);

	void store(SubCategory subCategory);

	void update(SubCategory subCategory);

	void removeById(Integer id);

	void removeByName(String name);

	List<SubCategory> findAll();

	List<SubCategory> findAndAscByName(int start, int size);

	List<SubCategory> findAndDescByName(int start, int size);

	SubCategory findByName(String name);
}
