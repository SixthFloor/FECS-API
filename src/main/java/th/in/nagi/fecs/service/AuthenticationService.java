package th.in.nagi.fecs.service;

import java.util.ArrayList;
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
public class AuthenticationService {

	public final String MEMBER = Role.MEMBER;
	public final String STAFF = Role.STAFF;
	public final String MANAGER = Role.MANAGER;
	public final String OWNER = Role.OWNER;

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
	public Authentication findByKey(Integer id) {
		return authenticationRepository.findByKey(id);
	}

	/**
	 * Save datum to database.
	 * 
	 * @param authentication
	 */
	public void store(Authentication authentication) {
		authenticationRepository.store(authentication);
	}

	/**
	 * Update user's authentication in database.
	 * 
	 * @param authentication
	 */
	public void update(Authentication authentication) {
		// TODO Auto-generated method stub
	}

	/**
	 * Find all authentication in database.
	 * 
	 * @return list<Authentication>
	 */
	public List<Authentication> findAll() {
		return authenticationRepository.findAll();
	}

	/**
	 * Find authentication by using token.
	 * 
	 * @param token
	 * @return Authentication
	 */
	public Authentication findByToken(String token) {
		return authenticationRepository.findByToken(token);
	}

	/**
	 * Remove authentication by using token.
	 * 
	 * @param token
	 */
	public void removeByToken(String token) {
		authenticationRepository.removeByToken(token);
	}

	/**
	 * Find tokens by using user's email.
	 * 
	 * @param email
	 * @return List<Authentication>
	 */
	public List<Authentication> findByEmail(String email) {
		
		User user = userService.findByEmail(email);
		return authenticationRepository.findByUser(user);
//		return user.getAuthenticate();
	}

	/**
	 * Find role by using token.
	 * 
	 * @param token
	 * @return Role
	 */
	public Role getRole(String token) {
		return findByToken(token).getUser().getRole();
	}

	/**
	 * check token is expired or not.
	 * 
	 * @param token
	 * @return boolean if true, the token is valid.
	 */
	public boolean isExpired(String token) {
		Date date = new Date();
		if (date.before(findByToken(token).getExpDate())) {
			return true;
		}
		return false;
	}

	/**
	 * check permission, what roles can access.
	 */
	public boolean checkPermission(String token, String... roles) {
		List<Role> list = new ArrayList<Role>();
		for (String role : roles) {
			list.add(roleRepository.findByName(role));
		}
		
		Role userRole = findByToken(token).getUser().getRole();
		if (list.contains(userRole) & isExpired(token)) {
			return true;
		}
		return false;

	}

}
