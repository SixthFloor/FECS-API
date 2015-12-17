package th.in.nagi.fecs.repository;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.stereotype.Repository;

import th.in.nagi.fecs.model.Order;
import th.in.nagi.fecs.model.ProductDescription;
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
		Criteria criteria = createEntityCriteria().addOrder(org.hibernate.criterion.Order.asc("status"));
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
		criteria.add(Restrictions.eq("orderNumber", orderNumber));
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

	/**
	 * Calculate total price of order number.
	 * 
	 * @param Integer
	 *            orderNumber
	 * @return Double total price
	 */
	public Double getTotalPrice(Integer orderNumber) {
		Criteria criteria = createEntityCriteria().createAlias("cart", "cart", JoinType.INNER_JOIN)
				.createAlias("cart.products", "product", JoinType.INNER_JOIN)
				.createAlias("product.productDescription", "product_description", JoinType.INNER_JOIN)
				.add(Restrictions.eq("orderNumber", orderNumber)).setProjection(Projections.property("price"))
				.setProjection(Projections.sum("product_description.price"));

		return ((Number) criteria.uniqueResult()).doubleValue();
	}
}
