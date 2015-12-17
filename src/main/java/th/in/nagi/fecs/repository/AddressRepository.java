package th.in.nagi.fecs.repository;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import th.in.nagi.fecs.model.Address;

/**
 * Implemented address repository
 * 
 * @author Chonnipa Kittisiriprasert
 *
 */
@Repository("addressRepository")
public class AddressRepository extends AbstractRepository<Address, Integer> {

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Address> findAll() {
		Criteria criteria = createEntityCriteria();
		return criteria.list();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Address findByKey(Integer key) {
		return getByKey(key);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void store(Address address) {
		persist(address);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void remove(Integer key) {
		remove(key);
	}
	
	public Address findByAddressId(Integer id) {
		Criteria criteria = createEntityCriteria();
		criteria.add(Restrictions.eq("addressId", id));
		return (Address) criteria.uniqueResult();
	}
}
