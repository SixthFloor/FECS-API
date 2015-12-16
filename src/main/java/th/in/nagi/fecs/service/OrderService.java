package th.in.nagi.fecs.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import th.in.nagi.fecs.model.Order;
import th.in.nagi.fecs.model.User;
import th.in.nagi.fecs.repository.OrderRepository;

/**
 * 
 * Order Service
 * 
 * @author Chonnipa Kittisiriprasert
 *
 */
@Service("orderService")
@Transactional
public class OrderService {

	/**
	 * Tool for managing Order in database.
	 */
	@Autowired
	private OrderRepository orderRepository;

	/**
	 * Find product by using id.
	 * 
	 * @param id
	 * @return FurnitureDescription
	 */
	public Order findByKey(Integer id) {
		return orderRepository.findByKey(id);
	}

	/**
	 * Save Order in database.
	 * 
	 * @param order
	 *            new order
	 */
	public void store(Order order) {
		orderRepository.store(order);
	}

	/**
	 * Save Order in database.
	 * 
	 * @param order
	 *            new order
	 * @return Integer order's id
	 */
	public Integer save(Order order) {
		return orderRepository.save(order);
	}

	/**
	 * Update Order's detail in database.
	 * 
	 * @param order
	 */
	public void update(Order order) {
		Order o = orderRepository.findByKey(order.getOrderNumber());
		if (o != null) {
			if (order.getCart() != null) {
				o.setCart(order.getCart());
			}
			o.setStatus(order.getStatus());
			if (order.getShipping() != null) {
				o.setShipping(order.getShipping());
			}
		}
	}

	/**
	 * Find all Orders in database.
	 * 
	 * @return List<Order>
	 */
	public List<Order> findAll() {
		return orderRepository.findAll();
	}

	/**
	 * Find Order by using order number.
	 * 
	 * @return Order
	 */
	public Order findByOrderNumber(Integer orderNumber) {
		return orderRepository.findByOrderNumber(orderNumber);
	}

	/**
	 * Find Order by using User.
	 * 
	 * @return List<Order>
	 */
	public List<Order> findByUser(User user) {
		return orderRepository.findByUser(user);
	}

	/**
	 * Get total price of order number.
	 * 
	 * @return Double
	 */
	public Double getTotalPrice(Integer orderNumber) {
		return orderRepository.getTotalPrice(orderNumber);
	}
}
