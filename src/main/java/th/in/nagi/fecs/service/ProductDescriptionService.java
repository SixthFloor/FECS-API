package th.in.nagi.fecs.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import th.in.nagi.fecs.model.ProductDescription;
import th.in.nagi.fecs.repository.ProductDescriptionRepository;

/**
 * Provide product description service for managing easier. Ex. add, remove,
 * edit.
 * 
 * @author Thanachote Visetsuthimont
 *
 */
@Service("productDescriptionService")
@Transactional
public class ProductDescriptionService {

	/**
	 * Tool for managing product description in database.
	 */
	@Autowired
	private ProductDescriptionRepository productDescriptionRepository;

	/**
	 * Find product description by using id.
	 * 
	 * @param id
	 * @return ProductDescription
	 */
	public ProductDescription findByKey(Integer id) {
		return productDescriptionRepository.findByKey(id);
	}

	/**
	 * Save product description in database.
	 * 
	 * @param productDescription
	 *            new product description
	 */
	public void store(ProductDescription productDescription) {
		productDescriptionRepository.store(productDescription);
	}

	/**
	 * Update product description's detail in database.
	 * 
	 * @param productDescription
	 */
	public void update(ProductDescription productDescription) {
		ProductDescription entity = productDescriptionRepository.findByKey(productDescription.getId());
		if (entity != null) {
			if (productDescription.getSerialNumber() != null) {
				entity.setSerialNumber(productDescription.getSerialNumber());
			}
			if (productDescription.getName() != null) {
				entity.setName(productDescription.getName());
			}
			if (productDescription.getPrice() >= 0) {
				entity.setPrice(productDescription.getPrice());
			}
			if (productDescription.getDescription() != null) {
				entity.setDescription(productDescription.getDescription());
			}
			if (productDescription.getDimensionDescription() != null) {
				entity.setDimensionDescription(productDescription.getDimensionDescription());
			}
			if (productDescription.getImages() != null) {
				entity.setImages(productDescription.getImages());
			}
		}
	}

	/**
	 * Find all product descriptions in database.
	 * 
	 * @return List<ProductDescription>
	 */
	public List<ProductDescription> findAll() {
		return productDescriptionRepository.findAll();
	}

	/**
	 * Find product descriptions by using serial number.
	 * 
	 * @return ProductDescription
	 */
	public ProductDescription findBySerialNumber(String serialNumber) {
		return productDescriptionRepository.findBySerialNumber(serialNumber);
	}

	/**
	 * Remove product descriptions by using serial number.
	 * 
	 * @param serialNumber
	 */
	public void removeBySerialNumber(String serialNumber) {
		productDescriptionRepository.removeBySerialNumber(serialNumber);

	}

	/**
	 * Find product descriptions with limit size and ascending by name.
	 * 
	 * @param start
	 * @param size
	 * @return List<ProductDescription>
	 */
	public List<ProductDescription> findAndAscByName(int start, int size) {
		return productDescriptionRepository.findAndAscByName(start, size);
	}

	/**
	 * Find product descriptions with limit size and descending by name.
	 * 
	 * @param start
	 * @param size
	 * @return List<ProductDescription>
	 */
	public List<ProductDescription> findAndDescByName(int start, int size) {
		return productDescriptionRepository.findAndDescByName(start, size);
	}

	/**
	 * Find product descriptions with limit size and ascending by price.
	 * 
	 * @param start
	 * @param size
	 * @return List<ProductDescription>
	 */
	public List<ProductDescription> findAndAscByPrice(int start, int size) {
		return productDescriptionRepository.findAndAscByPrice(start, size);
	}

	/**
	 * Find product descriptions with limit size and descending by price.
	 * 
	 * @param start
	 * @param size
	 * @return List<ProductDescription>
	 */
	public List<ProductDescription> findAndDescByPrice(int start, int size) {
		return productDescriptionRepository.findAndDescByPrice(start, size);
	}

	/**
	 * Search product descriptions by name and serial number.
	 * 
	 * @param searchName
	 * @return List<ProductDescription>
	 */
	public List<ProductDescription> search(String searchName) {
		return productDescriptionRepository.search(searchName);
	}
}
