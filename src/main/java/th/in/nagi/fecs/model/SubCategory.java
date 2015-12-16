package th.in.nagi.fecs.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonView;

import th.in.nagi.fecs.view.SubCategoryView;

/**
 * SubCategory model
 * 
 * @author Thanachote Visetsuthimont
 *
 */
@Entity
@Table(name = "sub_category")
public class SubCategory {

	/**
	 * SubCategory's id
	 */
	@JsonView(SubCategoryView.Personal.class)
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(unique = true)
	private Integer id;

	/**
	 * SubCategory's name
	 */
	@JsonView(SubCategoryView.Personal.class)
	@Column(name = "name", nullable = false)
	private String name;

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

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof ProductDescription))
			return false;
		SubCategory other = (SubCategory) obj;
		if (id != other.id) {
			return false;
		}
		return true;
	}

}
