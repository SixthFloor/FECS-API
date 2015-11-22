package th.in.nagi.fecs.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import th.in.nagi.fecs.model.Role;
import th.in.nagi.fecs.repository.RoleRepository;

@Service("roleService")
@Transactional
public class RoleService {
	
	@Autowired
	RoleRepository roleRepository; 

	public Role findByKey(Integer id) {		
		return roleRepository.findByKey(id);
	}

	public void store(Role role) {
		roleRepository.store(role);
	}


	public void removeById(Integer id) {
		roleRepository.remove(id);
	}

	public List<Role> findAll() {
		return roleRepository.findAll();
	}

}
