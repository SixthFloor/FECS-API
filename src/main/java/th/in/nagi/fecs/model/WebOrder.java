package th.in.nagi.fecs.model;

import com.fasterxml.jackson.annotation.JsonView;

import th.in.nagi.fecs.view.WebOrderView;

public class WebOrder {

	@JsonView(WebOrderView.Personal.class)
	private String orderNumber;

	@JsonView(WebOrderView.Personal.class)
	private int userId;

	@JsonView(WebOrderView.WebCart.class)
	private WebCart webCart;

	public String getOrderNumber() {
		return orderNumber;
	}

	public int getUserId() {
		return userId;
	}

	public WebCart getWebCart() {
		return webCart;
	}
}
