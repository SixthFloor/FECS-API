package th.in.nagi.fecs.service;

import java.util.List;

import th.in.nagi.fecs.model.Authenticate;
import th.in.nagi.fecs.model.Role;

public interface AuthenticateService {

	Authenticate findByKey(Integer id);

	void store(Authenticate authenticate);

	void update(Authenticate authenticate);

	List<Authenticate> findAll();

	Authenticate findByToken(String token);

	List<Authenticate> findByEmail(String email);

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
