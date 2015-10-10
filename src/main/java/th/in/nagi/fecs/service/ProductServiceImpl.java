package th.in.nagi.fecs.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import th.in.nagi.fecs.model.Product;
import th.in.nagi.fecs.model.User;
import th.in.nagi.fecs.repository.ProductRepository;
import th.in.nagi.fecs.repository.UserRepository;

@Service("productService")
@Transactional
public class ProductServiceImpl implements ProductService {

	@Autowired
    private ProductRepository productRepository;

	@Override
	public Product findByKey(Integer id) {
		return productRepository.findByKey(id);
	}

	@Override
	public void store(Product product) {
		productRepository.store(product);
		
	}

	@Override
	public void update(Product product) {
		Product entity = productRepository.findByKey(product.getId());
        if (entity != null) {
            entity.setSerialNumber(product.getSerialNumber());
            entity.setName(product.getName());
            entity.setPrice(product.getPrice());
            entity.setDescription(product.getDescription());
       }
            
        
		
	}

	@Override
	public List<Product> findAll() {
		return productRepository.findAll();
	}

	@Override
	public Product findBySerialNumber(String serialNumber) {
		return productRepository.findBySerialNumber(serialNumber);
	}

	@Override
	public void removeBySerialNumber(String serialNumber) {
		productRepository.removeBySerialNumber(serialNumber);
		
	}
	


}
