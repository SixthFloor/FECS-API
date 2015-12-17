package th.in.nagi.fecs.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import th.in.nagi.fecs.model.ProductDescription;
import th.in.nagi.fecs.model.ProductImage;
import th.in.nagi.fecs.repository.ProductDescriptionRepository;
import th.in.nagi.fecs.repository.ProductImageRepository;

/**
 * Provide product image service for managing easier. Ex. add, remove,
 * edit.
 * 
 * @author Thanachote Visetsuthimont
 *
 */
@Service("productImageService")
@Transactional
public class ProductImageService {

	/**
	 * Tool for managing product image in database.
	 */
	@Autowired
	private ProductImageRepository productImageRepository;

	/**
	 * Find product image by using id.
	 * 
	 * @param id
	 * @return ProductImage
	 */
	public ProductImage findByKey(Integer id) {
		return productImageRepository.findByKey(id);
	}

	/**
	 * Save product image in database.
	 * 
	 * @param productImage
	 *            new productImage
	 */
	public void store(ProductImage productImage) {
		productImageRepository.store(productImage);
	}

}
