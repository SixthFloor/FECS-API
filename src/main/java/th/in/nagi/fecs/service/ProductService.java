package th.in.nagi.fecs.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import th.in.nagi.fecs.model.Order;
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

	public Product findByKey(Integer id) {
		return productRepository.findByKey(id);
	}

	public void store(Product product) {
		productRepository.store(product);
	}

	/**
	 * {@inheritDoc}
	 */
	public List<Product> findAll() {
		return productRepository.findAll();
	}

	public Product findByProductNumber(String productNumber) {
		return productRepository.findByProductNumber(productNumber);
	}
	
	public List<Product> findAvailableByProductDescription(ProductDescription pd, int quantity) {
		List<Product> productList = productRepository.findAvailableByProductDescription(pd);
		if (productList.size() >= quantity) {
			return productList.subList(0, quantity);
		}
		return new ArrayList<Product>();
	}
	
	public int findAvailableQuantityByProductDescription(ProductDescription pd) {
		return productRepository.findAvailableByProductDescription(pd).size();
	}

	public boolean release(Integer productId) {
		Product p = productRepository.findByKey(productId);
		if (p != null) {
			p.setStatus(Product.AVAILABLE);
			p.resetBoughtPrice();
			return true;
		}
		return false;
	}

	public boolean updateBoughtPrice(Integer productId) {
		Product p = productRepository.findByKey(productId);
		if (p != null) {
			p.setBoughtPrice();
			return true;
		}
		return false;
	}
}
