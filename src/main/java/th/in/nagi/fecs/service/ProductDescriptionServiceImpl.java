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
public class ProductDescriptionServiceImpl implements ProductDescriptionService {

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
	@Override
	public ProductDescription findByKey(Integer id) {
		return productDescriptionRepository.findByKey(id);
	}

	/**
	 * Save product in database.
	 * 
	 * @param furnitureDescription
	 */
	@Override
	public void store(ProductDescription furnitureDescription) {
		productDescriptionRepository.store(furnitureDescription);
	}

	/**
	 * Update product's detail in database.
	 */
	@Override
	public void update(ProductDescription furnitureDescription) {
		ProductDescription entity = productDescriptionRepository.findByKey(furnitureDescription.getId());
		if (entity != null) {
			entity.setSerialNumber(furnitureDescription.getSerialNumber());
			entity.setName(furnitureDescription.getName());
			entity.setPrice(furnitureDescription.getPrice());
			entity.setDescription(furnitureDescription.getDescription());
			entity.setDimensionDescription(furnitureDescription.getDimensionDescription());
			entity.setImages(furnitureDescription.getImages());
//			entity.setSubCategory(furnitureDescription.getSubCategory());
		}
	}

	/**
	 * Find all products in database.
	 * 
	 * @return List<FurnitureDescription>
	 */
	@Override
	public List<ProductDescription> findAll() {
		return productDescriptionRepository.findAll();
	}

	/**
	 * Find products by using serial number.
	 * 
	 * @return FurnitureDescription
	 */
	@Override
	public ProductDescription findBySerialNumber(String serialNumber) {
		return productDescriptionRepository.findBySerialNumber(serialNumber);
	}

	/**
	 * Remove products by using serial number.
	 * 
	 * @param serialNumber
	 */
	@Override
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
	@Override
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
	@Override
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
	@Override
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
	@Override
	public List<ProductDescription> findAndDescByPrice(int start, int size) {
		return productDescriptionRepository.findAndDescByPrice(start, size);
	}
}
