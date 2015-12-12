package th.in.nagi.fecs.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import th.in.nagi.fecs.model.Product;
import th.in.nagi.fecs.model.ProductDescription;
import th.in.nagi.fecs.repository.ProductRepository;

/**
 * 
 * Product Service
 * 
 * @author Chonnipa Kittisiriprasert
 *
 */
@Service("productService")
@Transactional
public class ProductService {

	/**
	 * Tool for managing product in database.
	 */
	@Autowired
	private ProductRepository productRepository;

	/**
	 * Find product by using id.
	 * 
	 * @param id
	 * @return Product
	 */
	public Product findByKey(Integer id) {
		return productRepository.findByKey(id);
	}

	/**
	 * Save product in database.
	 * 
	 * @param product
	 *            new product
	 */
	public void store(Product product) {
		productRepository.store(product);
	}

	/**
	 * Find all product in database.
	 * 
	 * @return List<Product>
	 */
	public List<Product> findAll() {
		return productRepository.findAll();
	}

	/**
	 * Find product by product number.
	 * 
	 * @param productNumber
	 * @return Product
	 */
	public Product findByProductNumber(String productNumber) {
		return productRepository.findByProductNumber(productNumber);
	}

	/**
	 * Find available product by ProductDescription and quantity. if available
	 * product is 0 or not enough will return empty list
	 * 
	 * @param productDescription
	 * @param quantity
	 * @return List<Product>
	 */
	public List<Product> findAvailableByProductDescription(ProductDescription productDescription, int quantity) {
		List<Product> productList = productRepository.findAvailableByProductDescription(productDescription);
		if (productList.size() >= quantity) {
			return productList.subList(0, quantity);
		}
		return new ArrayList<Product>();
	}

	/**
	 * Find number of available product by ProductDescription.
	 * 
	 * @param productDescription
	 * @return int number of available product
	 */
	public int findAvailableQuantityByProductDescription(ProductDescription productDescription) {
		return productRepository.findAvailableByProductDescription(productDescription).size();
	}

	/**
	 * Reset status to AVAILABLE and BoughtPrice to 0 when user cancel.
	 * 
	 * @param productId
	 * @return boolean if release is successful.
	 */
	public boolean release(Integer productId) {
		Product p = productRepository.findByKey(productId);
		if (p != null) {
			p.setStatus(Product.AVAILABLE);
			p.resetBoughtPrice();
			return true;
		}
		return false;
	}
	
	/**
	 * lock the price for unchanging after payment. 
	 * 
	 * @param productId
	 * @return boolean if update price is successful.
	 */
	public boolean updateBoughtPrice(Integer productId) {
		Product p = productRepository.findByKey(productId);
		if (p != null) {
			p.setBoughtPrice();
			return true;
		}
		return false;
	}
}
