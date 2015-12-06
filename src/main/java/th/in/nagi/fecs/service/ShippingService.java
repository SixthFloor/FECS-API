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

	@Autowired
	private ShippingRepository shippingRepository;

	public Shipping findByKey(Integer id) {
		return shippingRepository.findByKey(id);
	}

	public void store(Shipping shipping) {
		shippingRepository.store(shipping);
	}
		
	public List<Shipping> findAllAvailable() {
		return shippingRepository.findAllAvailable();
	}
}
