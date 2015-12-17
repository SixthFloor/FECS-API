package th.in.nagi.fecs.repository;

import org.springframework.stereotype.Repository;

import th.in.nagi.fecs.model.ProductImage;
import th.in.nagi.fecs.model.User;

/**
 * Implemented product image repository
 * 
 * @author Thanachote Visetsuthimont
 *
 */
@Repository("productImageRepository")
public class ProductImageRepository extends AbstractRepository<ProductImage, Integer> {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ProductImage findByKey(Integer key) {
		return getByKey(key);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void store(ProductImage productImage) {
		persist(productImage);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void remove(Integer key) {
		remove(key);

	}
}
