package th.in.nagi.fecs.repository;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractRepository<E, K extends Serializable>
        implements Repository<E, K> {

    private final Class<E> persistentClass;

    @SuppressWarnings("unchecked")
    public AbstractRepository() {
        this.persistentClass = (Class<E>) ((ParameterizedType) this.getClass()
                .getGenericSuperclass()).getActualTypeArguments()[0];
    }

    @Autowired
    private SessionFactory sessionFactory;

    protected Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    @SuppressWarnings("unchecked")
    public E getByKey(K key) {
        return (E) getSession().get(persistentClass, key);
    }

    public void persist(E entity) {
        getSession().persist(entity);
    }

    public void remove(E entity) {
        getSession().delete(entity);
    }

    protected Criteria createEntityCriteria() {
        return getSession().createCriteria(persistentClass);
    }
}
