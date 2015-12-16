package th.in.nagi.fecs.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonView;

import th.in.nagi.fecs.view.RoleView;

/**
 * Role model
 * 
 * @author Thanachote Visetsuthimont
 *
 */
@Entity
@Table(name = "role")
public class Role {

	/**
	 * The possible roles of Role
	 */
	public static final String MEMBER = "member";
	public static final String STAFF = "staff";
	public static final String MANAGER = "manager";
	public static final String OWNER = "owner";

	/**
	 * Role's id
	 */
	@JsonView(RoleView.Personal.class)
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	/**
	 * Role's name
	 */
	@JsonView(RoleView.Personal.class)
	@NotEmpty
	@Column(name = "name", nullable = false)
	private String name;

	/**
	 * Users who have this role.
	 */
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "role")
	private List<User> users;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof User))
			return false;
		Role other = (Role) obj;
		if (id != other.id)
			return false;
		if (name != other.name)
			return false;
		return true;
	}
}
