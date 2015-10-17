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
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "user")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@NotEmpty
	@Column(name = "email", nullable = false)
	private String email;

	@NotEmpty
	@Size(min = 3, max = 50)
	@Column(name = "first_name", nullable = false)
	private String firstName;

	@NotEmpty
	@Size(min = 3, max = 50)
	@Column(name = "last_name", nullable = false)
	private String lastName;

	@NotEmpty
	@Column(name = "joining_date", nullable = false)
	private Date joiningDate;

	@NotEmpty
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "username", unique = true, nullable = false)
	private String username;

	@NotEmpty
	@Size(min = 131, max = 131)
	@Column(name = "password", nullable = false)
	private String password;

	@Column(name = "address_1", nullable = true)
	private String address1;

	@Column(name = "address_2", nullable = true)
	private String address2;

	@Column(name = "province", nullable = true)
	private String province;

	@Size(min = 5, max = 5)
	@Column(name = "zipcode", nullable = true)
	private String zipcode;

	@Size(min = 9, max = 10)
	@Column(name = "telephone_number", nullable = true)
	private String telephone_number;

	@Column(name = "card_name", nullable = true)
	private String card_name;

	@Column(name = "expiration_date", nullable = true)
	private String expirationDate;

	@Size(min = 3, max = 3)
	@Column(name = "card_cvv", nullable = true)
	private String cardCVV;

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "user")
	@JsonManagedReference
	private List<Authenticate> authenticate;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JsonBackReference
	private Role role;

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

	public String getExpirationDate() {
		return expirationDate;
	}

	public void setExpirationDate(String expirationDate) {
		this.expirationDate = expirationDate;
	}

	public String getCardCVV() {
		return cardCVV;
	}

	public void setCardCVV(String cardCVV) {
		this.cardCVV = cardCVV;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public List<Authenticate> getAuthenticate() {
		return authenticate;
	}

	public void setAuthenticate(List<Authenticate> authenticate) {
		this.authenticate = authenticate;
	}

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

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime * result + ((username == null) ? 0 : username.hashCode());
		return result;
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
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", first name=" + firstName + ", last name=" + lastName + ", joiningDate="
				+ joiningDate + ", username=" + username + ", password=" + password + "]";
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
