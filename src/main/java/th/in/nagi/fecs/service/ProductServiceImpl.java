package th.in.nagi.fecs.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import th.in.nagi.fecs.model.Product;
import th.in.nagi.fecs.repository.ProductRepository;

/**
 * Provide product service for managing easier. 
 * Ex. add, remove, edit.
 * 
 * @author Thanachote Visetsuthimont
 *
 */
@Service("productService")
@Transactional
public class ProductServiceImpl implements ProductService {

	/**
	 * Tool for managing product in database.
	 */
	@Autowired
	private ProductRepository productRepository;

	/**
	 * Find product by using index.
	 * 
	 * @param id
	 * @return Product
	 */
	@Override
	public Product findByKey(Integer id) {
		return productRepository.findByKey(id);
	}

	/**
	 * Save product in database.
	 * 
	 * @param product
	 */
	@Override
	public void store(Product product) {
		productRepository.store(product);
	}

	/**
	 * Update product's detail in database.
	 */
	@Override
	public void update(Product product) {
		Product entity = productRepository.findByKey(product.getId());
		if (entity != null) {
			entity.setSerialNumber(product.getSerialNumber());
			entity.setName(product.getName());
			entity.setPrice(product.getPrice());
			entity.setDescription(product.getDescription());
			entity.setDimensionDescription(product.getDimensionDescription());
			entity.setImages(product.getImages());
			entity.setSubCategory(product.getSubCategory());
		}
	}

	/**
	 * Find all products in database.
	 * 
	 * @return List<Product>
	 */
	@Override
	public List<Product> findAll() {
		return productRepository.findAll();
	}

	/**
	 * Find products by using serial number.
	 * 
	 * @return Product
	 */
	@Override
	public Product findBySerialNumber(String serialNumber) {
		return productRepository.findBySerialNumber(serialNumber);
	}

	/**
	 * Remove products by using serial number.
	 * 
	 * @param serialNumber
	 */
	@Override
	public void removeBySerialNumber(String serialNumber) {
		productRepository.removeBySerialNumber(serialNumber);

	}

	/**
	 * Find products with limit size and ascending by name.
	 * 
	 * @param start
	 * @param size
	 * @return List<Product>
	 */
	@Override
	public List<Product> findAndAscByName(int start, int size) {
		return productRepository.findAndAscByName(start, size);
	}

	/**
	 * Find products with limit size and descending by name.
	 * 
	 * @param start
	 * @param size
	 * @return List<Product>
	 */
	@Override
	public List<Product> findAndDescByName(int start, int size) {
		return productRepository.findAndDescByName(start, size);
	}

	/**
	 * Find products with limit size and ascending by price.
	 * 
	 * @param start
	 * @param size
	 * @return List<Product>
	 */
	@Override
	public List<Product> findAndAscByPrice(int start, int size) {
		return productRepository.findAndAscByPrice(start, size);
	}

	/**
	 * Find products with limit size and descending by price.
	 * 
	 * @param start
	 * @param size
	 * @return List<Product>
	 */
	@Override
	public List<Product> findAndDescByPrice(int start, int size) {
		return productRepository.findAndDescByPrice(start, size);
	}
}
