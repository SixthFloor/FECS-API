package th.in.nagi.fecs.model;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.List;
import java.util.Set;

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
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import th.in.nagi.fecs.service.ProductService;
import th.in.nagi.fecs.view.ProductDescriptionView;

/**
 * ProductDescription model
 * @author Thanachote Visetsuthimont
 *
 */
@Entity
@Table(name = "product_description")
public class ProductDescription {
	
	/**
	 * The possible status of ProductDescription
	 */
	public static final int SELL = 0;
	public static final int HIDE = 1;

	/**
	 * id of ProductDescription
	 */
	@JsonView(ProductDescriptionView.Personal.class)
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	/**
	 * serialNumber of ProductDescription
	 */
	@JsonView(ProductDescriptionView.Personal.class)
	@Column(name = "serial_number", unique=true)
	private String serialNumber;

	/**
	 * name of ProductDescription
	 */
	@JsonView(ProductDescriptionView.Personal.class)
	@Size(min = 1, max = 50)
	@Column(name = "name", nullable = false)
	private String name;
	
	/**
	 * price of ProductDescription
	 */
	@JsonView(ProductDescriptionView.Personal.class)
	@Min(0)
	@Column(name = "price", nullable = false)
	private double price;
	
	/**
	 * description of ProductDescription
	 */
	@JsonView(ProductDescriptionView.Personal.class)
	@Size(min = 1, max = 255)
	@Column(name = "description", nullable = true)
	private String description;
	
	/**
	 * dimension description of ProductDescription
	 */
	@JsonView(ProductDescriptionView.Personal.class)
	@Size(min = 1, max = 255)
	@Column(name = "dimension_description", nullable = true)
	private String dimensionDescription;

	/**
	 * images list of ProductDescription
	 */
	@JsonView(ProductDescriptionView.ElementalImage.class)
	@OneToMany(mappedBy = "productDescription")
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<ProductImage> images;
	
	/**
	 * products list of ProductDescription
	 */
	@OneToMany(mappedBy = "productDescription")
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<Product> products;
	
	/**
	 * status of ProductDescription
	 */
	@JsonView(ProductDescriptionView.Personal.class)
	private Integer status;
	
	public String getDimensionDescription() {
		return dimensionDescription;
	}

	public void setDimensionDescription(String dimensionDescription) {
		this.dimensionDescription = dimensionDescription;
	}

	public Integer getId() {
		return id;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public String getSerialNumber() {
		return serialNumber;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

    public List<ProductImage> getImages() {
		return images;
	}

	public void setImages(List<ProductImage> images) {
		this.images = images;
	}

	public void setId(Integer id) {
		this.id = id;
	}
		
	/**
	 * get product quantity that are available of ProductDescription
	 */
	@JsonView(ProductDescriptionView.Personal.class)
	public int getQuantity() {
		int quantity = 0;
		if(products != null){
			for (Product product: products) {
				if (product.isAvailable()) {
					quantity++;
				}
			}
		}
		return quantity;
	}

	/**
     * Indicates whether some other object is "equal to" this one.
     * @parem obj the reference object with which to compare.
     * @return true if this object is the same as the obj argument; false otherwise.
     */
	@Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!(obj instanceof ProductDescription))
            return false;
        ProductDescription other = (ProductDescription) obj;
        if (id != other.id){
            return false;
        } else if (!serialNumber.equals(other.serialNumber))
            return false;
        return true;
    }

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
}
