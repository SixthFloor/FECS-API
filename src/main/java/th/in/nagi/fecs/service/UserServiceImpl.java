package th.in.nagi.fecs.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import th.in.nagi.fecs.model.SubCategory;
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
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	public User findByKey(Integer id) {
		return userRepository.findByKey(id);
	}

	public void store(User user) {
		userRepository.store(user);
	}

	/*
	 * Since the method is running with Transaction, No need to call hibernate
	 * update explicitly. Just fetch the entity from db and update it with
	 * proper values within transaction. It will be updated in db once
	 * transaction ends.
	 */
	/**
	 * {@inheritDoc}
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
//			entity.setJoiningDate(user.getJoiningDate());
			if (user.getAddress1() != null) {
				entity.setAddress1(user.getAddress1());
			}
			if (user.getAddress2() != null) {
				entity.setAddress2(user.getAddress1());
			}
//			entity.setAddress2(user.getAddress2());
			if (user.getCard_name() != null) {
				entity.setCard_name(user.getCard_name());
			}
			if (user.getCardCVV() != null) {
				entity.setCardCVV(user.getCardCVV());
			}
			if (user.getEmail() != null) {
				entity.setEmail(user.getEmail());
			}
			if (user.getExpirationDate() != null) {
				entity.setExpirationDate(user.getExpirationDate());
			}
			if (user.getPassword() != null) {
				entity.setPassword(user.getPassword());
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

	/**
	 * {@inheritDoc}
	 */
	public void removeByEmail(String email) {
		userRepository.removeByEmail(email);
	}

	/**
	 * {@inheritDoc}
	 */
	public List<User> findAll() {
		return userRepository.findAll();
	}

	/**
	 * {@inheritDoc}
	 */
	public User findByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	/**
	 * {@inheritDoc}
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
	@Override
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
	@Override
	public List<User> findAndDescByFirstName(int start, int size) {
		return userRepository.findAndDescByFirstName(start, size);
	}

}
