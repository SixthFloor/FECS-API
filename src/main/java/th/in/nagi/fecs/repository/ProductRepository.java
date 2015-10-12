package th.in.nagi.fecs.repository;

import java.util.List;

import javax.persistence.criteria.Order;

import th.in.nagi.fecs.model.Product;

public interface ProductRepository extends Repository<Product, Integer>{
	
	public Product findBySerialNumber(String serialNumber);
	
	public void removeBySerialNumber(String serialNumber);
	
	public List<Product> findAndAscByName(int start, int size);
	
	public List<Product> findAndDescByName(int start, int size);
	
	public List<Product> findAndAscByPrice(int start, int size);
	
	public List<Product> findAndDescByPrice(int start, int size);
	
}
