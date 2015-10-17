package th.in.nagi.fecs.repository;

import java.util.List;

import th.in.nagi.fecs.model.Category;
import th.in.nagi.fecs.model.Product;

public interface CategoryRepository extends Repository<Category, Integer>{
	
	List<Category> findAndAscByName(int start, int size);
	
	List<Category> findAndDescByName(int start, int size);
	
	Category findByName(String name);
}
