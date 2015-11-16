package th.in.nagi.fecs.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import th.in.nagi.fecs.model.Authentication;
import th.in.nagi.fecs.model.Role;
import th.in.nagi.fecs.model.User;
import th.in.nagi.fecs.repository.AuthenticationRepository;
import th.in.nagi.fecs.repository.RoleRepository;

/**
 * Authentication service It help to manage the data about authentication. Ex.
 * add, edit, find.
 * 
 * @author Nara Surawit
 *
 */
@Service("authenticationService")
@Transactional
public class AuthenticationServiceImpl implements AuthenticationService {

	/**
	 * Tool for managing authentication table that link to database.
	 */
	@Autowired
	private AuthenticationRepository authenticationRepository;

	/**
	 * Tool for managing role table that link to database.
	 */
	@Autowired
	private RoleRepository roleRepository;

	/**
	 * Tool for managing user table that link to database.
	 */
	@Autowired
	private UserService userService;

	/**
	 * Find datum in authentication table.
	 * 
	 * @param id
	 * @return Authentication
	 */
	@Override
	public Authentication findByKey(Integer id) {
		return authenticationRepository.findByKey(id);
	}

	/**
	 * Save datum to database.
	 * 
	 * @param authentication
	 */
	@Override
	public void store(Authentication authentication) {
		authenticationRepository.store(authentication);
	}

	/**
	 * Update user's authentication in database.
	 * 
	 * @param authentication
	 */
	@Override
	public void update(Authentication authentication) {
		// TODO Auto-generated method stub
		// Authentication entity =
		// authenticationRepository.findByKey(authenticate.getId());
		// if (entity != null) {
		// entity.setUsername(authenticate.getUsername());
		// entity.setToken(authenticate.getToken());
		// }
	}

	/**
	 * Find all authentication in database.
	 * 
	 * @return list<Authentication>
	 */
	@Override
	public List<Authentication> findAll() {
		return authenticationRepository.findAll();
	}

	/**
	 * Find authentication by using token.
	 * 
	 * @param token
	 * @return Authentication
	 */
	@Override
	public Authentication findByToken(String token) {
		return authenticationRepository.findByToken(token);
	}

	/**
	 * Remove authentication by using token.
	 * 
	 * @param token
	 */
	@Override
	public void removeByToken(String token) {
		authenticationRepository.removeByToken(token);
	}

	/**
	 * Find tokens by using user's email.
	 */
	@Override
	public List<Authentication> findByEmail(String email) {
		User user = userService.findByEmail(email);
		return user.getAuthenticate();

	}

	@Override
	public Role getRole(String token) {
		return findByToken(token).getUser().getRole();
	}

	@Override
	public boolean isExpiration(String token) {
		Date date = new Date();
		if (date.before(findByToken(token).getExpDate())) {
			return true;
		}
		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean checkPermission(String token, String... roles) {
		List<Role> list = new ArrayList<Role>();
		for (String role : roles) {
			list.add(roleRepository.findByName(role));
		}
		Role userRole = findByToken(token).getUser().getRole();
		if (list.contains(userRole) & isExpiration(token)) {
			return true;
		}
		return false;

	}

}
