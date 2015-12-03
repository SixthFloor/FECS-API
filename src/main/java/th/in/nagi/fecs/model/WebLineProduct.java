package th.in.nagi.fecs.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;

import th.in.nagi.fecs.service.ProductDescriptionService;
import th.in.nagi.fecs.view.WebLineProductView;

public class WebLineProduct {

	@Autowired
	private static ProductDescriptionService productDescriptionService;

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

	public static List<WebLineProduct> create(Cart cart) {
		List<WebLineProduct> webLineProducts = new ArrayList<WebLineProduct>();
		List<Product> products = cart.getProducts();
		Map<ProductDescription, Integer> map = new HashMap<ProductDescription, Integer>();

		for (Product p : products) {
			ProductDescription pd = p.getProductDescription();
			if (map.containsKey(pd)) {
				int count = map.get(pd);
				map.put(pd, count + 1);
			} else {
				map.put(pd, 1);
			}
		}

		for (Map.Entry<ProductDescription, Integer> entry : map.entrySet()) {
			WebLineProduct wlp = new WebLineProduct();
			wlp.setProductDescription(entry.getKey());
			wlp.setQuantity(entry.getValue());
			webLineProducts.add(wlp);
		}

		return webLineProducts;
	}
}
