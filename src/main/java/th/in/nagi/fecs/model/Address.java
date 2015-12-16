//package th.in.nagi.fecs.model;
//
//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.JoinColumn;
//import javax.persistence.ManyToOne;
//import javax.persistence.Table;
//import javax.validation.constraints.Size;
//
//import org.hibernate.annotations.Fetch;
//import org.hibernate.annotations.FetchMode;
//
///**
// * Address model
// * 
// * @author Chonnipa Kittisiriprasert
// *
// */
//@Entity
//@Table(name = "address")
//public class Address {
//
//	@ManyToOne
//	@JoinColumn(name = "user_id")
//	@Fetch(FetchMode.SELECT)
//	private User user;
//	
//	/**
//	 * Address's address(line 1)
//	 */
//	@Column(name = "address_1", nullable = true)
//	private String address1;
//
//	/**
//	 * Address's address(line 2)
//	 */
//	@Column(name = "address_2", nullable = true)
//	private String address2;
//
//	/**
//	 * Address's province
//	 */
//	@Column(name = "province", nullable = true)
//	private String province;
//
//	/**
//	 * Address's zipcode
//	 */
//	@Size(min = 5, max = 5)
//	@Column(name = "zipcode", nullable = true)
//	private String zipcode;
//
//	/**
//	 * Address's telephone number
//	 */
////	@JsonView(AddressView.Location.class)
////	@Size(min = 9, max = 10)
////	@Column(name = "telephone_number", nullable = true)
////	private String telephone_number;
//}
