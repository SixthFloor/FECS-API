package th.in.nagi.fecs.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import th.in.nagi.fecs.model.Order;
import th.in.nagi.fecs.model.Shipping;
import th.in.nagi.fecs.repository.OrderRepository;
import th.in.nagi.fecs.repository.ShippingRepository;

/**
 * 
 * Shipping Service
 * 
 * @author Chonnipa Kittisiriprasert
 *
 */
@Service("shippingService")
@Transactional
public class ShippingService {

	/**
	 * Tool for managing Shipping in database.
	 */
	@Autowired
	private ShippingRepository shippingRepository;

	@Autowired
	private OrderRepository orderRepository;
	
	/**
	 * Find Shipping by using id.
	 * 
	 * @param id
	 *            shipping's id
	 * @return Shipping
	 */
	public Shipping findByKey(Integer id) {
		return shippingRepository.findByKey(id);
	}

	/**
	 * Save Shipping in database.
	 * 
	 * @param shipping
	 */
	public void store(Shipping shipping) {
		shippingRepository.store(shipping);
	}

	/**
	 * Get list of Shippings that they are available and unique.
	 * 
	 * @return List<Shipping>
	 */
	public List<Shipping> findAllUniqueAvailable() {
		return shippingRepository.findAllUniqueAvailable();
	}

	/**
	 * Update Shipping's detail in database.
	 * 
	 * @param shipping
	 *            old shipping
	 */
	public void update(Shipping shipping) {
		Shipping s = shippingRepository.findByKey(shipping.getId());
		if (s != null) {
			s.setStatus(shipping.getStatus());
		}
	}

	/**
	 * Find shipment that is ready for shipping by year and month
	 * 
	 * @param year
	 * @param month
	 * 
	 * @return List<Shipping>
	 */
	public List<Shipping> findByDate(Integer year, Integer month) {
		return shippingRepository.findByDate(year, month);
	}

	public boolean updateToProgress(Integer id) {
		Shipping s = shippingRepository.findByKey(id);
		Order o = orderRepository.findByShipping(s);
		
		if (s != null && o != null) {
			s.setStatus(Shipping.INPROGRESS);
			o.setStatus(Order.SHIPPING);
			return true;
		}

		return false;
	}

	public boolean updateToDone(Integer id) {
		Shipping s = shippingRepository.findByKey(id);
		Order o = orderRepository.findByShipping(s);

		if (s != null && o != null) {
			s.setStatus(Shipping.DONE);
			o.setStatus(Order.COMPLETED);
			return true;
		}

		return false;
	}
}
