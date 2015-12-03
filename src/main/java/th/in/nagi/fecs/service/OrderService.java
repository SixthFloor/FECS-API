package th.in.nagi.fecs.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import th.in.nagi.fecs.model.Order;
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

	@Autowired
	private OrderRepository orderRepository;

	public Order findByKey(Integer id) {
		return orderRepository.findByKey(id);
	}

	public void store(Order order) {
		orderRepository.store(order);
	}

	/**
	 * {@inheritDoc}
	 */
	public List<Order> findAll() {
		return orderRepository.findAll();
	}

	public Order findByOrderNumber(Integer orderNumber) {
		return orderRepository.findByOrderNumber(orderNumber);
	}
}
