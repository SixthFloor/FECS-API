package th.in.nagi.fecs.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "shipping")
public class Shipping {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private Date date;

	private String team;

	public int getId() {
		return id;
	}

	public Date getDate() {
		return date;
	}

	public String getTeam() {
		return team;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public void setTeam(String team) {
		this.team = team;
	}
}
