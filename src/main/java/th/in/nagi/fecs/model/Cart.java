package th.in.nagi.fecs.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Min;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonView;

import th.in.nagi.fecs.view.CartView;

@Entity
@Table(name = "cart")
public class Cart {

	@JsonView(CartView.Personal.class)
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@JsonView(CartView.User.class)
	@ManyToOne
	private User user;
	
	@JsonView(CartView.Personal.class)
	@Column(name = "quantity", nullable = false)
	private Integer quantity;
	
	@JsonView(CartView.ProductDescription.class)
	@ManyToOne
	@JoinColumn(name="product_description_id")
	private ProductDescription productDescription;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public ProductDescription getProductDescription() {
		return productDescription;
	}

	public void setProductDescription(ProductDescription productDescription) {
		this.productDescription = productDescription;
	}
	
	
}
