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

import th.in.nagi.fecs.view.FurnitureImageView;
import th.in.nagi.fecs.view.FurnitureDescriptionView;

@Entity
//@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="id")
@Table(name = "furniture_image")
public class FurnitureImage {
	
	@JsonView(FurnitureImageView.Personal.class)
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@JsonView(FurnitureImageView.Personal.class)
	@Size(min = 1, max = 255)
	@Column(name = "link", nullable = false)
	private String link;
	
	@JsonView(FurnitureImageView.Summary.class)
	@ManyToOne
	@JoinColumn(name="furniture_description_id")
	private FurnitureDescription furnitureDescription;

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
