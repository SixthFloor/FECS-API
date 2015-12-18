package th.in.nagi.fecs.model;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import com.fasterxml.jackson.annotation.JsonView;

import th.in.nagi.fecs.view.UserView;

/**
 * User model
 * 
 * @author Chonnipa Kittisiriprasert
 *
 */
@Entity
@Table(name = "user")
public class User {

	/**
	 * User's id
	 */
	@JsonView(UserView.Personal.class)
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	/**
	 * User's email
	 */
	@JsonView(UserView.Personal.class)
	@Column(name = "email", nullable = false)
	private String email;

	/**
	 * User's first name
	 */
	@JsonView(UserView.Personal.class)
	@Size(min = 1, max = 50)
	@Column(name = "first_name", nullable = false)
	private String firstName;

	/**
	 * User's last name
	 */
	@JsonView(UserView.Personal.class)
	@Size(min = 1, max = 50)
	@Column(name = "last_name", nullable = false)
	private String lastName;

	/**
	 * The date when this User join
	 */
	@JsonView(UserView.Personal.class)
	@Column(name = "joining_date", nullable = false)
	private Date joiningDate;

	/**
	 * User's password
	 */
	@Size(min = 64, max = 64)
	@Column(name = "password", nullable = false)
	private String password;

	/**
	 * User's address(line 1)
	 */
	@JsonView(UserView.Location.class)
	@Column(name = "address_1", nullable = true)
	private String address1;

	/**
	 * User's address(line 2)
	 */
	@JsonView(UserView.Location.class)
	@Column(name = "address_2", nullable = true)
	private String address2;

	/**
	 * User's province
	 */
	@JsonView(UserView.Location.class)
	@Column(name = "province", nullable = true)
	private String province;

	/**
	 * User's zipcode
	 */
	@JsonView(UserView.Location.class)
	@Size(min = 5, max = 5)
	@Column(name = "zipcode", nullable = true)
	private String zipcode;

	/**
	 * User's telephone number
	 */
	@JsonView(UserView.Location.class)
	@Size(min = 9, max = 10)
	@Column(name = "telephone_number", nullable = true)
	private String telephone_number;

	/**
	 * User's card name
	 */
	@JsonView(UserView.PaymentInformation.class)
	@Column(name = "card_name", nullable = true)
	private String card_name;

	/**
	 * User's card number
	 */
	@JsonView(UserView.PaymentInformation.class)
	@Column(name = "card_number", nullable = true)
	private String card_number;

	/**
	 * Expiration date of this card number
	 */
	@JsonView(UserView.PaymentInformation.class)
	@Column(name = "expiration_date", nullable = true)
	private Date expirationDate;

	/**
	 * Authentications list of this User
	 */
//	@OneToMany(mappedBy = "user")
//	@LazyCollection(LazyCollectionOption.FALSE)
//	private List<Authentication> authentication;

	/**
	 * Role of this User
	 */
	@ManyToOne
	@Fetch(FetchMode.SELECT)
	private Role role;

//	/**
//	 * Orders list of this User
//	 */
//	@OneToMany(mappedBy = "user")
//	@LazyCollection(LazyCollectionOption.FALSE)
//	private List<Order> orders;
	
//	/**
//	 * Address list of this User
//	 */
//	@Transient
//	@OneToMany(mappedBy = "user")
//	@LazyCollection(LazyCollectionOption.FALSE)
//	private List<Address> addresses;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAddress1() {
		return address1;
	}

	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	public String getAddress2() {
		return address2;
	}

	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getZipcode() {
		return zipcode;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}

	public String getTelephone_number() {
		return telephone_number;
	}

	public void setTelephone_number(String telephone_number) {
		this.telephone_number = telephone_number;
	}

	public String getCard_name() {
		return card_name;
	}

	public void setCard_name(String card_name) {
		this.card_name = card_name;
	}

	public Date getExpirationDate() {
		return expirationDate;
	}

	public void setExpirationDate(Date expirationDate) {
		this.expirationDate = expirationDate;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

//	public List<Authentication> getAuthenticate() {
//		return authentication;
//	}

	public Integer getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Date getJoiningDate() {
		return joiningDate;
	}

	public void setJoiningDate(Date joiningDate) {
		this.joiningDate = joiningDate;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		return result;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getCard_number() {
		return card_number;
	}

	public void setCard_number(String card_number) {
		this.card_number = card_number;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof User))
			return false;
		User other = (User) obj;
		if (id != other.id)
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", first name=" + firstName + ", last name=" + lastName + ", joiningDate="
				+ joiningDate + ", email=" + email + ", password=" + password + "]";
	}

	/**
	 * Encrypt password by using SHA-256
	 */
	public String changeToHash(String password) {
		String passwordHash = "";
		try {
			MessageDigest sha256 = MessageDigest.getInstance("SHA-256");
			try {
				byte[] hash = sha256.digest(password.getBytes("UTF-8"));
				passwordHash = String.format("%64x", new java.math.BigInteger(1, hash));

			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return passwordHash;
	}

}
