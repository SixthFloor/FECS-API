package th.in.nagi.fecs.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.SocketTimeoutException;
import java.util.Scanner;

import org.apache.commons.httpclient.ConnectTimeoutException;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.JSONException;
import org.json.JSONObject;
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

import th.in.nagi.fecs.message.Message;
import th.in.nagi.fecs.model.Order;
import th.in.nagi.fecs.model.Shipping;
import th.in.nagi.fecs.model.WebPayment;
import th.in.nagi.fecs.service.AuthenticationService;
import th.in.nagi.fecs.service.OrderService;
import th.in.nagi.fecs.service.ShippingService;

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
		if (!authenticationService.checkPermission(token, authenticationService.STAFF, authenticationService.MANAGER,
				authenticationService.OWNER)) {
			return new ResponseEntity<Message>(new Message("This payment does not allow"), HttpStatus.FORBIDDEN);
		}

		Order order = orderService.findByKey(orderNumber);
		if (order == null) {
			return new ResponseEntity<Message>(new Message("Invalid order number [" + orderNumber + "]"),
					HttpStatus.BAD_REQUEST);
		}

		webPayment.setPrice(orderService.getTotalPrice(orderNumber));

		return new ResponseEntity<WebPayment>(webPayment, HttpStatus.OK);
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

		CloseableHttpClient httpClient = HttpClientBuilder.create().build();
		String bank = "bank1";
		String result = "";

		try {
			HttpPost request = new HttpPost("http://50.112.182.228:3000/" + bank + "/pay");
			StringEntity params = new StringEntity("details={" + "\"card\": {" + "\"no\":\""
					+ webPayment.getWebCreditCard().getNumber() + "\"," + "\"exp_date\":\""
					+ webPayment.getWebCreditCard().getExpirationDate() + "\"}," + "\"cvv\":\"" + webPayment.getCvv()
					+ "\"," + "\"price\":\"" + webPayment.getPrice() + "\"}");
			request.addHeader("content-type", "application/x-www-form-urlencoded");
			request.setEntity(params);
			HttpResponse response = httpClient.execute(request);

			JSONObject json = new JSONObject(response);
			result = json.getString("result");

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (SocketTimeoutException e) {
			e.printStackTrace();
		} catch (ConnectTimeoutException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		//		if (result.equals("success")) {
		Shipping slot;

		try {
			slot = shippingService.findByKey(webPayment.getShipping().getId());
			slot.setStatus(Shipping.RESERVED);
			shippingService.update(slot);

			order.setStatus(Order.PAID);
			order.setShipping(slot);
			orderService.update(order);
		} catch (Exception e) {
			return new ResponseEntity<Message>(new Message("Invalid shipping information"), HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<Boolean>(true, HttpStatus.OK);
		//		}

		//		return new ResponseEntity<Message>(new Message("Invalid card information"), HttpStatus.BAD_REQUEST);
	}
}
