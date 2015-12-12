package th.in.nagi.fecs.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonView;

import th.in.nagi.fecs.view.ProductImageView;

/**
 * ProductImage model
 * 
 * @author Thanachote Visetsuthimont
 *
 */
@Entity
@Table(name = "product_image")
public class ProductImage {

	/**
	 * id of ProductImage
	 */
	@JsonView(ProductImageView.Personal.class)
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	/**
	 * link of ProductImage
	 */
	@JsonView(ProductImageView.Personal.class)
	@Size(min = 1, max = 255)
	@Column(name = "link", nullable = false)
	private String link;

	/**
	 * productDescription of ProductImage
	 */
	@JsonView(ProductImageView.Summary.class)
	@ManyToOne
	@JoinColumn(name = "product_description_id")
	private ProductDescription productDescription;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

}
