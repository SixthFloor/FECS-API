package th.in.nagi.fecs.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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
	@JsonView(ShippingView.Summary.class)
	@RequestMapping(value = "/all", method = RequestMethod.GET)
	public ResponseEntity<?> getAllShippings(@RequestHeader(value = "Authorization") String token) {
		if (!authenticationService.checkPermission(token, authenticationService.MEMBER, authenticationService.STAFF,
				authenticationService.MANAGER, authenticationService.OWNER)) {
			return new ResponseEntity<Message>(new Message("This shipping does not allow"), HttpStatus.FORBIDDEN);
		}

		List<Shipping> shippings = shippingService.findAllUniqueAvailable();

		if (shippings != null && !shippings.isEmpty()) {
			return new ResponseEntity<List<Shipping>>(shippings, HttpStatus.OK);
		}

		return new ResponseEntity<Message>(new Message("None of shipping slots is available"), HttpStatus.OK);
	}
}
