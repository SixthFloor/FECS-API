package th.in.nagi.fecs.service;

import java.util.List;

import th.in.nagi.fecs.model.Authentication;
import th.in.nagi.fecs.model.Role;

public interface AuthenticateService {
	
	public final String MEMBER = "member";
	public final String STAFF = "staff";
	public final String MANAGER = "member";
	public final String OWNER = "owner";

	Authentication findByKey(Integer id);

	void store(Authentication authentication);

	void update(Authentication authentication);

	List<Authentication> findAll();

	Authentication findByToken(String token);

	List<Authentication> findByEmail(String email);

	void removeByToken(String token);

	Role getRole(String token);
	
	boolean isExpiration(String token);
	
    /**
     * 
     * @param token
     * @param roles
     * @return true if this token can access and token isn't expiration.
     */
    boolean checkPermission(String token, String... roles);
}
