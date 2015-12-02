package th.in.nagi.fecs.model;

import com.fasterxml.jackson.annotation.JsonView;

import th.in.nagi.fecs.view.WebLineProductView;

public class WebLineProduct {

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
}
