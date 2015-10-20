package th.in.nagi.fecs.service;

import java.util.List;

import th.in.nagi.fecs.model.Role;

public interface RoleService {
	Role findByKey(Integer id);

	void store(Role category);

	void removeById(Integer id);

	List<Role> findAll();

}
