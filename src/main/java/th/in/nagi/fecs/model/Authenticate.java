package th.in.nagi.fecs.model;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonBackReference;

/**
 * Product model
 * @author Nara Surawit
 *
 */
@Entity
@Table(name = "authenticate")
public class Authenticate {

	/**
	 * id of product
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	/**
	 * serialNumber of product
	 */
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "token")
	private String token;

	/**
	 * name of product
	 */
	@NotNull
	@Column(name = "username")
	private String username;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JsonBackReference
	@NotEmpty
	private User user;
	
	@NotNull
    @Column(name = "expiration_date", nullable = false)
	private Date expDate;
	
	public Authenticate(){
		
	}
	
	public Authenticate(String token){
		this.token = token;
	}
	
	public Authenticate(String token, String username, Date date){
		this.token = token;
		this.username = username;
		this.expDate =date;
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
	

    
//    /**
//     * Indicates whether some other object is "equal to" this one.
//     * @parem obj the reference object with which to compare.
//     * @return true if this object is the same as the obj argument; false otherwise.
//     */
//	@Override
//    public boolean equals(Object obj) {
//        if (this == obj)
//            return true;
//        if (obj == null)
//            return false;
//        if (!(obj instanceof Product))
//            return false;
//        Product other = (Product) obj;
//        if (id != other.id){
//            return false;
//        } else if (!serialNumber.equals(other.serialNumber))
//            return false;
//        return true;
//    }
//	
	/** 
	 * Return a string representation of the object.
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
