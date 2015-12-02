package th.in.nagi.fecs.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonView;

import th.in.nagi.fecs.view.WebCartView;

public class WebCart {

	@JsonView(WebCartView.Product.class)
	private List<WebLineProduct> products = new ArrayList<WebLineProduct>();

	public List<WebLineProduct> getProducts() {
		return products;
	}
}
