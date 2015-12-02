package th.in.nagi.fecs.controller;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
import th.in.nagi.fecs.model.WebCart;
import th.in.nagi.fecs.model.WebOrder;
import th.in.nagi.fecs.model.WebLineProduct;
import th.in.nagi.fecs.service.AuthenticationService;
import th.in.nagi.fecs.service.CartService;
import th.in.nagi.fecs.service.OrderService;
import th.in.nagi.fecs.service.ProductDescriptionService;
import th.in.nagi.fecs.service.ProductService;
import th.in.nagi.fecs.service.UserService;
import th.in.nagi.fecs.view.OrderView;

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
	private ProductDescriptionService productDescriptionService;

	@Autowired
	private ProductService productService;

	/**
	 * Lists all existing orders.
	 * 
	 * @return list of orders
	 */
	@JsonView(OrderView.Personal.class)
	@RequestMapping(value = "/all", method = RequestMethod.GET)
	public ResponseEntity getAllOrders(@RequestHeader(value = "Authorization") String token) {
		if (!authenticationService.checkPermission(token, authenticationService.STAFF, authenticationService.MANAGER,
				authenticationService.OWNER)) {
			return new ResponseEntity(new Message("This order does not allow"), HttpStatus.FORBIDDEN);
		}

		Set<Order> orders = new HashSet<Order>(orderService.findAll());
		if (orders != null) {
			return new ResponseEntity(orders, HttpStatus.OK);
		}
		return new ResponseEntity(new Message("Not found order"), HttpStatus.BAD_REQUEST);
	}

	/**
	 * Lists all existing orders.
	 * 
	 * @return list of orders
	 */
	@JsonView(OrderView.Personal.class)
	@RequestMapping(value = "/{orderNumber}", method = RequestMethod.GET)
	public ResponseEntity getOrder(@RequestHeader(value = "Authorization") String token,
			@PathVariable int orderNumber) {
		if (!authenticationService.checkPermission(token, authenticationService.MEMBER, authenticationService.STAFF,
				authenticationService.MANAGER, authenticationService.OWNER)) {
			return new ResponseEntity(new Message("This order does not allow"), HttpStatus.FORBIDDEN);
		}

		Order order = orderService.findByOrderNumber(orderNumber);
		if (order != null) {
			return new ResponseEntity(order, HttpStatus.OK);
		}
		return new ResponseEntity(new Message("Not found order"), HttpStatus.BAD_REQUEST);
	}

	/**
	 * Creates new order.
	 * 
	 * @param model
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = {"/new"}, method = RequestMethod.POST)
	public ResponseEntity createOrder(@RequestBody WebOrder webOrder,
			@RequestHeader(value = "Authorization") String token) {
		if (!authenticationService.checkPermission(token, authenticationService.MEMBER, authenticationService.STAFF, authenticationService.MANAGER,
				authenticationService.OWNER)) {
			return new ResponseEntity(new Message("This user does not allow"), HttpStatus.FORBIDDEN);
		}

		Date date = new Date();
		User user = userService.findByKey(webOrder.getUserId());

		Cart cart = new Cart();
		cart.setUser(user);

		WebCart webCart = webOrder.getWebCart();
		List<WebLineProduct> webLineProduct = webCart.getProducts();
		for (int i = 0; i < webLineProduct.size(); i++) {
			WebLineProduct wlp = webLineProduct.get(i);
			List<Product> products = productService.findByProductDescription(wlp.getProductDescription(),
					wlp.getQuantity());
			cart.addProduct(products);
		}

		try {
			cartService.store(cart);
		} catch (Exception e) {
			System.out.println(e);
			return new ResponseEntity(new Message("Create order failed"), HttpStatus.BAD_REQUEST);
		}

		Order order = new Order();
		order.setOrderDate(date);
		order.setUser(user);
		order.setStatus(0);

		cart = cartService.findLastInserted();
		order.setCart(cart);

		try {
			orderService.store(order);
		} catch (Exception e) {
			System.out.println(e);
			return new ResponseEntity(new Message("Create order failed"), HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity(new Message("The order has created"), HttpStatus.CREATED);
	}
}
