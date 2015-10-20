package th.in.nagi.fecs.model;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.Cascade;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="id")
@Table(name = "sub_category")
public class SubCategory {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(unique=true)
	private Integer id;

	@Column(name = "name", nullable = false)
	private String name;

	@ManyToOne
	@JsonIgnore
	private Category category;

	@JsonIgnore
	@OneToMany(fetch = FetchType.EAGER, mappedBy="subCategory")
	@Cascade(org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
	private Set<Product> products;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public Set<Product> getProducts() {
		return products;
	}
	
	public Category getCategory() {
		return category;
	}

	public void setProducts(Set<Product> products) {
		this.products = products;
	}

	public void setCategory(Category category) {
		this.category = category;
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
