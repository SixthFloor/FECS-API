package th.in.nagi.fecs.repository;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import th.in.nagi.fecs.model.Order;

/**
 * Implemented order repository
 * 
 * @author Chonnipa Kittisiriprasert
 *
 */
@Repository("orderRepository")
public class OrderRepository extends AbstractRepository<Order, Integer> {

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Order> findAll() {
		Criteria criteria = createEntityCriteria();
		return criteria.list();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Order findByKey(Integer key) {
		return getByKey(key);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void store(Order order) {
		persist(order);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void remove(Integer key) {
		remove(key);
	}

	public Order findByOrderNumber(Integer orderNumber) {
		Criteria criteria = createEntityCriteria();
		criteria.add(Restrictions.eq("order_number", orderNumber));
		return (Order) criteria.uniqueResult();
	}
}
