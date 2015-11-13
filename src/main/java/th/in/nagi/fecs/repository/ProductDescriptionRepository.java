package th.in.nagi.fecs.repository;

import java.util.List;

import th.in.nagi.fecs.model.ProductDescription;

/**
 * Collection of tools for managing product.
 * 
 * @author Thanachote Visetsuthimont
 *
 */
public interface ProductDescriptionRepository extends Repository<ProductDescription, Integer> {

	/**
	 * Query product by serial number.
	 * 
	 * @param serialNumber
	 * @return FurnitureDescription
	 */
	public ProductDescription findBySerialNumber(String serialNumber);

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
	public List<ProductDescription> findAndAscByName(int start, int size);

	/**
	 * Query products with limit size and descending by name.
	 * 
	 * @param start
	 * @param size
	 * @return List<FurnitureDescription>
	 */
	public List<ProductDescription> findAndDescByName(int start, int size);

	/**
	 * Query products with limit size and ascending by price.
	 * 
	 * @param start
	 * @param size
	 * @return List<FurnitureDescription>
	 */
	public List<ProductDescription> findAndAscByPrice(int start, int size);

	/**
	 * Query products with limit size and descending by price.
	 * 
	 * @param start
	 * @param size
	 * @return List<FurnitureDescription>
	 */
	public List<ProductDescription> findAndDescByPrice(int start, int size);

}
