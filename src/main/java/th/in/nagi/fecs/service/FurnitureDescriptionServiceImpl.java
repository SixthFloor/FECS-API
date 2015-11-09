package th.in.nagi.fecs.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import th.in.nagi.fecs.model.FurnitureDescription;
import th.in.nagi.fecs.repository.FurnitureDescriptionRepository;

/**
 * Provide product service for managing easier. 
 * Ex. add, remove, edit.
 * 
 * @author Thanachote Visetsuthimont
 *
 */
@Service("furnitureDescriptionService")
@Transactional
public class FurnitureDescriptionServiceImpl implements FurnitureDescriptionService {

	/**
	 * Tool for managing product in database.
	 */
	@Autowired
	private FurnitureDescriptionRepository furnitureDescriptionRepository;

	/**
	 * Find product by using index.
	 * 
	 * @param id
	 * @return FurnitureDescription
	 */
	@Override
	public FurnitureDescription findByKey(Integer id) {
		return furnitureDescriptionRepository.findByKey(id);
	}

	/**
	 * Save product in database.
	 * 
	 * @param furnitureDescription
	 */
	@Override
	public void store(FurnitureDescription furnitureDescription) {
		furnitureDescriptionRepository.store(furnitureDescription);
	}

	/**
	 * Update product's detail in database.
	 */
	@Override
	public void update(FurnitureDescription furnitureDescription) {
		FurnitureDescription entity = furnitureDescriptionRepository.findByKey(furnitureDescription.getId());
		if (entity != null) {
			entity.setSerialNumber(furnitureDescription.getSerialNumber());
			entity.setName(furnitureDescription.getName());
			entity.setPrice(furnitureDescription.getPrice());
			entity.setDescription(furnitureDescription.getDescription());
			entity.setDimensionDescription(furnitureDescription.getDimensionDescription());
			entity.setImages(furnitureDescription.getImages());
			entity.setSubCategory(furnitureDescription.getSubCategory());
		}
	}

	/**
	 * Find all products in database.
	 * 
	 * @return List<FurnitureDescription>
	 */
	@Override
	public List<FurnitureDescription> findAll() {
		return furnitureDescriptionRepository.findAll();
	}

	/**
	 * Find products by using serial number.
	 * 
	 * @return FurnitureDescription
	 */
	@Override
	public FurnitureDescription findBySerialNumber(String serialNumber) {
		return furnitureDescriptionRepository.findBySerialNumber(serialNumber);
	}

	/**
	 * Remove products by using serial number.
	 * 
	 * @param serialNumber
	 */
	@Override
	public void removeBySerialNumber(String serialNumber) {
		furnitureDescriptionRepository.removeBySerialNumber(serialNumber);

	}

	/**
	 * Find products with limit size and ascending by name.
	 * 
	 * @param start
	 * @param size
	 * @return List<FurnitureDescription>
	 */
	@Override
	public List<FurnitureDescription> findAndAscByName(int start, int size) {
		return furnitureDescriptionRepository.findAndAscByName(start, size);
	}

	/**
	 * Find products with limit size and descending by name.
	 * 
	 * @param start
	 * @param size
	 * @return List<FurnitureDescription>
	 */
	@Override
	public List<FurnitureDescription> findAndDescByName(int start, int size) {
		return furnitureDescriptionRepository.findAndDescByName(start, size);
	}

	/**
	 * Find products with limit size and ascending by price.
	 * 
	 * @param start
	 * @param size
	 * @return List<FurnitureDescription>
	 */
	@Override
	public List<FurnitureDescription> findAndAscByPrice(int start, int size) {
		return furnitureDescriptionRepository.findAndAscByPrice(start, size);
	}

	/**
	 * Find products with limit size and descending by price.
	 * 
	 * @param start
	 * @param size
	 * @return List<FurnitureDescription>
	 */
	@Override
	public List<FurnitureDescription> findAndDescByPrice(int start, int size) {
		return furnitureDescriptionRepository.findAndDescByPrice(start, size);
	}
}
