package th.in.nagi.fecs.repository;

import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
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
	
	public List<Shipping> findByStatusByDate(int status, int year, int month, int day) {
		Criteria criteria = createEntityCriteria();
		criteria.add(Restrictions.eq("status", status));
		criteria.add(Restrictions.sqlRestriction("DAY(date) = ?", day, StandardBasicTypes.INTEGER));
		criteria.add(Restrictions.sqlRestriction("MONTH(date) = ?", month, StandardBasicTypes.INTEGER));
		criteria.add(Restrictions.sqlRestriction("YEAR(date) = ?", year, StandardBasicTypes.INTEGER));
		criteria.addOrder(org.hibernate.criterion.Order.desc("date"));
		return criteria.list();
	}
	
	public List<Shipping> findByStatusByDate(int status, int year, int month) {
		Criteria criteria = createEntityCriteria();
		criteria.add(Restrictions.eq("status", status));
		criteria.add(Restrictions.sqlRestriction("MONTH(date) = ?", month, StandardBasicTypes.INTEGER));
		criteria.add(Restrictions.sqlRestriction("YEAR(date) = ?", year, StandardBasicTypes.INTEGER));
		criteria.addOrder(org.hibernate.criterion.Order.desc("date"));
		return criteria.list();
	}
	
	public List<Shipping> findByStatusByDate(int status, int year) {
		Criteria criteria = createEntityCriteria();
		criteria.add(Restrictions.eq("status", status));
		criteria.add(Restrictions.sqlRestriction("YEAR(date) = ?", year, StandardBasicTypes.INTEGER));
		criteria.addOrder(org.hibernate.criterion.Order.desc("date"));
		return criteria.list();
	}
}
