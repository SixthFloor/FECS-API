package th.in.nagi.fecs.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;

import th.in.nagi.fecs.view.WebPaymentView;

/**
 * @author Chonnipa Kittisiriprasert
 */
public class WebPayment {
	
	/**
	 * WebCreditCard (credit card information)
	 */
	@JsonProperty("card")
	@JsonView({WebPaymentView.Personal.class, WebPaymentView.WebCreditCard.class})
	private WebCreditCard webCreditCard;

	/**
	 * the a three digit number of credit card
	 */
	@JsonView(WebPaymentView.Personal.class)
	private String cvv;
	
	/**
	 * the total price of the order
	 */
	@JsonView(WebPaymentView.Personal.class)
	private Double price;
	
	/**
	 * Shipping (shipping information)
	 */
	@JsonView({WebPaymentView.Personal.class, WebPaymentView.Shipping.class})
	private Shipping shipping;

	public WebCreditCard getWebCreditCard() {
		return webCreditCard;
	}

	public String getCvv() {
		return cvv;
	}

	public Double getPrice() {
		return price;
	}

	public Shipping getShipping() {
		return shipping;
	}

	public void setPrice(Double price) {
		this.price = price;
	}
}