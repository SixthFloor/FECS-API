package th.in.nagi.fecs.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.fasterxml.jackson.annotation.JsonView;

import th.in.nagi.fecs.view.CatalogView;

/**
 * Category model
 * 
 * @author Thanachote Visetsuthimont
 *
 */
@Entity
@Table(name = "catalog")
public class Catalog {

	/**
	 * Catalog's id
	 */
	@JsonView(CatalogView.Personal.class)
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	/**
	 * Catalog's Type
	 */
	@JsonView(CatalogView.Type.class)
	@ManyToOne
	@Fetch(FetchMode.SELECT)
	@JoinColumn(name="type_id")
	private Type type;

	/**
	 * Catalog's Product Description
	 */
	@JsonView(CatalogView.ProductDescription.class)
	@ManyToOne
	@Fetch(FetchMode.SELECT)
	@JoinColumn(name="product_description_id")
	private ProductDescription productDescription;

	public void setId(Integer id) {
		this.id = id;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public ProductDescription getProductDescription() {
		return productDescription;
	}

	public void setProductDescription(ProductDescription productDescription) {
		this.productDescription = productDescription;
	}

	public Integer getId() {
		return id;
	}
	
	
}
