package th.in.nagi.fecs.controller;

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
import th.in.nagi.fecs.model.Order;
import th.in.nagi.fecs.model.Shipping;
import th.in.nagi.fecs.model.WebPayment;
import th.in.nagi.fecs.service.AuthenticationService;
import th.in.nagi.fecs.service.OrderService;
import th.in.nagi.fecs.service.PaymentService;
import th.in.nagi.fecs.service.ShippingService;
import th.in.nagi.fecs.view.WebOrderView;
import th.in.nagi.fecs.view.WebPaymentView;

/**
 * Controller for validating credit card and make a payment.
 * 
 * @author Chonnipa Kittisiriprasert
 *
 */
@Controller
@RequestMapping("/api/payment")
public class PaymentController extends BaseController {

	/**
	 * authenticate service.
	 */
	@Autowired
	private AuthenticationService authenticationService;

	@Autowired
	private OrderService orderService;

	@Autowired
	private ShippingService shippingService;

	@Autowired
	private PaymentService paymentService;

	/**
	 * Validate credit card.
	 * 
	 * @return validation result
	 */
	@ResponseBody
	@RequestMapping(value = "/validate", method = RequestMethod.POST)
	public ResponseEntity<?> validate(@RequestHeader(value = "Authorization") String token,
			@RequestParam(value = "orderNumber", required = true) Integer orderNumber,
			@RequestBody WebPayment webPayment) {
		if (!authenticationService.checkPermission(token, authenticationService.MEMBER, authenticationService.STAFF,
				authenticationService.MANAGER, authenticationService.OWNER)) {
			return new ResponseEntity<Message>(new Message("This payment does not allow"), HttpStatus.FORBIDDEN);
		}

		Order order = orderService.findByKey(orderNumber);
		if (order == null) {
			return new ResponseEntity<Message>(new Message("Invalid order number [" + orderNumber + "]"),
					HttpStatus.BAD_REQUEST);
		}

		String result = paymentService.validate(order, webPayment);

		return (result.equals("ready")) ? new ResponseEntity<Boolean>(true, HttpStatus.OK)
				: new ResponseEntity<Message>(new Message(result), HttpStatus.BAD_REQUEST);
	}

	/**
	 * Make a payment.
	 * 
	 * @return payment result
	 */
	@ResponseBody
	@RequestMapping(value = "/pay", method = RequestMethod.POST)
	public ResponseEntity<?> pay(@RequestHeader(value = "Authorization") String token,
			@RequestParam(value = "orderNumber", required = true) Integer orderNumber,
			@RequestBody WebPayment webPayment) {
		if (!authenticationService.checkPermission(token, authenticationService.MEMBER, authenticationService.STAFF,
				authenticationService.MANAGER, authenticationService.OWNER)) {
			return new ResponseEntity<Message>(new Message("This payment does not allow"), HttpStatus.FORBIDDEN);
		}

		Order order = orderService.findByKey(orderNumber);
		if (order == null) {
			return new ResponseEntity<Message>(new Message("Invalid order number [" + orderNumber + "]"),
					HttpStatus.BAD_REQUEST);
		}

		String result = paymentService.pay(order, webPayment);

		if (result.equals("success")) {

			return new ResponseEntity<Boolean>(true, HttpStatus.OK);
		}

		return new ResponseEntity<Message>(new Message(result), HttpStatus.BAD_REQUEST);
	}
	
	@JsonView(WebPaymentView.Personal.class)
	@ResponseBody
	@RequestMapping(value = "/test", method = RequestMethod.POST)
	public ResponseEntity<?> test(
			@RequestBody WebPayment webPayment) {
		
		webPayment.setPrice(8190000.0);
		
		return new ResponseEntity<WebPayment>(webPayment, HttpStatus.OK);
	}
}
