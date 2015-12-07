package th.in.nagi.fecs.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonView;

import th.in.nagi.fecs.view.ShippingView;
import th.in.nagi.fecs.view.WebOrderView;

@Entity
@Table(name = "shipping")
public class Shipping {
	
	public static final int AVAILABLE = 0;
	public static final int RESERVED = 1;
	public static final int INPROGRESS = 2;
	public static final int DONE = 3;
	
	@JsonView(ShippingView.Personal.class)
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@JsonView(ShippingView.Personal.class)
	private Date date;
	
	@JsonView(ShippingView.Personal.class)
	private int status;

	public int getId() {
		return id;
	}
	
	public Date getDate() {
		return date;
	}
	
	public void resetId() {
		id = null;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
	public void setStatus(int status) {
		this.status = status;
	}
}
