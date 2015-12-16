package th.in.nagi.fecs.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import th.in.nagi.fecs.model.Shipping;
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
	 * Get list of Shippings that they are available.
	 * 
	 * @return List<Shipping>
	 */
	public List<Shipping> findAllAvailable() {
		return shippingRepository.findAllAvailable();
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
	
	public List<Shipping> findByStatusByDate(Integer status, Integer year, Integer month, Integer day) {
		if(day != null & month != null & year != null & status != null){
			return shippingRepository.findByStatusByDate(status, year, month, day);
		}
		if(month != null & year != null & status != null){
			return shippingRepository.findByStatusByDate(status, year, month);
		}
		if(year != null & status != null){
			return shippingRepository.findByStatusByDate(status, year);
		}
		return null;
	}
}
