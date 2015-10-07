package th.in.nagi.fecs.repository;


import java.util.List;

import th.in.nagi.fecs.model.User;

public interface UserRepository {

	User findById(int id);

	void save(User user);
	
	void deleteByUsername(String username);
	
	List<User> findAll();

	User findByUsername(String username);

}
