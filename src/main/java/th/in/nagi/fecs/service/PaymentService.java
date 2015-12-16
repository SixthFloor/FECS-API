package th.in.nagi.fecs.service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.SocketTimeoutException;

import org.apache.commons.httpclient.ConnectTimeoutException;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import th.in.nagi.fecs.message.Message;
import th.in.nagi.fecs.model.Address;
import th.in.nagi.fecs.model.Order;
import th.in.nagi.fecs.model.Shipping;
import th.in.nagi.fecs.model.WebPayment;
import th.in.nagi.fecs.repository.AddressRepository;
import th.in.nagi.fecs.repository.OrderRepository;
import th.in.nagi.fecs.repository.ShippingRepository;

/**
 * 
 * Payment Service
 * 
 * @author Chonnipa Kittisiriprasert
 *
 */
@Service("paymentService")
@Transactional
public class PaymentService {
	
	private static final String BANK_URL = "http://50.112.182.228:3000/";
	private static final String OWNER_ACCOUNT_BANK = "bank1";
	private static final String OWNER_ACCOUNT = "54260012";
	
	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	ShippingRepository shippingRepository;
	
	@Autowired
	AddressRepository addressRepository;

	private String connect(String method, String param) {
		CloseableHttpClient httpClient = HttpClientBuilder.create().build();
		String result = "";
		JSONObject json = null;

		try {
			HttpPost request = new HttpPost(BANK_URL + OWNER_ACCOUNT_BANK + "/" + method);

			StringEntity params = new StringEntity(param);
			request.addHeader("content-type", "application/json");
			request.setEntity(params);
			HttpResponse response = httpClient.execute(request);

			json = new JSONObject(EntityUtils.toString(response.getEntity()));
			result = json.getString("result");

			return (result.equals("ready") || result.equals("success")) ? result : json.getString("message");

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
		
		return "error";
	}
	
	public String validate(Order o, WebPayment webPayment) {	
		return connect("validate", webPayment.getJSONObject(o.getTotal(), OWNER_ACCOUNT).toString());
	}
	
	public String pay(Order o, WebPayment webPayment) {
		
		String result = connect("pay", webPayment.getJSONObject(o.getTotal(), OWNER_ACCOUNT).toString());
		
		if (!result.equals("success")) {
			return result;
		}
		
		try {
			Order order = orderRepository.getByKey(o.getOrderNumber());
			Shipping slot = shippingRepository.findByKey(webPayment.getShipping().getId());

			if (slot.getStatus() != Shipping.AVAILABLE) {
				return "Shipping slot is not available";
			} else if (order.getStatus() != Order.NOTPAY) {
				return "Order is already paid";
			}

			Address address = webPayment.getShipping().getAddress();
			address.setUser(order.getUser());
			address = addressRepository.findByKey(addressRepository.save(address));
			
			slot.reserved(address);
			order.paid(slot);
		} catch (Exception e) {
			return "Invalid shipping information";
		}
		
		return "success";
	}
}