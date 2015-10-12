package th.in.nagi.fecs.repository;

import java.util.List;

import th.in.nagi.fecs.model.Category;
import th.in.nagi.fecs.model.Product;

public interface CategoryRepository extends Repository<Category, Integer>{
	
	public List<Category> findAndAscByName(int start, int size);
	
	public List<Category> findAndDescByName(int start, int size);
}
