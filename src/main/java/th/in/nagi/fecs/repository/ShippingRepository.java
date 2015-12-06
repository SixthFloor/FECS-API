package th.in.nagi.fecs.repository;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
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
	@SuppressWarnings("unchecked")
	public List<Shipping> findAllAvailable() {
		Criteria criteria = createEntityCriteria();
		criteria.add(Restrictions.eq("status", 0));
		return criteria.list();
	}

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
}
