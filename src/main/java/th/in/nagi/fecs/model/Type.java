package th.in.nagi.fecs.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonView;

import th.in.nagi.fecs.view.TypeView;

@Entity
@Table(name = "type")
public class Type {
	
	@JsonView(TypeView.Personal.class)
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@JsonView(TypeView.Category.class)
	@ManyToOne
	@JoinColumn(name="category_id")
	private Category category;

	@JsonView(TypeView.SubCategory.class)
	@ManyToOne
	@JoinColumn(name="sub_category_id")
	private SubCategory subCategory;
	
	@JsonView(TypeView.Catalogs.class)
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "type")
	private List<Catalog> catalogs;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

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

	public List<Catalog> getCatalogs() {
		return catalogs;
	}

	public void setCatalogs(List<Catalog> catalogs) {
		this.catalogs = catalogs;
	}
	
	

}
