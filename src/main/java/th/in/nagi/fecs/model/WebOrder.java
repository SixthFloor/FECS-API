package th.in.nagi.fecs.model;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;

import th.in.nagi.fecs.view.WebOrderView;

/**
 * @author Chonnipa Kittisiriprasert
 */
public class WebOrder {

	/**
	 * order number
	 */
	@JsonView(WebOrderView.Personal.class)
	private Integer orderNumber;

	/**
	 * User
	 */
	@JsonView(WebOrderView.Personal.class)
	private User user;

	/**
	 * List of Product Description and quality (WebLineItem)
	 */
	@JsonProperty("cart")
	@JsonView(WebOrderView.Personal.class)
	private List<WebLineItem> webLineProduct;

	/**
	 * Shipping of this Order
	 */
	@JsonView(WebOrderView.Personal.class)
	private Shipping shipping;

	/**
	 * status of this Order
	 */
	@JsonView(WebOrderView.Personal.class)
	private int status;

	/**
	 * checkout date
	 */
	@JsonView(WebOrderView.Personal.class)
	private Date orderDate;

	public Integer getOrderNumber() {
		return orderNumber;
	}

	public User getUser() {
		return user;
	}

	public List<WebLineItem> getWebProductList() {
		return webLineProduct;
	}

	public Shipping getShipping() {
		return shipping;
	}

	public int getStatus() {
		return status;
	}

	public Date getOrderDate() {
		return orderDate;
	}

	public void setOrderNumber(Integer orderNumber) {
		this.orderNumber = orderNumber;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public void setWebProductList(List<WebLineItem> webProductList) {
		this.webLineProduct = webProductList;
	}

	/**
	 * add quantity by using WebLineItem. But that products have to have
	 * already.
	 * 
	 * @param webLineProduct
	 */
	public void addWebProductList(WebLineItem webLineProduct) {
		for (WebLineItem wlp : this.webLineProduct) {
			if (wlp.getProductDescription().getId() == webLineProduct.getProductDescription().getId()) {
				wlp.increaseQuantity(webLineProduct.getQuantity());
				return;
			}
		}

		this.webLineProduct.add(webLineProduct);
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

	/**
	 * create WebOrder by Order
	 * 
	 * @param order
	 * @return WebOrder
	 */
	public static WebOrder create(Order order) {
		WebOrder webOrder = new WebOrder();

		webOrder.setOrderNumber(order.getOrderNumber());
		webOrder.setUser(order.getUser());
		webOrder.setOrderDate(order.getOrderDate());
		webOrder.setStatus(order.getStatus());
		webOrder.setShipping(null);

		webOrder.setWebProductList(WebLineItem.create(order.getCart()));

		return webOrder;
	}
}
