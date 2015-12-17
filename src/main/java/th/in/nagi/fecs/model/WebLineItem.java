package th.in.nagi.fecs.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;

import th.in.nagi.fecs.service.ProductDescriptionService;
import th.in.nagi.fecs.view.WebLineItemView;

/**
 * @author Chonnipa Kittisiriprasert
 */
public class WebLineItem {

	/**
	 * Tool for managing about Product Description
	 */
	@Autowired
	private static ProductDescriptionService productDescriptionService;

	/**
	 * Product Description
	 */
	@JsonProperty("product")
	@JsonView(WebLineItemView.Personal.class)
	private ProductDescription productDescription;

	/**
	 * quantity of Product Description
	 */
	@JsonView(WebLineItemView.Personal.class)
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

	/**
	 * add more quantity
	 * 
	 * @param quantity
	 */
	public void increaseQuantity(int quantity) {
		this.quantity += quantity;
	}

	/**
	 * create list of WebLineItems. This method will update product's quantity
	 * from Cart.
	 * 
	 * @param cart
	 * @return List<WebLineItem>
	 */
	public static List<WebLineItem> create(Cart cart) {
		List<WebLineItem> webLineProducts = new ArrayList<WebLineItem>();
		List<Product> products = cart.getProducts();
		Map<ProductDescription, Integer> map = new HashMap<ProductDescription, Integer>();

		for (Product p : products) {
			ProductDescription pd = p.getProductDescription();
			if (map.containsKey(pd)) {
				int count = map.get(pd).intValue();
				map.put(pd, count + 1);
			} else {
				map.put(pd, 1);
			}
		}

		for (Map.Entry<ProductDescription, Integer> entry : map.entrySet()) {
			WebLineItem wlp = new WebLineItem();
			wlp.setProductDescription(entry.getKey());
			wlp.setQuantity(entry.getValue().intValue());
			webLineProducts.add(wlp);
		}

		return webLineProducts;
	}
}
