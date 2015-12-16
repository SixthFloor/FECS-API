package th.in.nagi.fecs.model;

import org.json.JSONException;
import org.json.JSONObject;

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
	@JsonView(WebPaymentView.Personal.class)
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
	@JsonView(WebPaymentView.Personal.class)
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

	public JSONObject getJSONObject(double total, String ownerAccount) {
		JSONObject info = new JSONObject();
		try {
			info.put("card", webCreditCard.getJSONObject());
			info.put("cvv", String.valueOf(cvv));
			info.put("price", String.valueOf(total));
			info.put("owner_account", ownerAccount);
			return info;
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
	}
}