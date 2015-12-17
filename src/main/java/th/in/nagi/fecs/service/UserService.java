package th.in.nagi.fecs.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import th.in.nagi.fecs.model.User;
import th.in.nagi.fecs.model.User;
import th.in.nagi.fecs.repository.UserRepository;

/**
 * 
 * User Service
 * 
 * @author Chonnipa Kittisiriprasert
 *
 */
@Service("userService")
@Transactional
public class UserService {

	@Autowired
	private UserRepository userRepository;

	/**
	 * Get list of all users.
	 * 
	 * @param id
	 *            user's id
	 * @return User
	 * 
	 */
	public User findByKey(Integer id) {
		return userRepository.findByKey(id);
	}

	/**
	 * Save new user in database.
	 * 
	 * @param user
	 *            new user
	 * 
	 * @return List<User>
	 * 
	 */
	public void store(User user) {
		userRepository.store(user);
	}

	/**
	 * Edit user detail.
	 * 
	 * @param user
	 *            old user
	 * 
	 */
	public void update(User user) {
		User entity = userRepository.findByKey(user.getId());
		if (entity != null) {
			if (user.getFirstName() != null) {
				entity.setFirstName(user.getFirstName());
			}
			if (user.getLastName() != null) {
				entity.setLastName(user.getLastName());
			}
			if (user.getLastName() != null) {
				entity.setRole(user.getRole());
			}
			if (user.getAddress1() != null) {
				entity.setAddress1(user.getAddress1());
			}
			if (user.getAddress2() != null) {
				entity.setAddress2(user.getAddress2());
			}
			if (user.getEmail() != null) {
				entity.setEmail(user.getEmail());
			}
			if (user.getPassword() != null && !user.getPassword().equals("")) {
				entity.setPassword(user.changeToHash(user.getPassword()));
			}
			if (user.getProvince() != null) {
				entity.setProvince(user.getProvince());
			}
			if (user.getZipcode() != null) {
				entity.setZipcode(user.getZipcode());
			}
			if (user.getTelephone_number() != null) {
				entity.setTelephone_number(user.getTelephone_number());
			}
		}
	}
	
	public void updatePayment(User user){
		User entity = userRepository.findByKey(user.getId());
		if (user.getCard_name() != null) {
			entity.setCard_name(user.getCard_name());
		}
		if (user.getCard_number() != null) {
			entity.setCard_number(user.getCard_number());
		}
		if (user.getExpirationDate() != null) {
			entity.setExpirationDate(user.getExpirationDate());
		}
	}
	
	public void updateRole(User user){
		User entity = userRepository.findByKey(user.getId());
		if (user.getRole() != null) {
			entity.setRole(user.getRole());
		}
	}

	/**
	 * Delete user in database by email.
	 * 
	 * @param email
	 *            email of that user
	 * 
	 */
	public void removeByEmail(String email) {
		userRepository.removeByEmail(email);
	}

	/**
	 * Get list of all users.
	 * 
	 * @return List<User>
	 * 
	 */
	public List<User> findAll() {
		return userRepository.findAll();
	}

	/**
	 * Get user by using email.
	 * 
	 * @param email
	 *            user's email
	 * 
	 * @return user
	 * 
	 */
	public User findByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	/**
	 * Check email in database to make sure that it is not duplicate.
	 * 
	 * @param id
	 *            user's id
	 * @param email
	 *            user's email
	 * @return List<User>
	 * 
	 */
	public boolean isEmailUnique(Integer id, String email) {
		User user = findByEmail(email);
		return (user == null || ((id != null) && (user.getId() == id)));
	}

	/**
	 * Find users with limit size and ascending by name.
	 * 
	 * @param start
	 * @param size
	 * @return List<User>
	 */
	public List<User> findAndAscByFirstName(int start, int size) {
		return userRepository.findAndAscByFirstName(start, size);
	}

	/**
	 * Find users with limit size and descending by name.
	 * 
	 * @param start
	 * @param size
	 * @return List<User>
	 */
	public List<User> findAndDescByFirstName(int start, int size) {
		return userRepository.findAndDescByFirstName(start, size);
	}

	public List<User> findByKeyword(String keyword) {
		return userRepository.findByKeyword(keyword);
	}

}
