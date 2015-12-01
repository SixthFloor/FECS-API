package th.in.nagi.fecs.model;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonView;

import th.in.nagi.fecs.view.AuthenticationView;

/**
 * FurnitureDescription model
 * 
 * @author Nara Surawit
 *
 */
@Entity
@Table(name = "authentication")
public class Authentication {

	/**
	 * id of product
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	/**
	 * serialNumber of product
	 */
	@JsonView(AuthenticationView.Personal.class)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "token")
	private String token;

	/**
	 * name of product
	 */

	@ManyToOne
	private User user;

	@JsonView(AuthenticationView.Personal.class)
	@Column(name = "expiration_date", nullable = false)
	private Date expDate;

	public Authentication() {

	}

	public Authentication(String token) {
		this.token = token;
	}

	public Authentication(String token, User user, Date date) {
		this.token = token;
		this.user = user;
		this.expDate = date;
	}

	@JsonView(AuthenticationView.Role.class)
	public Role getRole() {
		return user.getRole();
	}
	

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	@JsonView(AuthenticationView.User.class)
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Date getExpDate() {
		return expDate;
	}

	public void setExpDate(Date expDate) {
		this.expDate = expDate;
	}

	// /**
	// * Indicates whether some other object is "equal to" this one.
	// * @parem obj the reference object with which to compare.
	// * @return true if this object is the same as the obj argument; false
	// otherwise.
	// */
	// @Override
	// public boolean equals(Object obj) {
	// if (this == obj)
	// return true;
	// if (obj == null)
	// return false;
	// if (!(obj instanceof FurnitureDescription))
	// return false;
	// FurnitureDescription other = (FurnitureDescription) obj;
	// if (id != other.id){
	// return false;
	// } else if (!serialNumber.equals(other.serialNumber))
	// return false;
	// return true;
	// }
	//
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
