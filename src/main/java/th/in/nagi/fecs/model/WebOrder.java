package th.in.nagi.fecs.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;

import th.in.nagi.fecs.view.WebOrderView;

public class WebOrder {

	@JsonView(WebOrderView.Personal.class)
	private Integer orderNumber;

	@JsonView({WebOrderView.Personal.class, WebOrderView.User.class})
	private User user;

	@JsonProperty("cart")
	@JsonView({WebOrderView.Personal.class, WebOrderView.WebLineProduct.class})
	private List<WebLineProduct> webLineProduct;

	public Integer getOrderNumber() {
		return orderNumber;
	}

	public User getUser() {
		return user;
	}

	public List<WebLineProduct> getProductList() {
		return webLineProduct;
	}

	public List<WebLineProduct> getWebProductList() {
		return webLineProduct;
	}

	public void setOrderNumber(Integer orderNumber) {
		this.orderNumber = orderNumber;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public void setWebProductList(List<WebLineProduct> webProductList) {
		this.webLineProduct = webProductList;
	}
	
	public void addWebProductList(WebLineProduct webLineProduct) {
		for (WebLineProduct wlp: this.webLineProduct) {
			if (wlp.getProductDescription().getId() == webLineProduct.getProductDescription().getId()) {
				wlp.increaseQuantity(webLineProduct.getQuantity());
				return;
			}
		}
		
		this.webLineProduct.add(webLineProduct);
	}
	
	public static WebOrder create(Order order) {
		WebOrder webOrder = new WebOrder();
		
		webOrder.setOrderNumber(order.getOrderNumber());
		webOrder.setUser(order.getUser());
		
		webOrder.setWebProductList(WebLineProduct.create(order.getCart()));
		
		return webOrder;
	}
}
