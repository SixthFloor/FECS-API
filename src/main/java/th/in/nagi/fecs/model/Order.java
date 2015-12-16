package th.in.nagi.fecs.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.fasterxml.jackson.annotation.JsonView;

import th.in.nagi.fecs.view.OrderView;

/**
 * Order model
 * 
 * @author Chonnipa Kittisiriprasert
 *
 */
@Entity
@Table(name = "[order]")
public class Order {

	/**
	 * The possible status of Order
	 */
	public static final int NOTPAY = 0;
	public static final int CANCELED = 1;
	public static final int PAID = 2;
	public static final int SHIPPING = 3;
	public static final int COMPLETED = 4;

	/**
	 * Order's number
	 */
	@JsonView(OrderView.Personal.class)
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "serial_number", unique = true)
	private int orderNumber;

	/**
	 * Order's User
	 */
	@JsonView({OrderView.Personal.class, OrderView.User.class})
	@ManyToOne
	@JoinColumn(name = "user_id")
	@Fetch(FetchMode.SELECT)
	private User user;

	/**
	 * Order's Cart
	 */
	@JsonView({OrderView.Personal.class, OrderView.Cart.class})
	@OneToOne
	@JoinColumn(name = "cart_id")
	private Cart cart;

	/**
	 * Order Date
	 */
	@JsonView(OrderView.Personal.class)
	@Column(name = "order_date")
	private Date orderDate;

	/**
	 * Order's status
	 */
	@JsonView(OrderView.Personal.class)
	private int status;

	/**
	 * Order's Shipping
	 */
	@JsonView({OrderView.Personal.class, OrderView.Shipping.class})
	@OneToOne
	@JoinColumn(name = "shipping_id")
	private Shipping shipping;

	/**
	 * Order's total price
	 */
	@JsonView(OrderView.Personal.class)
	private Double total;

	public User getUser() {
		return user;
	}

	public Cart getCart() {
		return cart;
	}

	public Shipping getShipping() {
		return shipping;
	}

	public int getStatus() {
		return status;
	}

	public int getOrderNumber() {
		return orderNumber;
	}

	public Date getOrderDate() {
		return orderDate;
	}

	public Double getTotal() {
		return this.cart.getTotal();
	}

	public void setUser(User user) {
		this.user = user;
	}

	public void setCart(Cart cart) {
		this.cart = cart;
	}

	public void setShipping(Shipping shipping) {
		this.shipping = shipping;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	public void setTotal(Double total) {
		this.total = total;
	}

	public static Order create(User user, Cart cart) {
		Order order = new Order();
		order.setUser(user);
		order.setCart(cart);
		order.setOrderDate(new Date());
		order.setStatus(NOTPAY);
		order.setTotal(cart.getTotal());

		return order;
	}
}
