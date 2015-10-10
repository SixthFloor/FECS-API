package th.in.nagi.fecs.model;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

@Entity
@Table(name = "product")
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "serial_number")
	private String serialNumber;

	@Size(min = 1, max = 50)
	@Column(name = "name", nullable = false)
	private String name;
	
	@Min(0)
	@Column(name = "price", nullable = false)
	private double price;
	
	@Size(min = 1, max = 255)
	@Column(name = "description", nullable = true)
	private String description;
	
	@Size(min = 1, max = 255)
	@Column(name = "dimension_description", nullable = true)
	private String dimension_description;
	
	public String getDimension_description() {
		return dimension_description;
	}

	public void setDimension_description(String dimension_description) {
		this.dimension_description = dimension_description;
	}

	@Size(min = 1, max = 50)
	@OneToOne
	@MapsId
	private Category category;

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
	
    public void setCategory(Category category) {
		this.category = category;
	}
    
    public Category getCategory() {
		return this.category;
	}

	@Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!(obj instanceof Product))
            return false;
        Product other = (Product) obj;
        if (id != other.id){
            return false;
        } else if (!serialNumber.equals(other.serialNumber))
            return false;
        return true;
    }
	
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
