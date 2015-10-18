package th.in.nagi.fecs.repository;

import java.util.List;

import th.in.nagi.fecs.model.Category;
import th.in.nagi.fecs.model.Product;
import th.in.nagi.fecs.model.SubCategory;

public interface SubCategoryRepository extends Repository<SubCategory, Integer>{
	
	List<SubCategory> findAndAscByName(int start, int size);
	
	List<SubCategory> findAndDescByName(int start, int size);
	
	SubCategory findByName(String name);
}
