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

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.fasterxml.jackson.annotation.JsonView;

import th.in.nagi.fecs.view.AuthenticationView;

/**
 * Authentication model
 * 
 * @author Nara Surawit
 *
 */
@Entity
@Table(name = "authentication")
public class Authentication {

	/**
	 * id of Authentication
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	/**
	 * token of Authentication
	 */
	@JsonView(AuthenticationView.Personal.class)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "token")
	private String token;

	/**
	 * user's Authentication
	 */
	@ManyToOne
	@Fetch(FetchMode.SELECT)
	private User user;

	/**
	 * expiration of Authentication
	 */
	@JsonView(AuthenticationView.Personal.class)
	@Column(name = "expiration_date", nullable = false)
	private Date expDate;

	/**
	 * create Authentication
	 */
	public Authentication() {

	}

	/**
	 * create Authentication
	 * 
	 * @param token
	 */
	public Authentication(String token) {
		this.token = token;
	}

	/**
	 * create Authentication
	 * 
	 * @param token
	 * @param user
	 * @param date
	 */
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

}
