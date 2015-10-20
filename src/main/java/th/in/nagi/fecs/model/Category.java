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
import th.in.nagi.fecs.view.ProductView;

/**
 * Category model
 * 
 * @author Thanachote Visetsuthimont
 *
 */
@Entity
//@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="id")
@Table(name = "category")
public class Category {

	/**
	 * id of category
	 */
	@JsonView(CategoryView.Personal.class)
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	/**
	 * Name of category
	 */
	@JsonView(CategoryView.Personal.class)
	@Size(min = 1, max = 50)
	@Column(name = "name", nullable = false)
	private String name;

	@JsonView(CategoryView.ElementalSubCategory.class)
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "category")
	@Cascade(org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
	private Set<SubCategory> subCategories;

	
	public Set<SubCategory> getSubCategories() {
		return subCategories;
	}

	public void setSubCategories(Set<SubCategory> subCategories) {
		this.subCategories = subCategories;
	}
	
	@JsonIgnore
	public Set<Product> getProducts() {
		Set<Product> products = new HashSet<>();
		for (SubCategory subCategory : getSubCategories()) {
			products.addAll(subCategory.getProducts());
		}
		return products;
	}

	/**
	 * Set new id of category
	 * 
	 * @param id
	 *            new id
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * Set new name of category
	 * 
	 * @param name
	 *            new name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Return id of category
	 * 
	 * @return id of category
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * Return name of category
	 * 
	 * @return name of category
	 */
	public String getName() {
		return name;
	}

	/**
	 * Indicates whether some other object is "equal to" this one.
	 * 
	 * @parem obj the reference object with which to compare.
	 * @return true if this object is the same as the obj argument; false
	 *         otherwise.
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof User))
			return false;
		Category other = (Category) obj;
		if (id != other.id) {
			return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	/**
	 * Return a string representation of the object.
	 * 
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
