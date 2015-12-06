package th.in.nagi.fecs.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import th.in.nagi.fecs.model.ProductDescription;
import th.in.nagi.fecs.repository.ProductDescriptionRepository;

/**
 * Provide product service for managing easier. Ex. add, remove, edit.
 * 
 * @author Thanachote Visetsuthimont
 *
 */
@Service("productDescriptionService")
@Transactional
public class ProductDescriptionService {

	/**
	 * Tool for managing product in database.
	 */
	@Autowired
	private ProductDescriptionRepository productDescriptionRepository;

	/**
	 * Find product by using index.
	 * 
	 * @param id
	 * @return FurnitureDescription
	 */
	public ProductDescription findByKey(Integer id) {
		return productDescriptionRepository.findByKey(id);
	}

	/**
	 * Save product in database.
	 * 
	 * @param furnitureDescription
	 */
	public void store(ProductDescription furnitureDescription) {
		productDescriptionRepository.store(furnitureDescription);
	}

	/**
	 * Update product's detail in database.
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
	 * Find all products in database.
	 * 
	 * @return List<FurnitureDescription>
	 */
	public List<ProductDescription> findAll() {
		return productDescriptionRepository.findAll();
	}

	/**
	 * Find products by using serial number.
	 * 
	 * @return FurnitureDescription
	 */
	public ProductDescription findBySerialNumber(String serialNumber) {
		return productDescriptionRepository.findBySerialNumber(serialNumber);
	}

	/**
	 * Remove products by using serial number.
	 * 
	 * @param serialNumber
	 */
	public void removeBySerialNumber(String serialNumber) {
		productDescriptionRepository.removeBySerialNumber(serialNumber);

	}

	/**
	 * Find products with limit size and ascending by name.
	 * 
	 * @param start
	 * @param size
	 * @return List<FurnitureDescription>
	 */
	public List<ProductDescription> findAndAscByName(int start, int size) {
		return productDescriptionRepository.findAndAscByName(start, size);
	}

	/**
	 * Find products with limit size and descending by name.
	 * 
	 * @param start
	 * @param size
	 * @return List<FurnitureDescription>
	 */
	public List<ProductDescription> findAndDescByName(int start, int size) {
		return productDescriptionRepository.findAndDescByName(start, size);
	}

	/**
	 * Find products with limit size and ascending by price.
	 * 
	 * @param start
	 * @param size
	 * @return List<FurnitureDescription>
	 */
	public List<ProductDescription> findAndAscByPrice(int start, int size) {
		return productDescriptionRepository.findAndAscByPrice(start, size);
	}

	/**
	 * Find products with limit size and descending by price.
	 * 
	 * @param start
	 * @param size
	 * @return List<FurnitureDescription>
	 */
	public List<ProductDescription> findAndDescByPrice(int start, int size) {
		return productDescriptionRepository.findAndDescByPrice(start, size);
	}
	
	public List<ProductDescription> search(String searchName) {
		return productDescriptionRepository.search(searchName);
	}
}
