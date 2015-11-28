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

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import th.in.nagi.fecs.view.ProductDescriptionView;

/**
 * FurnitureDescription model
 * @author Thanachote Visetsuthimont
 *
 */
@Entity
//@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="id")
@Table(name = "product_description")
public class ProductDescription {

	/**
	 * id of product
	 */
	@JsonView(ProductDescriptionView.Personal.class)
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	/**
	 * serialNumber of product
	 */
	@JsonView(ProductDescriptionView.Personal.class)
	@Column(name = "serial_number", unique=true)
	private String serialNumber;

	/**
	 * name of product
	 */
	@JsonView(ProductDescriptionView.Personal.class)
	@Size(min = 1, max = 50)
	@Column(name = "name", nullable = false)
	private String name;
	
	/**
	 * price of product
	 */
	@JsonView(ProductDescriptionView.Personal.class)
	@Min(0)
	@Column(name = "price", nullable = false)
	private double price;
	
	/**
	 * description of product
	 */
	@JsonView(ProductDescriptionView.Personal.class)
	@Size(min = 1, max = 255)
	@Column(name = "description", nullable = true)
	private String description;
	
	/**
	 * dimension description of product
	 */
	@JsonView(ProductDescriptionView.Personal.class)
	@Size(min = 1, max = 255)
	@Column(name = "dimension_description", nullable = true)
	private String dimensionDescription;

	@JsonView(ProductDescriptionView.ElementalImage.class)
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "productDescription")
	private List<ProductImage> images;
	
	
	/**
	 * Return dimension description
	 * @return Dimension description
	 */
	public String getDimensionDescription() {
		return dimensionDescription;
	}

	/**
	 * Set new dimension description 
	 * @param dimensionDescription new dimension description
	 */
	public void setDimensionDescription(String dimensionDescription) {
		this.dimensionDescription = dimensionDescription;
	}

	/**
	 * Return id of product
	 * @return id of product
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * Set new serial number
	 * @param serialNumber new serial number 
	 */
	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	/**
	 * Return serial number of product
	 * @return serial number of product
	 */
	public String getSerialNumber() {
		return serialNumber;
	}

	/**
	 * Return name of product
	 * @return name of product
	 */
	public String getName() {
		return name;
	}

	/**
	 * Set new name of product
	 * @param name new name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Return price of product
	 * @return price of product
	 */
	public double getPrice() {
		return price;
	}

	/**
	 * Set new price of product
	 * @param price new price
	 */
	public void setPrice(double price) {
		this.price = price;
	}

	/**
	 * Return description of product
	 * @return description of product
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Set new description of product
	 * @param description new description
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	
	// /**
	// * Set new category of product
	// * @param category new category
	// */
	// public void setCategory(Category category) {
	// this.category = category;
	// }
	//
	// /**
	// * Return category of product
	// * @return category of product
	// */
	// public Category getCategory() {
	// return this.category;
	// }
    
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
	
	/** 
	 * Return a string representation of the object.
	 * @return a string representation of the object.
	 */
	@Override
	public String toString() {
		Class<?> clazz = this.getClass();
		StringBuilder sb = new StringBuilder("Class: " + clazz.getSimpleName()).append(" {");
        while (clazz != null && !clazz.equals(Object.class)) {
            Field[] fields = clazz.getDeclaredFields();
            for (Field f : fields) {
                if (!Modifier.isStatic(f.getModifiers())) {
                    try {
                        f.setAccessible(true);
                        sb.append(f.getName()).append(" = ").append(f.get(this)).append(",");
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
            clazz = clazz.getSuperclass();
        }
        sb.deleteCharAt(sb.lastIndexOf(","));
        return sb.append("}").toString();
	}	
}
