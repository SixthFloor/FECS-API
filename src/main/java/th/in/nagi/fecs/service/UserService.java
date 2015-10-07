package th.in.nagi.fecs.service;


import java.util.List;

import th.in.nagi.fecs.model.User;

public interface UserService {

	User findById(int id);
	
	void save(User user);
	
	void update(User user);
	
	void deleteByUsername(String username);

	List<User> findAll(); 
	
	User findByUsername(String username);

	boolean isUsernameUnique(Integer id, String username);
	
}
