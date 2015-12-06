package th.in.nagi.fecs.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;

import th.in.nagi.fecs.view.OrderView;
import th.in.nagi.fecs.view.WebOrderView;

public class WebOrder {

	@JsonView(WebOrderView.Personal.class)
	private Integer orderNumber;

	@JsonView({WebOrderView.Personal.class, WebOrderView.User.class})
	private User user;

	@JsonProperty("cart")
	@JsonView({WebOrderView.Personal.class, WebOrderView.WebLineProduct.class})
	private List<WebLineItem> webLineProduct;
	
	@JsonView({WebOrderView.Personal.class, WebOrderView.Shipping.class})
	private Shipping shipping;

	@JsonView(WebOrderView.Personal.class)
	private int status;
	
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
	
	public void addWebProductList(WebLineItem webLineProduct) {
		for (WebLineItem wlp: this.webLineProduct) {
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
