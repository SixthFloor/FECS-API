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
	
	public List<Product> findByProductDescription(ProductDescription pd, int quantity) {
		List<Product> productList = productRepository.findByProductDescription(pd);
		if (productList.size() > quantity) {
			return productRepository.findByProductDescription(pd).subList(0, quantity);
		}
		return new ArrayList<Product>();
	}
}
