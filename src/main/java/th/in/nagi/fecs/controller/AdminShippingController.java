package th.in.nagi.fecs.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.annotation.JsonView;

import th.in.nagi.fecs.message.Message;
import th.in.nagi.fecs.model.Shipping;
import th.in.nagi.fecs.service.AuthenticationService;
import th.in.nagi.fecs.service.ShippingService;
import th.in.nagi.fecs.view.ShippingView;

/**
 * Controller for checking out cart, select shipping date and payment.
 * 
 * @author Chonnipa Kittisiriprasert
 *
 */
@Controller
@RequestMapping("/api/admin/shipping")
public class AdminShippingController extends BaseController {

	/**
	 * authenticate service.
	 */
	@Autowired
	private AuthenticationService authenticationService;

	/**
	 * Shipping service.
	 */
	@Autowired
	private ShippingService shippingService;

	/**
	 * Creates new shipping.
	 * 
	 * @param web
	 *            shipping model
	 * @return shipping number
	 */
	@ResponseBody
	@RequestMapping(value = {"/new"}, method = RequestMethod.POST)
	public ResponseEntity<?> createShipping(@RequestHeader(value = "Authorization") String token,
			@RequestBody Shipping shipping) {
		if (!authenticationService.checkPermission(token, authenticationService.STAFF, authenticationService.MANAGER,
				authenticationService.OWNER)) {
			return new ResponseEntity<Message>(new Message("This user does not allow"), HttpStatus.FORBIDDEN);
		}

		shipping.resetId();

		try {
			shippingService.store(shipping);
		} catch (Exception e) {
			return new ResponseEntity<Message>(new Message("Create shipping slot failed"), HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<Message>(new Message("The shipping slot has created"), HttpStatus.CREATED);
	}

	/**
	 * Lists all existing shipment that is ready for shipping.
	 * 
	 * @return list of ready shippings
	 */
	@JsonView(ShippingView.All.class)
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ResponseEntity<?> getShippingsByDateMonthYear(@RequestHeader(value = "Authorization") String token,
			@RequestParam(required = true, value = "year") Integer year,
			@RequestParam(required = true, value = "month") Integer month) {
		
		if (!authenticationService.checkPermission(token, authenticationService.STAFF, authenticationService.MANAGER,
				authenticationService.OWNER)) {
			return new ResponseEntity<Message>(new Message("This user does not allow"), HttpStatus.FORBIDDEN);
		}
		
		if (year == null || month == null) {
			return new ResponseEntity<Message>(new Message("Invalid year or month"), HttpStatus.BAD_REQUEST);
		}

		List<Shipping> shippings = shippingService.findByDate(year, month);
		
		if (shippings != null) {
			return new ResponseEntity<List<Shipping>>(shippings, HttpStatus.OK);
		}

		return new ResponseEntity<Message>(new Message("Invalid"), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/progress", method = RequestMethod.POST)
	public ResponseEntity<?> progressShipment(@RequestHeader(value = "Authorization") String token,
			@RequestParam(required = true, value = "id") Integer id) {
		
		if (!authenticationService.checkPermission(token, authenticationService.STAFF, authenticationService.MANAGER,
				authenticationService.OWNER)) {
			return new ResponseEntity<Message>(new Message("This user does not allow"), HttpStatus.FORBIDDEN);
		}
		
		boolean result = shippingService.updateToProgress(id);
		
		if (result) {
			return new ResponseEntity<Boolean>(true, HttpStatus.OK);
		}
		
		return new ResponseEntity<Message>(new Message("Invalid shipment id"), HttpStatus.BAD_REQUEST);		
	}
	
	@RequestMapping(value = "/done", method = RequestMethod.POST)
	public ResponseEntity<?> doneShipment(@RequestHeader(value = "Authorization") String token,
			@RequestParam(required = true, value = "id") Integer id) {
		
		if (!authenticationService.checkPermission(token, authenticationService.STAFF, authenticationService.MANAGER,
				authenticationService.OWNER)) {
			return new ResponseEntity<Message>(new Message("This user does not allow"), HttpStatus.FORBIDDEN);
		}
		
		boolean result = shippingService.updateToProgress(id);
		
		if (result) {
			return new ResponseEntity<Boolean>(true, HttpStatus.OK);
		}
		
		return new ResponseEntity<Message>(new Message("Invalid shipment id"), HttpStatus.BAD_REQUEST);
	}
}
