package th.in.nagi.fecs.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.annotation.JsonView;

import th.in.nagi.fecs.message.Message;
import th.in.nagi.fecs.model.Cart;
import th.in.nagi.fecs.model.Order;
import th.in.nagi.fecs.model.Product;
import th.in.nagi.fecs.model.User;
import th.in.nagi.fecs.model.WebLineItem;
import th.in.nagi.fecs.model.WebOrder;
import th.in.nagi.fecs.service.AuthenticationService;
import th.in.nagi.fecs.service.CartService;
import th.in.nagi.fecs.service.OrderService;
import th.in.nagi.fecs.service.ProductService;
import th.in.nagi.fecs.service.UserService;
import th.in.nagi.fecs.view.OrderView;
import th.in.nagi.fecs.view.WebOrderView;

/**
 * Controller for checking out cart, select shipping date and payment.
 * 
 * @author Chonnipa Kittisiriprasert
 *
 */
@Controller
@RequestMapping("/api/order")
public class OrderController extends BaseController {

	/**
	 * Order service.
	 */
	@Autowired
	private OrderService orderService;

	/**
	 * authenticate service.
	 */
	@Autowired
	private AuthenticationService authenticationService;

	@Autowired
	private UserService userService;

	@Autowired
	private CartService cartService;

	@Autowired
	private ProductService productService;

	/**
	 * Lists all existing orders.
	 * 
	 * @return list of orders
	 */
	@JsonView(WebOrderView.Personal.class)
	@RequestMapping(value = "/all", method = RequestMethod.GET)
	public ResponseEntity<?> getAllOrders(@RequestHeader(value = "Authorization") String token) {
		if (!authenticationService.checkPermission(token, authenticationService.STAFF, authenticationService.MANAGER,
				authenticationService.OWNER)) {
			return new ResponseEntity<Message>(new Message("This user does not allow"), HttpStatus.FORBIDDEN);
		}

		List<WebOrder> webOrders = new ArrayList<WebOrder>();
		for (Order order : orderService.findAll()) {
			webOrders.add(WebOrder.create(order));
		}

		if (!webOrders.isEmpty()) {
			return new ResponseEntity<List<WebOrder>>(webOrders, HttpStatus.OK);
		}

		return new ResponseEntity<Message>(new Message("Not found order"), HttpStatus.BAD_REQUEST);
	}

	public boolean checkPermission(String token, User owner) {
		Integer userId = authenticationService.findByToken(token).getUser().getId();

		boolean member = authenticationService.checkPermission(token, authenticationService.MEMBER)
				&& Integer.valueOf(userId).equals(Integer.valueOf(owner.getId()));
		boolean staff = authenticationService.checkPermission(token, authenticationService.STAFF,
				authenticationService.MANAGER, authenticationService.OWNER);
		
		return member || staff;
	}

	/**
	 * Get order by order number.
	 * 
	 * @return order
	 */
	@JsonView(WebOrderView.Personal.class)
	@RequestMapping(value = "/{orderNumber}", method = RequestMethod.GET)
	public ResponseEntity<?> getOrder(@RequestHeader(value = "Authorization") String token,
			@PathVariable Integer orderNumber) {

		Order order = orderService.findByKey(orderNumber);
		
		if (order == null) {
			return new ResponseEntity<Message>(new Message("Not found order"), HttpStatus.BAD_REQUEST);
		} else if (!checkPermission(token, order.getUser())) {
			return new ResponseEntity<Message>(new Message("This user does not allow"), HttpStatus.FORBIDDEN);
		}

		WebOrder webOrder = WebOrder.create(order);
		return new ResponseEntity<WebOrder>(webOrder, HttpStatus.OK);
	}

	/**
	 * Get orders by user id.
	 * 
	 * @return list of orders
	 */
	@JsonView(WebOrderView.Personal.class)
	@RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> getOrderByEmail(@RequestHeader(value = "Authorization") String token,
			@PathVariable Integer id) {

		User user = userService.findByKey(id);

		if (user == null) {
			return new ResponseEntity<Message>(new Message("User [" + id + "] not found"), HttpStatus.BAD_REQUEST);
		} else if (!checkPermission(token, user)) {
			return new ResponseEntity<Message>(new Message("This user does not allow"), HttpStatus.FORBIDDEN);
		}
		
		List<Order> orders = orderService.findByUser(user);
		List<WebOrder> webOrders = new ArrayList<WebOrder>();

		for (Order order : orders) {
			webOrders.add(WebOrder.create(order));
		}

		return new ResponseEntity<List<WebOrder>>(webOrders, HttpStatus.OK);
	}

	/**
	 * Get orders by user id.
	 * 
	 * @return list of orders
	 */
	@JsonView(WebOrderView.Personal.class)
	@RequestMapping(value = "/email/{email:.+}", method = RequestMethod.GET)
	public ResponseEntity<?> getOrderByEmail(@RequestHeader(value = "Authorization") String token,
			@PathVariable String email) {

		User user = userService.findByEmail(email);

		if (user == null) {
			return new ResponseEntity<Message>(new Message("User [" + email + "] not found"), HttpStatus.BAD_REQUEST);
		} else if (!checkPermission(token, user)) {
			return new ResponseEntity<Message>(new Message("This user does not allow"), HttpStatus.FORBIDDEN);
		}

		List<Order> orders = orderService.findByUser(user);
		List<WebOrder> webOrders = new ArrayList<WebOrder>();

		for (Order order : orders) {
			webOrders.add(WebOrder.create(order));
		}

		return new ResponseEntity<List<WebOrder>>(webOrders, HttpStatus.OK);
	}

	/**
	 * Creates new order.
	 * 
	 * @param web
	 *            order model
	 * @return order number
	 */
	@ResponseBody
	@RequestMapping(value = {"/new"}, method = RequestMethod.POST)
	public ResponseEntity<?> createOrder(@RequestHeader(value = "Authorization") String token,
			@RequestBody WebOrder webOrder) {

		User user = userService.findByKey(webOrder.getUser().getId());

		if (user == null) {
			return new ResponseEntity<Message>(new Message("User not found"), HttpStatus.BAD_REQUEST);
		} else if (!checkPermission(token, user)) {
			return new ResponseEntity<Message>(new Message("User does not match"), HttpStatus.FORBIDDEN);
		}
		
		Cart cart = Cart.create(user);

		for (WebLineItem wlp : webOrder.getWebProductList()) {
			List<Product> products = productService.findAvailableByProductDescription(wlp.getProductDescription(),
					wlp.getQuantity());
			if (products.isEmpty()) {
				return new ResponseEntity<Message>(
						new Message(
								"Create order failed: [" + wlp.getProductDescription().getId() + "] id out of stock"),
						HttpStatus.BAD_REQUEST);
			}
			cart.addProducts(products);
		}

		Integer cartId = null;
		try {
			cartId = cartService.save(cart);

			cart = cartService.findByKey(cartId);
			Order order = Order.create(user, cart);

			Integer orderId = orderService.save(order);
			return new ResponseEntity<Integer>(orderId, HttpStatus.CREATED);
		} catch (Exception e) {
			System.out.println(e);
			return new ResponseEntity<Message>(new Message("Create order failed"), HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * Cancels order.
	 * 
	 * @param web
	 *            order model
	 * @return message
	 */
	@ResponseBody
	@RequestMapping(value = {"/cc"}, method = RequestMethod.POST)
	public ResponseEntity<?> cancelOrder(@RequestHeader(value = "Authorization") String token,
			@RequestBody WebOrder webOrder) {

		Order order = orderService.findByOrderNumber(webOrder.getOrderNumber());

		if (order == null) {
			return new ResponseEntity<Message>(new Message("Not found order"), HttpStatus.BAD_REQUEST);
		} else if (!checkPermission(token, order.getUser())) {
			return new ResponseEntity<Message>(new Message("User does not match"), HttpStatus.FORBIDDEN);
		}
		
		if (order.getStatus() == 0) {
			order.setStatus(Order.CANCELED);
			orderService.update(order);
			return new ResponseEntity<Message>(new Message("Order is canceled"), HttpStatus.OK);
		} else if (order != null && order.getStatus() == 1) {
			return new ResponseEntity<Message>(new Message("Order is already canceled"), HttpStatus.BAD_REQUEST);
		} else if (order != null && order.getStatus() > 1) {
			return new ResponseEntity<Message>(new Message("Order is already paid"), HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<Message>(new Message("Order not found"), HttpStatus.BAD_REQUEST);
	}
	
	@JsonView(OrderView.Summary.class)
	@RequestMapping(value = "/test/{orderNumber}", method = RequestMethod.GET)
	public ResponseEntity<?> getOrder(
			@PathVariable Integer orderNumber) {

		Order order = orderService.findByKey(orderNumber);
		
		if (order == null) {
			return new ResponseEntity<Message>(new Message("Not found order"), HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<Order>(order, HttpStatus.OK);
	}
}
