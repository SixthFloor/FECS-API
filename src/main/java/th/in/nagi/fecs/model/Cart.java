package th.in.nagi.fecs.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import com.fasterxml.jackson.annotation.JsonView;

import th.in.nagi.fecs.view.CartView;

@Entity
@Table(name = "cart")
public class Cart {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonView(CartView.Personal.class)
	private Integer id;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	@OneToMany(cascade = CascadeType.MERGE)
	@LazyCollection(LazyCollectionOption.FALSE)
	@JoinTable(name = "product_in_cart", joinColumns = {
			@JoinColumn(name = "cart_id", referencedColumnName = "id")}, inverseJoinColumns = {
					@JoinColumn(name = "product_id", referencedColumnName = "id")})
	@JsonView({CartView.Personal.class, CartView.Product.class})
	private List<Product> products = new ArrayList<Product>();

	public Integer getId() {
		return id;
	}

	public User getUser() {
		return user;
	}

	public List<Product> getProducts() {
		return products;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public void setProduct(List<Product> products) {
		this.products = products;
	}

	public void addProducts(List<Product> products) {
		this.products.addAll(products);
	}

	public void addProduct(Product product) {
		this.products.add(product);
	}

	public static Cart create(User user) {
		Cart cart = new Cart();
		cart.setUser(user);

		return cart;
	}
}
