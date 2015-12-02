package th.in.nagi.fecs.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Min;

import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name = "cart")
public class Cart {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "product_in_cart", joinColumns = {
			@JoinColumn(name = "cart_id", referencedColumnName = "id")}, inverseJoinColumns = {
					@JoinColumn(name = "product_id", referencedColumnName = "id")})
	private List<Product> products = new ArrayList<Product>();

	//	@OneToOne(mappedBy = "cart")
	//	private Order order;

	public Integer getId() {
		return id;
	}

	public User getUser() {
		return user;
	}

	public List<Product> getProduct() {
		return products;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public void setProduct(List<Product> products) {
		this.products = products;
	}

	public void addProduct(List<Product> products) {
		this.products.addAll(products);
	}

	public void addProduct(Product product) {
		this.products.add(product);
	}
}
