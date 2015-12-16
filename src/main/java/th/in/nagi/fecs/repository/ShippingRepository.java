package th.in.nagi.fecs.repository;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.stereotype.Repository;

import th.in.nagi.fecs.model.Shipping;

/**
 * Implemented shipping repository
 * 
 * @author Chonnipa Kittisiriprasert
 *
 */
@Repository("shippingRepository")
public class ShippingRepository extends AbstractRepository<Shipping, Integer> {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Shipping findByKey(Integer key) {
		return getByKey(key);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void store(Shipping shipping) {
		persist(shipping);
	}
	
	/**
	 * Get list of Shippings that they are available and unique.
	 * 
	 * @return List<Shipping>
	 */
	@SuppressWarnings("unchecked")
	public List<Shipping> findAllUniqueAvailable() {
		Criteria criteria = createEntityCriteria();
		criteria.add(Restrictions.eq("status", Shipping.AVAILABLE));
		return criteria.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<Shipping> findByDate(int year, int month) {
		Criteria criteria = createEntityCriteria();
		criteria.add(Restrictions.gt("status", Shipping.AVAILABLE));
		criteria.add(Restrictions.sqlRestriction("MONTH(date) = ?", month, StandardBasicTypes.INTEGER));
		criteria.add(Restrictions.sqlRestriction("YEAR(date) = ?", year, StandardBasicTypes.INTEGER));
		return criteria.list();
	}
}
