//package th.in.nagi.fecs.model;
//
//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;
//import javax.persistence.Table;
//import javax.validation.constraints.NotNull;
//import javax.validation.constraints.Size;
//
//import org.hibernate.annotations.Type;
//import org.hibernate.validator.constraints.NotEmpty;
//import org.joda.time.LocalDate;
//import org.springframework.context.annotation.Bean;
//import org.springframework.format.annotation.DateTimeFormat;
//
//import com.fasterxml.jackson.databind.annotation.JsonSerialize;
//import com.fasterxml.jackson.databind.ser.std.DateSerializer;
//
//@Entity
//@Table(name = "f_user")
//public class User extends AbstractEntity<Integer> {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private int id;
//
//    @Size(min = 3, max = 50)
//    @Column(name = "first_name", nullable = false)
//    private String firstName;
//
//    @Size(min = 3, max = 50)
//    @Column(name = "last_name", nullable = false)
//    private String lastName;
//
//    @NotNull
//    @DateTimeFormat(pattern = "dd/MM/yyyy")
//    @Column(name = "JOINING_DATE", nullable = false)
//    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDate")
//    @JsonSerialize(using = DateSerializer.class)
//    private LocalDate joiningDate;
//
//    @NotEmpty
//    @Column(name = "username", unique = true, nullable = false)
//    private String username;
//
//    public Integer getId() {
//        return id;
//    }
//
//    public void setId(int id) {
//        this.id = id;
//    }
//
//    public String getFirstName() {
//        return firstName;
//    }
//
//    public void setFirstName(String firstName) {
//        this.firstName = firstName;
//    }
//
//    public String getLastName() {
//        return lastName;
//    }
//
//    public void setLastName(String lastName) {
//        this.lastName = lastName;
//    }
//
//    public LocalDate getJoiningDate() {
//        return joiningDate;
//    }
//
//    public void setJoiningDate(LocalDate joiningDate) {
//        this.joiningDate = joiningDate;
//    }
//
//    public String getUsername() {
//        return username;
//    }
//
//    public void setUsername(String username) {
//        this.username = username;
//    }
//
//    @Override
//    public int hashCode() {
//        final int prime = 31;
//        int result = 1;
//        result = (int) (prime * result + id);
//        result = prime * result
//                + ((username == null) ? 0 : username.hashCode());
//        return result;
//    }
//
//    @Override
//    public boolean equals(Object obj) {
//        if (this == obj)
//            return true;
//        if (obj == null)
//            return false;
//        if (!(obj instanceof User))
//            return false;
//        User other = (User) obj;
//        if (id != other.id)
//            return false;
//        if (username == null) {
//            if (other.username != null)
//                return false;
//        } else if (!username.equals(other.username))
//            return false;
//        return true;
//    }
//
//    @Override
//    public String toString() {
//        return "User [id=" + id + ", first name=" + firstName + ", last name="
//                + lastName + ", joiningDate=" + joiningDate + ", username="
//                + username + "]";
//    }
//
//    public static User create() {
//        return new User();
//    }
//}
package th.in.nagi.fecs.model;

import java.sql.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.NotEmpty;
import org.joda.time.LocalDate;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "f_user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Size(min = 3, max = 50)
    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Size(min = 3, max = 50)
    @Column(name = "last_name", nullable = false)
    private String lastName;

    @NotNull
    @Column(name = "JOINING_DATE", nullable = false)
    private Date joiningDate;

    @NotEmpty
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "username", unique = true, nullable = false)
    private String username;
    
    @OneToMany(fetch=FetchType.EAGER,mappedBy = "user")
    private Set<Authenticate> authenticate;

    public Set<Authenticate> getAuthenticate() {
		return authenticate;
	}

	public void setAuthenticate(Set<Authenticate> authenticate) {
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

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + id;
        result = prime * result
                + ((username == null) ? 0 : username.hashCode());
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
        return "User [id=" + id + ", first name=" + firstName + ", last name="
                + lastName + ", joiningDate=" + joiningDate + ", username="
                + username + "]";
    }

}
