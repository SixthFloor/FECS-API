package th.in.nagi.fecs.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import th.in.nagi.fecs.model.Role;
import th.in.nagi.fecs.repository.RoleRepository;

/**
 * Provide Role service for managing data easier. Ex. add, remove, edit.
 * 
 * @author Thanachote Visetsuthimont
 *
 */
@Service("roleService")
@Transactional
public class RoleService {

	public final String MEMBER = "member";
	public final String STAFF = "staff";
	public final String MANAGER = "member";
	public final String OWNER = "owner";

	/**
	 * Tool for manage role in database.
	 *
	 */
	@Autowired
	RoleRepository roleRepository;

	/**
	 * Get role by id.
	 * 
	 * @param id
	 *            role's id
	 * @return role
	 * 
	 */
	public Role findByKey(Integer id) {
		return roleRepository.findByKey(id);
	}

	/**
	 * Save new role in database.
	 * 
	 * @param role
	 *            new role
	 * 
	 */
	public void store(Role role) {
		roleRepository.store(role);
	}

	/**
	 * Delete role by id
	 * 
	 * @param id
	 *            role's id
	 * 
	 */
	public void removeById(Integer id) {
		roleRepository.remove(id);
	}

	/**
	 * Get all roles
	 * 
	 * @return List<Role>
	 * 
	 */
	public List<Role> findAll() {
		return roleRepository.findAll();
	}

	/**
	 * Get role by name
	 * 
	 * @param name
	 *            role's name
	 * 
	 */
	public Role findByName(String name) {
		return roleRepository.findByName(name);
	}

}
