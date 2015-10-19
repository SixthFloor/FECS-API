package th.in.nagi.fecs.repository;

import th.in.nagi.fecs.model.Role;

/**
 * Collection of tool for managing role in database.
 * 
 * @author Thanachote Visetsuthimont
 *
 */
public interface RoleRepository extends Repository<Role, Integer> {

	/**
	 * Query role by name.
	 * 
	 * @param name
	 * @return SubCategory
	 */
	Role findByName(String name);
}
