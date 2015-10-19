package th.in.nagi.fecs.service;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import th.in.nagi.fecs.model.Authenticate;
import th.in.nagi.fecs.model.Role;
import th.in.nagi.fecs.model.User;
import th.in.nagi.fecs.repository.AuthenticateRepository;
import th.in.nagi.fecs.repository.RoleRepository;

/**
 * Authentication service It help to manage the data about authentication. Ex.
 * add, edit, find.
 * 
 * @author Nara Surawit
 *
 */
@Service("authenticateService")
@Transactional
public class AuthenticateServiceImpl implements AuthenticateService {

	/**
	 * Tool for managing authentication table that link to database.
	 */
	@Autowired
	private AuthenticateRepository authenticateRepository;

	/**
	 * Tool for managing role table that link to database.
	 */
	@Autowired
	private RoleRepository roleRopository;
	
	public final Role ADMIN = roleRopository.findByName("admin");
	public final Role STAFF = roleRopository.findByName("staff");
	public final Role MANAGER = roleRopository.findByName("manager");
	public final Role OWNER = roleRopository.findByName("owner");

	/**
	 * Tool for managing user table that link to database.
	 */
	@Autowired
	private UserService userService;

	/**
	 * Find datum in authentication table.
	 * 
	 * @param id
	 * @return Authenticate
	 */
	@Override
	public Authenticate findByKey(Integer id) {
		return authenticateRepository.findByKey(id);
	}

	/**
	 * Save datum to database.
	 * 
	 * @param authenticate
	 */
	@Override
	public void store(Authenticate authenticate) {
		authenticateRepository.store(authenticate);
	}

	/**
	 * Update user's authentication in database.
	 * 
	 * @param authenticate
	 */
	@Override
	public void update(Authenticate authenticate) {
		// TODO Auto-generated method stub
		// Authenticate entity =
		// authenticateRepository.findByKey(authenticate.getId());
		// if (entity != null) {
		// entity.setUsername(authenticate.getUsername());
		// entity.setToken(authenticate.getToken());
		// }
	}

	/**
	 * Find all authentication in database.
	 * 
	 * @return list<Authenticate>
	 */
	@Override
	public List<Authenticate> findAll() {
		return authenticateRepository.findAll();
	}

	/**
	 * Find authentication by using token.
	 * 
	 * @param token
	 * @return Authenticate
	 */
	@Override
	public Authenticate findByToken(String token) {
		return authenticateRepository.findByToken(token);
	}

	/**
	 * Remove authentication by using token.
	 * 
	 * @param token
	 */
	@Override
	public void removeByToken(String token) {
		authenticateRepository.removeByToken(token);
	}

	/**
	 * Find tokens by using user's email.
	 */
	@Override
	public List<Authenticate> findByEmail(String email) {
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
	public boolean checkPermission(String token, Role... roles) {
		Role userRole = findByToken(token).getUser().getRole();
		if (Arrays.asList(roles).contains(userRole) & isExpiration(token)) {
			return true;
		}
		return false;

	}

}
