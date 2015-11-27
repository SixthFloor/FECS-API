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
	
	public final String MEMBER = "member";
	public final String STAFF = "staff";
	public final String MANAGER = "member";
	public final String OWNER = "owner";
	
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
	
	public Role findByName(String name){
		return roleRepository.findByName(name);
	}

}
