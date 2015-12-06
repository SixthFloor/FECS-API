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

import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.annotation.JsonView;

import th.in.nagi.fecs.service.ProductService;
import th.in.nagi.fecs.view.ProductView;

@Entity
@Table(name = "product")
public class Product {

	public static final int AVAILABLE = 0;
	public static final int CRACKED = 1;
	public static final int SOLD = 2;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(nullable = false)
	@JsonView(ProductView.Personal.class)
	private Integer id;

	@ManyToOne
	@JoinColumn(name = "product_description_id")
	@JsonView({ProductView.Personal.class, ProductView.ProductDescription.class})
	private ProductDescription productDescription;

//	@Column(name = "bought_price")
//	private Integer boughtPrice;
	
	@ManyToMany(mappedBy = "products")
	private List<Cart> carts;

	@Column(nullable = false)
	@JsonView(ProductView.Personal.class)
	private int status;

	public int getId() {
		return id;
	}

	public ProductDescription getProductDescription() {
		return productDescription;
	}
	
	public int getStatus() {
		return status;
	}

	public void setProductDescription(ProductDescription productDescription) {
		this.productDescription = productDescription;
	}
	
	public void setStatus(int status) {
		this.status = status;
	}
}
