package th.in.nagi.fecs.service;

import java.util.List;

import th.in.nagi.fecs.model.ProductDescription;

public interface ProductDescriptionService {

	ProductDescription findByKey(Integer id);

	void store(ProductDescription furnitureDescription);

	void update(ProductDescription furnitureDescription);

	List<ProductDescription> findAll();

	ProductDescription findBySerialNumber(String serialNumber);

	void removeBySerialNumber(String serialNumber);

	List<ProductDescription> findAndAscByName(int start, int size);

	List<ProductDescription> findAndDescByName(int start, int size);

	List<ProductDescription> findAndAscByPrice(int start, int size);

	List<ProductDescription> findAndDescByPrice(int start, int size);

}
