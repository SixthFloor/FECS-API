package th.in.nagi.fecs.repository;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityNotFoundException;

/**
 * Repository
 * 
 * @author Chonnipa Kittisiriprasert
 *
 * @param <E>
 *            Type of entity
 * @param <K>
 *            Type of key
 */
public interface Repository<E, K extends Serializable> {

    /**
     * Finds all entities stored in this repository
     * 
     * @return all entities stored in this repository or an empty list
     */
    List<E> findAll();

    /**
     * Finds an entity that matches the given key
     * 
     * @param key
     *            key
     * @return an entity
     * @throws EntityNotFoundException
     *             the repository does not contain an entity that matches the
     *             given key
     */
    E findByKey(K key);

    /**
     * Stores new entity to this repository
     * 
     * @param entity
     *            new entity
     * @throws EntityAlreadyExistedException
     *             the entity with the same key already existed in this
     *             repository
     */
    void store(E entity);

    /**
     * Removes an existing entity from this repository.
     * 
     * @param key
     *            key
     * @throws EntityNotFoundException
     *             the repository does not contain an entity that matches the
     *             given key
     */
    void remove(K key);

    /**
     * Removes an existing entity from this repository.
     * 
     * @param entity
     *            entity
     * @throws EntityNotFoundException
     *             the repository does not contain an entity that matches the
     *             given key
     */
    void remove(E entity);
}
