package th.in.nagi.fecs.repository;

import th.in.nagi.fecs.model.Product;

public interface ProductRepository extends Repository<Product, Integer>{
	
	public Product findBySerialNumber(String serialNumber);
	
	public void removeBySerialNumber(String serialNumber);
}
