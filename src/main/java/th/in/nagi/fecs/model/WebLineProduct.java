package th.in.nagi.fecs.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;

import th.in.nagi.fecs.view.WebLineProductView;

public class WebLineProduct {

	@JsonProperty("product")
	@JsonView(WebLineProductView.ProductDescription.class)
	private ProductDescription productDescription;

	@JsonView(WebLineProductView.Personal.class)
	private int quantity;

	public ProductDescription getProductDescription() {
		return productDescription;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setProductDescription(ProductDescription productDescription) {
		this.productDescription = productDescription;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public void increaseQuantity(int quantity) {
		this.quantity += quantity;
	}
}
