package th.in.nagi.fecs.repository;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import th.in.nagi.fecs.model.Role;

/**
 * Tool for managing role in database.
 * 
 * @author Thanachote Visetsuthimont
 *
 */
@Repository("roleRopository")
public class RoleRopositoryImpl extends AbstractRepository<Role, Integer>implements RoleRepository {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Role findByName(String name) {
		Criteria criteria = createEntityCriteria();
		criteria.add(Restrictions.eq("name", name));
		return (Role) criteria.uniqueResult();
	}

}
