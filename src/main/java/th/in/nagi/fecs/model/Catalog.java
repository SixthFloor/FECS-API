package th.in.nagi.fecs.model;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Cascade;
import org.springframework.web.servlet.View;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import th.in.nagi.fecs.view.CategoryView;
import th.in.nagi.fecs.view.ProductDescriptionView;

/**
 * Category model
 * 
 * @author Thanachote Visetsuthimont
 *
 */
@Entity
//@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="id")
@Table(name = "catalog")
public class Catalog {

	@JsonView(CategoryView.Personal.class)
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@JsonView(CategoryView.Personal.class)
	@ManyToOne
	@JoinColumn(name="category_id")
	private Category category;

	@JsonView(CategoryView.Personal.class)
	@ManyToOne
	@JoinColumn(name="sub_category_id")
	private SubCategory subCategory;

	@JsonView(CategoryView.Personal.class)
	@ManyToOne
	@JoinColumn(name="product_description_id")
	private ProductDescription productDescription;

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public SubCategory getSubCategory() {
		return subCategory;
	}

	public void setSubCategory(SubCategory subCategory) {
		this.subCategory = subCategory;
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
