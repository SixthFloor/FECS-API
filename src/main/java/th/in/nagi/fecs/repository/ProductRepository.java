package th.in.nagi.fecs.repository;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import th.in.nagi.fecs.model.Product;
import th.in.nagi.fecs.model.ProductDescription;

/**
 * Implemented product repository
 * 
 * @author Chonnipa Kittisiriprasert
 *
 */
@Repository("productRepository")
public class ProductRepository extends AbstractRepository<Product, Integer> {

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Product> findAll() {
		Criteria criteria = createEntityCriteria();
		return criteria.list();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Product findByKey(Integer key) {
		return getByKey(key);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void store(Product product) {
		persist(product);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void remove(Integer key) {
		remove(key);
	}

	/**
	 * Find product by using product number.
	 * 
	 * @param productNumber
	 * @return Product
	 */
	public Product findByProductNumber(String productNumber) {
		Criteria criteria = createEntityCriteria();
		criteria.add(Restrictions.eq("productNumber", productNumber));
		return (Product) criteria.uniqueResult();
	}

	/**
	 * Find available products by product description.
	 * 
	 * @param productDescription
	 * @return List<Product>
	 */
	public List<Product> findAvailableByProductDescription(ProductDescription productDescription) {
		Criteria criteria = createEntityCriteria().add(Restrictions.isNull("cart"))
				.add(Restrictions.eq("productDescription", productDescription)).add(Restrictions.eq("status", 0));
		return criteria.list();
	}
}
