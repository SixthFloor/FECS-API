package th.in.nagi.fecs.model;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;

@Entity
@Table(name = "category")
public class Category {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Size(min = 1, max = 50)
	@Column(name = "name", nullable = false)
	private String name;

	public void setId(Integer id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getId() {
		return id;
	}

	public String getName() {
		return name;
	}
	
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!(obj instanceof User))
            return false;
        Category other = (Category) obj;
        if (id != other.id) {
            return false;
        } else if (!name.equals(other.name))
            return false;
        return true;
    }
    
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
