package th.in.nagi.fecs.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;

import th.in.nagi.fecs.view.WebCreditCardView;

public class WebCreditCard {
	
	@JsonProperty("no")
	@JsonView(WebCreditCardView.Personal.class)
	private String number;

	@JsonProperty("holder_name")
	@JsonView(WebCreditCardView.Personal.class)
	private String holderName;
	
	@JsonProperty("exp_date")
	@JsonView(WebCreditCardView.Personal.class)
	private Date expirationDate;

	public String getNumber() {
		return number;
	}

	public String getHolderName() {
		return holderName;
	}

	public Date getExpirationDate() {
		return expirationDate;
	}
}