package th.in.nagi.fecs.repository;

import java.util.List;

import th.in.nagi.fecs.model.Product;

/**
 * Collection of tools for managing product.
 * 
 * @author Thanachote Visetsuthimont
 *
 */
public interface ProductRepository extends Repository<Product, Integer> {

	/**
	 * Query product by serial number.
	 * 
	 * @param serialNumber
	 * @return Product
	 */
	public Product findBySerialNumber(String serialNumber);

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
	 * @return List<Product>
	 */
	public List<Product> findAndAscByName(int start, int size);

	/**
	 * Query products with limit size and descending by name.
	 * 
	 * @param start
	 * @param size
	 * @return List<Product>
	 */
	public List<Product> findAndDescByName(int start, int size);

	/**
	 * Query products with limit size and ascending by price.
	 * 
	 * @param start
	 * @param size
	 * @return List<Product>
	 */
	public List<Product> findAndAscByPrice(int start, int size);

	/**
	 * Query products with limit size and descending by price.
	 * 
	 * @param start
	 * @param size
	 * @return List<Product>
	 */
	public List<Product> findAndDescByPrice(int start, int size);

}
