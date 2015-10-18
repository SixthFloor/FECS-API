package th.in.nagi.fecs.service;

import java.util.List;

import th.in.nagi.fecs.model.Product;

public interface ProductService {

	Product findByKey(Integer id);

	void store(Product product);

	void update(Product product);

	List<Product> findAll();

	Product findBySerialNumber(String serialNumber);

	void removeBySerialNumber(String serialNumber);

	List<Product> findAndAscByName(int start, int size);

	List<Product> findAndDescByName(int start, int size);

	List<Product> findAndAscByPrice(int start, int size);

	List<Product> findAndDescByPrice(int start, int size);

}
