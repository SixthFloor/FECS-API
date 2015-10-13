package th.in.nagi.fecs.service;

import java.util.List;
import th.in.nagi.fecs.model.Authenticate;


public interface AuthenticateService {
	Authenticate findByKey(Integer id);

    void store(Authenticate authenticate);

    void update(Authenticate authenticate);

    List<Authenticate> findAll();

    Authenticate findByToken(String token);
    
    Authenticate findByUsername(String username);
    
    void removeByToken(String token);
}