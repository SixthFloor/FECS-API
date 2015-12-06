package th.in.nagi.fecs.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;

import th.in.nagi.fecs.view.WebPaymentView;

public class WebPayment {
	
	@JsonProperty("card")
	@JsonView({WebPaymentView.Personal.class, WebPaymentView.WebCreditCard.class})
	private WebCreditCard webCreditCard;

	@JsonView(WebPaymentView.Personal.class)
	private Integer cvv;
	
	@JsonView(WebPaymentView.Personal.class)
	private Integer price;
	
	@JsonView({WebPaymentView.Personal.class, WebPaymentView.Shipping.class})
	private Shipping shipping;
}