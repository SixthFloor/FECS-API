package th.in.nagi.fecs.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.fasterxml.jackson.annotation.JsonView;

import th.in.nagi.fecs.view.ProductView;

/**
 * Product model
 * 
 * @author Chonnipa Kittisiriprasert
 *
 */
@Entity
@Table(name = "product")
public class Product {

	/**
	 * The possible status of Product
	 */
	public static final int AVAILABLE = 0;
	public static final int CRACKED = 1;
	public static final int SOLD = 2;

	/**
	 * Product's id
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(nullable = false)
	@JsonView(ProductView.Personal.class)
	private Integer id;

	/**
	 * Product's Product Description
	 */
	@ManyToOne
	@JoinColumn(name = "product_description_id")
	@JsonView({ProductView.Personal.class, ProductView.ProductDescription.class})
	private ProductDescription productDescription;

	/**
	 * Product's status
	 */
	@Column(nullable = false)
	@JsonView(ProductView.Personal.class)
	private int status;

	/**
	 * Product's Cart. Cart can be null.
	 */
	@ManyToOne
	@JoinTable(name = "product_in_cart", joinColumns = {
			@JoinColumn(name = "product_id", referencedColumnName = "id")}, inverseJoinColumns = {
					@JoinColumn(name = "cart_id", referencedColumnName = "id")})
	@Fetch(FetchMode.SELECT)
	private Cart cart;

	/**
	 * Product's price that User buy in that time.
	 */
	@Column(name = "bought_price")
	private Double boughtPrice;

	public int getId() {
		return id;
	}

	public ProductDescription getProductDescription() {
		return productDescription;
	}

	public int getStatus() {
		return status;
	}
	
	public Cart getCart() {
		return cart;
	}

	public Double getBoughtPrice() {
		return boughtPrice;
	}

	public void setProductDescription(ProductDescription productDescription) {
		this.productDescription = productDescription;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public void setBoughtPrice() {
		this.boughtPrice = this.productDescription.getPrice();
	}

	/**
	 * Check that this product can be bought.
	 */
	public boolean isAvailable() {
		return (status == Product.AVAILABLE && cart == null);
	}

	/**
	 * Reset the price.
	 */
	public void resetBoughtPrice() {
		this.boughtPrice = 0.0;
	}
}
