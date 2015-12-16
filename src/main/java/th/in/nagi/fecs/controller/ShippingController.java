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
@RequestMapping("/api/shipping")
public class ShippingController extends BaseController {

	/**
	 * Shipping service.
	 */
	@Autowired
	private ShippingService shippingService;

	/**
	 * authenticate service.
	 */
	@Autowired
	private AuthenticationService authenticationService;

	/**
	 * Lists all existing shippings.
	 * 
	 * @return list of shippings
	 */
	@JsonView(ShippingView.Personal.class)
	@RequestMapping(value = "/all", method = RequestMethod.GET)
	public ResponseEntity<?> getAllShippings(@RequestHeader(value = "Authorization") String token) {
		if (!authenticationService.checkPermission(token, authenticationService.MEMBER, authenticationService.STAFF,
				authenticationService.MANAGER, authenticationService.OWNER)) {
			return new ResponseEntity<Message>(new Message("This shipping does not allow"), HttpStatus.FORBIDDEN);
		}

		List<Shipping> shippings = shippingService.findAllAvailable();

		if (shippings != null && !shippings.isEmpty()) {
			return new ResponseEntity<List<Shipping>>(shippings, HttpStatus.OK);
		}

		return new ResponseEntity<Message>(new Message("None of shipping slots is available"), HttpStatus.OK);
	}

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
	 * Lists all existing shippings.
	 * 
	 * @return list of shippings
	 */
	@JsonView(ShippingView.View.class)
	@RequestMapping(value = "/view", method = RequestMethod.GET)
	public ResponseEntity<?> getAllShippingsByDateMonthYear(@RequestHeader(value = "Authorization") String token,
			@RequestParam(required = false, value = "year") Integer year,
			@RequestParam(required = false, value = "month") Integer month,
			@RequestParam(required = false, value = "date") Integer day) {
		if (!authenticationService.checkPermission(token, authenticationService.STAFF, authenticationService.MANAGER,
				authenticationService.OWNER)) {
			return new ResponseEntity<Message>(new Message("This user does not allow"), HttpStatus.FORBIDDEN);
		}

		List<Shipping> shippings = shippingService.findByStatusByDate(Shipping.INPROGRESS, year, month, day);
		if (shippings != null && !shippings.isEmpty()) {
			return new ResponseEntity<List<Shipping>>(shippings, HttpStatus.OK);
		}

		return new ResponseEntity<Message>(new Message("None of shipping slots is inprogress"), HttpStatus.OK);
	}
}
