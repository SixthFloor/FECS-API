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

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

/**
 * Address model
 * 
 * @author Chonnipa Kittisiriprasert
 *
 */
@Entity
@Table(name = "address")
public class Address {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(nullable = false)
	private int id;

	@ManyToOne
	@JoinColumn(name = "user_id")
	@Fetch(FetchMode.SELECT)
	private User user;

	/**
	 * Address's address(line 1)
	 */
	@Column(name = "address_1", nullable = false)
	private String address1;

	/**
	 * Address's address(line 2)
	 */
	@Column(name = "address_2", nullable = true)
	private String address2;

	/**
	 * Address's province
	 */
	@Column(name = "province", nullable = false)
	private String province;

	/**
	 * Address's zipcode
	 */
	@Size(min = 5, max = 5)
	@Column(name = "zipcode", nullable = false)
	private String zipcode;

	@Size(min = 9, max = 10)
	@Column(name = "phone_number", nullable = false)
	private String phoneNumber;

	public Integer getId() {
		return id;
	}

	public User getUser() {
		return user;
	}

	public String getAddress1() {
		return address1;
	}

	public String getAddress2() {
		return address2;
	}

	public String getProvince() {
		return province;
	}

	public String getZipcode() {
		return zipcode;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
}
