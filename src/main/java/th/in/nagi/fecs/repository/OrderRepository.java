package th.in.nagi.fecs.repository;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import th.in.nagi.fecs.model.Order;
import th.in.nagi.fecs.model.User;

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

	/**
	 * Finds order by order number.
	 * 
	 * @param orderNumber
	 *            order number
	 * @return order
	 */
	public Order findByOrderNumber(Integer orderNumber) {
		Criteria criteria = createEntityCriteria();
		criteria.add(Restrictions.eq("order_number", orderNumber));
		return (Order) criteria.uniqueResult();
	}

	/**
	 * Finds orders by user.
	 * 
	 * @param user
	 *            user
	 * @return list of orders
	 */
	public List<Order> findByUser(User user) {
		Criteria criteria = createEntityCriteria();
		criteria.add(Restrictions.eq("user", user));
		return criteria.list();
	}
}
