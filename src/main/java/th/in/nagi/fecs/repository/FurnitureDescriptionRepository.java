package th.in.nagi.fecs.repository;

import java.util.List;

import th.in.nagi.fecs.model.FurnitureDescription;

/**
 * Collection of tools for managing product.
 * 
 * @author Thanachote Visetsuthimont
 *
 */
public interface FurnitureDescriptionRepository extends Repository<FurnitureDescription, Integer> {

	/**
	 * Query product by serial number.
	 * 
	 * @param serialNumber
	 * @return FurnitureDescription
	 */
	public FurnitureDescription findBySerialNumber(String serialNumber);

	/**
	 * Remove product by using serial number.
	 * 
	 * @param serialNumber
	 */
	public void removeBySerialNumber(String serialNumber);

	/**
	 * Query products with limit size and ascending by name.
	 * 
	 * @param start
	 * @param size
	 * @return List<FurnitureDescription>
	 */
	public List<FurnitureDescription> findAndAscByName(int start, int size);

	/**
	 * Query products with limit size and descending by name.
	 * 
	 * @param start
	 * @param size
	 * @return List<FurnitureDescription>
	 */
	public List<FurnitureDescription> findAndDescByName(int start, int size);

	/**
	 * Query products with limit size and ascending by price.
	 * 
	 * @param start
	 * @param size
	 * @return List<FurnitureDescription>
	 */
	public List<FurnitureDescription> findAndAscByPrice(int start, int size);

	/**
	 * Query products with limit size and descending by price.
	 * 
	 * @param start
	 * @param size
	 * @return List<FurnitureDescription>
	 */
	public List<FurnitureDescription> findAndDescByPrice(int start, int size);

}
