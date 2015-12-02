package th.in.nagi.fecs.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonView;

import th.in.nagi.fecs.view.ProductView;

@Entity
@Table(name = "product")
public class Product {

	@JsonView(ProductView.Personal.class)
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private int id;

	@JsonView(ProductView.ProductDescription.class)
	@ManyToOne
	@JoinColumn(name="product_description_id")
	private ProductDescription productDescription;
	
	@ManyToMany(mappedBy = "products")
	private List<Cart> carts;
	
	@JsonView(ProductView.Personal.class)
	@Column(nullable = false)
	private int status;

	public int getId() {
		return id;
	}

	public ProductDescription getProductDescription() {
		return productDescription;
	}

	public void setProductDescription(ProductDescription productDescription) {
		this.productDescription = productDescription;
	}

}
